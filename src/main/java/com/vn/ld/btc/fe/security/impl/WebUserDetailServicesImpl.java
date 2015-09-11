package com.vn.ld.btc.fe.security.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vn.ld.btc.fe.business.IAuthenManager;
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.btc.fe.security.IWebUserDetailServices;

public class WebUserDetailServicesImpl implements IWebUserDetailServices {

	private IAuthenManager authenManager;
	private String password;
	private String username;
	
	@Override
	public synchronized UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username == null) {
			return null;
		}
		WebappUser webapp = null;
		synchronized (getAuthenManager()) {
			webapp = getAuthenManager().findCustomerByLoginEmail(username, getPassword());
			
//			if(webapp != null) {
//				Authentication request = new UsernamePasswordAuthenticationToken(webapp, getPassword());
//				SecurityContextHolder.getContext().setAuthentication(request);
//			}
		}
		return webapp;
	}
	
	@Override
	public UserDetails reloadPassword(String password, String username) {
		setPassword(password);
		return loadUserByUsername(username);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IAuthenManager getAuthenManager() {
		return authenManager;
	}
	public void setAuthenManager(IAuthenManager authenManager) {
		this.authenManager = authenManager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
