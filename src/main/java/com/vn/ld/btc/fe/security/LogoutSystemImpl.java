package com.vn.ld.btc.fe.security;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSystemImpl extends SimpleUrlLogoutSuccessHandler {
	
	private final static Logger LOGGER = Logger.getLogger(LogoutSystemImpl.class.getName());
	
	private String logoutUrl;
	
	
	

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	

}
