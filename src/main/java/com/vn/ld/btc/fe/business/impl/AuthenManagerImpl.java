package com.vn.ld.btc.fe.business.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vn.ld.api.service.RSCustomerService;
import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.btc.fe.business.IAuthenManager;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.common.util.ConvertUtils;

public class AuthenManagerImpl implements IAuthenManager {
	private final static Logger LOGGER = Logger.getLogger(AuthenManagerImpl.class.getName());
	private RSCustomerService rsCustomerServices;
	
	
	@Override
	public synchronized WebappUser findCustomerByLoginEmail(String email, String password) {
		LOGGER.info("Request login with email "+email);
		WebappUser webappUser = null;
		try {
			
			CustomerDTO customerDTO = rsCustomerServices.getCustomerByEmail(email);
			UserOnlineInfo userOnlineInfo = null;
			if(customerDTO != null) {
				if(ConvertUtils.cryptWithMD5(password).equals(customerDTO.getPassword())) {
					userOnlineInfo = new UserOnlineInfo();
					userOnlineInfo.setLoginEmail(customerDTO.getEmail());
					userOnlineInfo.setPassword(customerDTO.getPassword());
					userOnlineInfo.setUserId(customerDTO.getCustomerId());
					userOnlineInfo.setUserName(customerDTO.getUsername());
					userOnlineInfo.setGoogleAuthFlg(customerDTO.getGauthFlg());
					userOnlineInfo.setSecretCode(customerDTO.getSecretCode());
				}

				if(userOnlineInfo != null) {
					webappUser = new WebappUser(userOnlineInfo, "ROLE_USER");
					RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
					HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
					if(httpServletRequest != null) {
						webappUser.setRemoteIp(httpServletRequest.getRemoteAddr());
					}
				}
				
			}		
			
		} catch (Exception e) {
			LOGGER.error("[AuthenManagerImpl][findCustomerByLoginEmail] error "+ e, e);
		}
		return webappUser;
	}

	///////////////////Get-Set 
	public RSCustomerService getRsCustomerServices() {
		return rsCustomerServices;
	}

	public void setRsCustomerServices(RSCustomerService rsCustomerServices) {
		this.rsCustomerServices = rsCustomerServices;
	}
	
}
