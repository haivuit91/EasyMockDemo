package com.vn.ld.btc.fe.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.vn.ld.api.context.AccountContext;
import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.btc.fe.business.IAccountManager;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.btc.fe.model.AccountModel;
import com.vn.ld.btc.fe.security.IWebUserDetailServices;
import com.vn.ld.btc.fe.security.UserOnlineUtils;
import com.vn.ld.btc.fe.util.GoogleAuthenticateUtils;
import com.vn.ld.btc.fe.util.MenuTagObjectInfo;
import com.vn.ld.common.Constants;
import com.vn.ld.common.email.MailObject;
import com.vn.ld.common.email.MailSendExcutor;
import com.vn.ld.common.util.ConvertUtils;
import com.vn.ld.common.util.ValidationUtil;

import freemarker.template.TemplateException;

public class AccountWebAction extends BaseAction<AccountModel> {
	
	private static final long serialVersionUID = 3926492651615417344L;
	private final static Logger LOGGER = Logger.getLogger(AccountWebAction.class);
	private final static String PARAM = "p";
	private final static String PARAM_CAPTCHA = "c";
	private final static String PARAM_CODE = "code";
	private final static String PARAM_ENABLE = "enable";
	private IAccountManager adminManager;
	private IWebUserDetailServices authencationUser;
	
	private AccountModel model = new AccountModel();
	
	/**
	 * Init Screen for register new customer
	 * @return
	 */
	public String initFromRegisterCustomer() {
		return SUCCESS;
	}
	
	public String customerRegistration(){
		try {			
			AccountContext accountContex = model.getAccountContext();
			if(accountContex != null ) {
			
				//Validate data before process
				if(!validateCustomerDataInput(accountContex)) {
					return ERROR;
				}
				
				//Create active code
				String activeCode = ConvertUtils.createActiveCode(15);
				if (!activeCode.isEmpty()) {
					accountContex.setActiveCode(activeCode);
				}
				
				
				//if not error add customer on db and send mail for customer 
				adminManager.registerNewCustomer(accountContex);
				
				//Send mail active link
				sendmailActiveLink(accountContex);
			}
		} catch (Exception e) {
			LOGGER.error("[AccountWebAction][CustomerRegistraion] error "+e, e);
		}
				
		return SUCCESS;
	}
	
	/** 
	 * Send mail active link
	 * @param accountContext
	 * @throws TemplateException 
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private void sendmailActiveLink(AccountContext accountContext) throws IOException, TemplateException, AddressException, MessagingException {
		if(accountContext != null) {
			MailObject mailObject = new MailObject();
			Map<String, String> mapObject = new HashMap<String, String>();
				if(accountContext.getActiveCode() != null) {
					StringBuilder configLink = new StringBuilder(getText("config.link.sendemail"));
					configLink.append("?p=").append(accountContext.getActiveCode());
					mapObject.put(Constants.MAIL_PARAMS.USERNAME, accountContext.getUserName() == null ? " " : accountContext.getUserName());
					mapObject.put(Constants.MAIL_PARAMS.LINK_ACTIVE_ACCOUNT, configLink.toString());
					//mailObject.setMailContent(MailSendExcutor.getInstance().convertContentMail(getText("mail.reg.active.content"), mapObject));
					//Get mail content from db
					// TODO
					mailObject.setMailContent(MailSendExcutor.getInstance().convertContentMail(Constants.MAIL_PARAMS.MAIL_ACTIVE_CONTENT, mapObject));
					mailObject.setTo(accountContext.getEmail());
					mailObject.setSubject(getText("mail.reg.active.link.subject"));
					MailSendExcutor.getInstance().sendmailViaGoogleSMTP(mailObject);
				}				
		}
	}
	
	/**
	 * Comfirm captcha
	 */
	public void captchaConfirm(){
		try {
			String captcha = request.getParameter(PARAM_CAPTCHA).toLowerCase();
			PrintWriter printWriter = response.getWriter();
			if(captcha != null && captcha.length() == 6) {
				String c = (String) ActionContext.getContext().getSession().get(Constants.CAPTCHA_KEY);
				if(c != null) {
					if(captcha.equals(c.toLowerCase())) {
						printWriter.print(1);
					} else {
						printWriter.print(0);
					}
				} else {
					printWriter.print(0);
				}
				
			} else {
				printWriter.print(0);
			}
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Logout system
	public void userLogOutSystem() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if(session.containsKey(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_SESSION_SPRING_SECURITTY)) {
				ActionContext.getContext().getSession().remove(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_SESSION_SPRING_SECURITTY);
			}
			MenuTagObjectInfo menuTagObjectInfo = new MenuTagObjectInfo();
			PrintWriter printWriter = response.getWriter();
			printWriter.print(menuTagObjectInfo.createMenuString());
			printWriter.flush();
			printWriter.close();
			
		} catch (Exception e) {
			LOGGER.error("Exception error "+e, e);
		}
	}
	
	public void userLoginSystem(){
		try {
			String loginEmail = request.getParameter(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_USER_NAME);
			String password = request.getParameter(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_PASSWORD);
			PrintWriter printWriter = response.getWriter();
			if(!validateLoginData(loginEmail, password)) {				
				// fail
				printWriter.print("-1");
				
			} else {
				WebappUser webappUser = (WebappUser) authencationUser.reloadPassword(password, loginEmail);
				if (webappUser != null) {
					UserOnlineInfo userOnline = webappUser.getUserOnlineInfo();
					if (Constants.ACTIVE_FLG.equals(userOnline.getGoogleAuthFlg())) {
						// need to call google authentication
						request.getSession().setAttribute(Constants.SESSION_KEY_TEMPORARY_USER, webappUser);
						request.getSession().setAttribute(Constants.SESSION_KEY_TEMPORARY_PASSWORD, password);
						printWriter.print("2");
					} else {
						// success
						Authentication authentication = new UsernamePasswordAuthenticationToken(webappUser, password);
						SecurityContextHolder.getContext().setAuthentication(authentication);
						
						//Set session
						Map<String, Object> session = ActionContext.getContext().getSession();
						session.put(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_SESSION_SPRING_SECURITTY, authentication);
						printWriter.print("1");
					}
				} else {
					// fail
					printWriter.print("0");
				}
				printWriter.flush();
				printWriter.close();
			}
		} catch (Exception e) {
			LOGGER.error("Exception error " + e, e);
		}
		
	}
	
	/**
	 * Validate data before login
	 * @param userEmail
	 * @param password
	 * @return
	 */
	public boolean validateLoginData(String userEmail, String password) {
		if(userEmail.trim().isEmpty()) {
			return false;
		} else {
			if(!ValidationUtil.isEmail(userEmail)) {
				return false;
			}
		}
		if(password.trim().isEmpty()) {
			return false;
		} else {
			if(ValidationUtil.isContainSpecialChars(password)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Validate customer data input registration form
	 * @param context
	 * @return
	 */
	private boolean validateCustomerDataInput(AccountContext context) {
		model.clearErrorMesage();
		String userName = context.getUserName();
		if(userName != null && !userName.isEmpty()) {
			if(!StringUtils.isAlphanumeric(userName)) {
				model.addErrorContent(getText("msg001"));
				return false;
			} else {
				if(!adminManager.checkExistUserName(userName)) {
					model.addErrorContent(getText("msg003",new String[]{userName, getText("label.customer.username").toLowerCase()}));
					return false;
				}
			}
		}
		
		
		String email = context.getEmail();
		if(email == null || email.isEmpty() || !ValidationUtil.isEmail(email)) {
			model.addErrorContent(getText("msg002", new String[]{getText("label.customer.email")}));
			return false;
		} else {
				if(!adminManager.checkExistCustomerByEmail(email)) {
					model.addErrorContent(getText("msg003", new String[] {email, getText("label.customer.email")}));
					return false;
				}
			}
		
//		String phoneNumber = context.getPhoneNumber();
//		if(!phoneNumber.isEmpty())
//		if(phoneNumber != null && phoneNumber.isEmpty()) {
//			model.addErrorContent(getText("msg002", new String[]{getText("label.customer.phone")}));
//			return false;
//		}
//		
//				
//		String firstName = context.getFirstName();
//		if(firstName != null && !firstName.isEmpty()) {
//			if(!StringUtils.isAlphanumeric(firstName)) {
//				model.addErrorContent(getText("msg002", new String[]{getText("label.customer.firstName")}));
//				return false;
//			}
//		}
//		
//		String lastName = context.getLastName();
//		if(lastName != null && !lastName.isEmpty()) {
//			if(!StringUtils.isAlphanumeric(lastName)) {
//				model.addErrorContent(getText("msg002", new String[]{getText("label.customer.lastName")}));
//				return false;
//			}
//		}
		
//		String birthday = context.getBirthday();
//		if(birthday != null && birthday.isEmpty()) {
//			model.addErrorContent(getText("msg002", new String[]{getText("label.customer.birthday")}));
//			return false;
//		}
		
		return true;
	}
	
	/**
	 * Comfirm active email
	 * @return
	 */
	public String customerComfirmRegistration() {
		LOG.info("[Start] Active account.");
		try {
			String activeCode = request.getParameter(PARAM);
			if(activeCode != null && !activeCode.isEmpty()) {
				boolean okActiveCode = StringUtils.isAlphanumeric(activeCode);
				if(okActiveCode) {
					CustomerDTO customerDTO = adminManager.getCustomerDTObyActiveCode(activeCode);
					if(customerDTO != null){
						LOG.info("Customer exists, activate.");
						Integer activeFlag = customerDTO.getActiveFlg();
						if(Constants.ACCOUNT_INFORMATION_FLAG.ACCOUNT_INACTIVE.equals(activeFlag)) {
							adminManager.comfirmActiveCustomerAccount(customerDTO.getCustomerId());
						} else {
							return SUCCESS;
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("[AccountWebAction][ComfirmCustomerRegistration] error when active link "+e, e);
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	public void generateBarcode() throws IOException {
		PrintWriter printWriter = response.getWriter();
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null || StringUtils.isBlank(userOnline.getUserName())) {
			LOG.warn("User does not login or have blank username.");
			printWriter.print("-1");
		} else {
			try {
				String secretKey = GoogleAuthenticateUtils.generateSecretKey(userOnline.getPassword());
				request.getSession().setAttribute(Constants.SESSION_KEY_GAUTH_SECRET_KEY, secretKey);
				String hashKey = GoogleAuthenticateUtils.generatePrivateKey(userOnline.getPassword());
				String decryptedSecret = GoogleAuthenticateUtils.decryptAES(hashKey, secretKey);
				byte[] qrCode = GoogleAuthenticateUtils.generateQRCode(userOnline.getUserName(), Constants.HOST_NAME, decryptedSecret);
				Base64 codec = new Base64();
				printWriter.print(codec.encodeAsString(qrCode));
			} catch (Exception ex) {
				LOG.error("Error while generating barcode.", ex);
				printWriter.print("-1");
			}
		}
		printWriter.flush();
		printWriter.close();
	}
	
	public void verifyCode() throws IOException {
		LOG.debug("[Start] Verify the authentication code.");
		PrintWriter printWriter = response.getWriter();
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		WebappUser webappUser = (WebappUser) request.getSession().getAttribute(Constants.SESSION_KEY_TEMPORARY_USER);
		if (userOnline == null && webappUser == null) {
			LOG.warn("User does not login!");
			printWriter.print("-1");
		} else {
			String codeStr = request.getParameter(PARAM_CODE);
			String secretKey = "";
			boolean isValidCode = false;
			String enableRequest = request.getParameter(PARAM_ENABLE);
			boolean isEnableRequest = Constants.ACTIVE_FLG.toString().equals(enableRequest);
			
			long code = 0;
			if (NumberUtils.isNumber(codeStr)) {
				code = Long.parseLong(codeStr);
			}
			long time = System.currentTimeMillis();
			try {
				if (isEnableRequest) {
					secretKey = (String) request.getSession().getAttribute(Constants.SESSION_KEY_GAUTH_SECRET_KEY);
					isValidCode = GoogleAuthenticateUtils.checkCode(secretKey, userOnline.getPassword(), code, time);
					if (isValidCode) {
						// User requests to enable 2FA 
						Boolean result = this.adminManager.enableGoogleAuth(userOnline.getUserId(), secretKey);
						if (result) {
							request.getSession().removeAttribute(Constants.SESSION_KEY_GAUTH_SECRET_KEY);
							UserOnlineUtils.getUserOnlineInfo().setGoogleAuthFlg(Constants.ACTIVE_FLG);
							UserOnlineUtils.getUserOnlineInfo().setSecretCode(secretKey);
							printWriter.print("1");
						} else {
							LOG.warn("Error while saving secret key.");
							printWriter.print("-1");
						}
					}
				} else {
					userOnline = webappUser.getUserOnlineInfo();
					if (StringUtils.isBlank(secretKey)) {
						secretKey = userOnline.getSecretCode();
					}
					isValidCode = GoogleAuthenticateUtils.checkCode(secretKey, userOnline.getPassword(), code, time);
					if (isValidCode) {
						// User authenticate with 2FA
						LOG.info("Google Authenticate successfully.");
						String password = (String) this.request.getSession().getAttribute(Constants.SESSION_KEY_TEMPORARY_PASSWORD);
						Authentication authentication = new UsernamePasswordAuthenticationToken(webappUser, password);
						SecurityContextHolder.getContext().setAuthentication(authentication);
						
						//Set secsion
						Map<String, Object> session = ActionContext.getContext().getSession();
						session.put(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_SESSION_SPRING_SECURITTY, authentication);
						this.request.getSession().removeAttribute(Constants.SESSION_KEY_TEMPORARY_PASSWORD);
						this.request.getSession().removeAttribute(Constants.SESSION_KEY_TEMPORARY_USER);
						printWriter.print("1");
					}
				}
				
				if (!isValidCode) {
					LOG.info("Invalid authentication code.");
					printWriter.print("0");
				}
			} catch (Exception ex) {
				LOG.error("Error while verifying authentication code.", ex);
				printWriter.print("-1");
			}
			
		}
		printWriter.flush();
		printWriter.close();
		LOG.debug("[Finish] Verify the authentication code.");
	}
	
	public void disableGoogleAuth() throws IOException {
		LOG.debug("[Start] Disable google authentication.");
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		PrintWriter printWriter = response.getWriter();
		if (userOnline == null) {
			LOG.warn("User not login or session timeout.");
			printWriter.print("-1");
		} else {
			Integer customerId = userOnline.getUserId();
			Boolean result = this.adminManager.disableGoogleAuth(customerId);
			if (result) {
				UserOnlineUtils.getUserOnlineInfo().setGoogleAuthFlg(Constants.INACTIVE_FLG);
				UserOnlineUtils.getUserOnlineInfo().setSecretCode(null);
				printWriter.print("1");
			} else {
				LOG.warn("Disable google authentication failed.");
				printWriter.print("-1");
			}
		}
		LOG.debug("[Finish] Disable google authentication.");
	}
	///////////////////////////////////
	
	public void setModel(AccountModel model) {
		this.model = model;
	}

	public IAccountManager getAdminManager() {
		return adminManager;
	}

	public void setAdminManager(IAccountManager adminManager) {
		this.adminManager = adminManager;
	}

	@Override
	public AccountModel getModel() {
		return model;
	}

	public IWebUserDetailServices getAuthencationUser() {
		return authencationUser;
	}

	public void setAuthencationUser(IWebUserDetailServices authencationUser) {
		this.authencationUser = authencationUser;
	}
}
