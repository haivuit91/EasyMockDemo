package com.vn.ld.btc.fe.security;

import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.opensymphony.xwork2.ActionContext;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.common.Constants;

public class UserOnlineUtils {
	private static final Logger LOGGER = Logger.getLogger(UserOnlineUtils.class.getName());
	
	/**
	 * Get detail webuser app
	 * @return
	 */
	public static WebappUser getWebappUser() {
		try {
			Authentication authentication =(Authentication) ActionContext.getContext().getSession().get(Constants.ACCOUNT_LOGIN_LOGOUT_PARAMS.KEY_SESSION_SPRING_SECURITTY);
			if(authentication == null) {
				return null;
			}
			Object obj = authentication.getPrincipal();
			if(obj != null && obj instanceof WebappUser) {
				WebappUser webappUser = (WebappUser) obj;
				webappUser.setRemoteIp(ServletActionContext.getRequest().getRemoteAddr());
				return webappUser;
			}
		} catch (Exception e) {
			LOGGER.error("[UserOnlineUtils][getWebappUser()] error"+e,e);
		}
		return null;
	}
	
	
	/**
	 * Get UserOnlineInfo
	 * @return
	 */
	public static UserOnlineInfo getUserOnlineInfo() {
		try {
			return getWebappUser().getUserOnlineInfo();
		} catch (Exception e) {
			LOGGER.info("[getUserOnlineInfo] UserOnlineInfo is null");
		}
		return null;
	}
		
	/**
	 * Get RemoteIP
	 * @return
	 */
	public static String getRemoteIP() {
		try {
			return getWebappUser().getRemoteIp();
		} catch (Exception e) {
			LOGGER.info("[getRemoteIP] Remote IP null");
		}
		return null;
	}
	
	
}
