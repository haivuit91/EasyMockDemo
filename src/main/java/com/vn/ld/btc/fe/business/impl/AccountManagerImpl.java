package com.vn.ld.btc.fe.business.impl;

import java.sql.Timestamp;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vn.ld.api.context.AccountContext;
import com.vn.ld.api.service.RSCustomerService;
import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.btc.fe.business.IAccountManager;
import com.vn.ld.btc.fe.info.ProfileInfo;
import com.vn.ld.common.Constants;
import com.vn.ld.common.email.MailObject;
import com.vn.ld.common.email.MailSendExcutor;
import com.vn.ld.common.email.MailSenderInfo;
import com.vn.ld.common.util.ConvertUtils;
import com.vn.ld.common.util.DateUtils;

public class AccountManagerImpl implements IAccountManager {
	
	private final static Logger LOGGER = Logger.getLogger(AccountManagerImpl.class);
	private RSCustomerService rsCustomerServices;
	private final static String FIX_EMAIL = "bitvalor@gmail.com";
	private final static String PASSWORD = "beautiful()ak";
//	private final static String USER_NAME_PARAMS = "username";
//	private final static String Link_ACTIVE_PARAMS = "linkactive";
	/**
	 * Check exist customer by email
	 */
	@Override
	public boolean checkExistCustomerByEmail(String email) {
		LOGGER.info("[AccountManagerImpl][INFO] check exist email :"+email);
		CustomerDTO customerDTO = rsCustomerServices.getCustomerByEmail(email);
		if(customerDTO != null) {
			LOGGER.info("[AccountManagerImpl][INFO] email exist in database");
			return false;
		}		
		return true;
	}
	
	
	/**
	 * Check exist customer by username
	 */
	@Override
	public boolean checkExistUserName(String username) {
		LOGGER.info("[AccountmanagerImpl][Info] check exist username: "+username);
		CustomerDTO customerDTO = rsCustomerServices.getCustomerByUserName(username);
		if (customerDTO != null ) {
			LOGGER.info("[AccountManagerImpl][INFO] username exist in database");
			return false;
		}
		return true;
	}



	/**
	 * Customer register new customer 
	 */
	@Override
	public void registerNewCustomer(AccountContext context) {
		CustomerDTO customerDTO = convertCustomerContexToDTO(context);
		if(customerDTO != null) {
			rsCustomerServices.saveNewCustomer(customerDTO);
		}
	}

	/**
	 * Convert customer context data to customer dto
	 * @param context
	 * @return CustomerDTO Object
	 */
	private CustomerDTO convertCustomerContexToDTO(AccountContext context) {
		CustomerDTO customerDTO = null;
		if(context != null) {
			customerDTO = new CustomerDTO();
			customerDTO.setUsername(context.getUserName());
			customerDTO.setActiveFlg(0);
			customerDTO.setActiveCode(context.getActiveCode());
			customerDTO.setAddress(context.getAddress());
			String birthday = context.getBirthday();
			if(birthday != null && !birthday.isEmpty()) {
				String convertDate = DateUtils.convertDateToOtherDatePattern(birthday, Constants.DateTimeFormat.PATTERN_MMDDYYYY, Constants.DateTimeFormat.PATTERN_YYYYMMDD_BLANK);
				if(convertDate != null) {
					
				}
			}
			if(context.getPassword() != null) {
				customerDTO.setPassword(ConvertUtils.cryptWithMD5(context.getPassword()));
			}
			customerDTO.setInputDate(new Timestamp(System.currentTimeMillis()));
			customerDTO.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			customerDTO.setPhoneNumber(context.getPhoneNumber());
			customerDTO.setEmail(context.getEmail());
			customerDTO.setFirstName(context.getFirstName());
			customerDTO.setLastName(context.getLastName());
			customerDTO.setStatus(1);
			customerDTO.setAllowLogin(0);
			customerDTO.setAllowTransaction(0);
			customerDTO.setAllowWithdraw(0);
			customerDTO.setGauthFlg(0);
		}
		
		return customerDTO;
	}
	
	
	
	/**
	 * Public send email active account customer
	 */
	@Override
	public void sendMailActiveAccountToCustomer(String urlLink,AccountContext context) {
		
		 
		 Properties mailProperties =  new Properties();
         mailProperties.put( "mail.transport.protocol", "smtp" );
         mailProperties.put( "mail.smtp.host", "smtp.gmail.com" );
         mailProperties.put( "mail.smtp.auth", "true" );
         mailProperties.put( "mail.smtp.starttls.enable", "true" );
         
         Session sessionMail = Session. getDefaultInstance(mailProperties,  new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(FIX_EMAIL, PASSWORD);
                }
           });
         
         //sessionMail.setDebug( true);
	      try {
	            Message messageMail = new MimeMessage(sessionMail);
	            messageMail.setFrom( new InternetAddress("bitvalor@gmail.com" ));
	            messageMail.setRecipients(Message.RecipientType. TO,
	                  InternetAddress. parse(context.getEmail()));
	            messageMail.setSubject( "No reply registration mail");
	            messageMail.setText( "Dear "+ context.getFirstName() + context.getLastName()+","
	                  + "\n\nThank you for submitting for registering Live trading accounts with BITVALOR! Your registration has been completely approved."
	                  + "In order to finish your final step of registration, please kindly complete the click below link to active your account."
	                  + "\n" + urlLink + "\n"
	                  + "Details of your trading accounts will be sent to you in the separate emails."
	                  + "If you need further assistance, please does not hesitate to contact our 24/6 Customer Support Team. \n\n Kind Regards,");
	            Transport. send(messageMail);
	     } catch (MessagingException e) {
	    	LOGGER.info("[AccountManagermentImpl][Sendemail] Error when send email comfirm "+ e, e); 
	     }
         
	}

	
	@Override
	public void sendMailActiveRegisterCustomer(MailObject mailObject,	MailSenderInfo mailSender) throws AddressException, MessagingException {		
		MailSendExcutor.getInstance().sendMail(mailObject, mailSender, false);
	}


	@Override
	public void activeAccountByActiveCode(String activeCode) {
		// TODO Auto-generated method stub
		
	}

	

	/**
	 * Get CustomerDTO object by activeCode
	 * @return CustomerDTO
	 */
	@Override
	public CustomerDTO getCustomerDTObyActiveCode(String activeCode) {
		return rsCustomerServices.getCustomerByActiveCode(activeCode);
	}

	public ProfileInfo getProfileByCustomerId(Integer customerId) {
		CustomerDTO customer = this.rsCustomerServices.getCustomerByCustomerID(customerId);
		if (customer == null) {
			LOGGER.warn("No customer found with id: " + customerId);
			return null;
		} else {
			return new ProfileInfo(customer);
		}
	}
	
	public Boolean updateProfile(ProfileInfo profileInfo) {
		LOGGER.info("[Start] Update user profile.");
		Integer customerId = profileInfo.getCustomerId();
		CustomerDTO customer = this.rsCustomerServices.getCustomerByCustomerID(customerId);
		if (customer == null) {
			LOGGER.warn("No customer found with id: " + customerId);
			return false;
		}
		if (!customer.getEmail().equals(profileInfo.getEmail())) {
			LOGGER.warn("Email does not match.");
			return false;
		}
		
		String username = profileInfo.getUsername();
		if (StringUtils.isNotBlank(username) && !username.equals(customer.getUsername())) {
			customer.setUsername(username);
		}
		String firstName = profileInfo.getFirstName();
		if (StringUtils.isNotBlank(firstName) && !firstName.equals(customer.getFirstName())) {
			customer.setFirstName(firstName);
		}
		String lastName = profileInfo.getLastName();
		if (StringUtils.isNotBlank(lastName) && !lastName.equals(customer.getLastName())) {
			customer.setLastName(lastName);
		}
		String phoneNumber = profileInfo.getPhoneNumber();
		if (StringUtils.isNotBlank(phoneNumber) && !phoneNumber.equals(customer.getPhoneNumber())) {
			customer.setPhoneNumber(phoneNumber);
		}
		String address = profileInfo.getAddress();
		if (StringUtils.isNotBlank(address) && !address.equals(customer.getAddress())) {
			customer.setAddress(address);
		}
		
		this.rsCustomerServices.updateCustomer(customer);
		return true;
	}
	
	/**
	 * Active customer
	 */
	@Override
	public Boolean comfirmActiveCustomerAccount(Integer customerId) {
		LOGGER.info("AccountManagerImpl: Activate account.");
		return this.rsCustomerServices.activateCustomer(customerId);
	}
	
	@Override
	public Boolean enableGoogleAuth(Integer customerId, String secretCode) {
		
		return this.rsCustomerServices.enableGoogleAuth(customerId, secretCode);
	}

	@Override
	public Boolean disableGoogleAuth(Integer customerId) {
		return this.rsCustomerServices.disableGoogleAuth(customerId);
	}
	///////////////////////////////////////////////
	public RSCustomerService getRsCustomerServices() {
		return rsCustomerServices;
	}

	public void setRsCustomerServices(RSCustomerService rsCustomerServices) {
		this.rsCustomerServices = rsCustomerServices;
	}

}
