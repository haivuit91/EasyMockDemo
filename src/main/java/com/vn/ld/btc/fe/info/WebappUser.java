package com.vn.ld.btc.fe.info;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class WebappUser implements UserDetails {

	private static Logger LOGGER = Logger.getLogger(WebappUser.class.getName());
	
	private static final long serialVersionUID = 3021141628249427825L;
	private Collection<GrantedAuthority> roles = null;
	private UserOnlineInfo userOnlineInfo;
	private String remoteIp;
	
	
	@SuppressWarnings("deprecation")
	public WebappUser(UserOnlineInfo onlineInfo, String role) {
		LOGGER.info("[WebappUser][RoleImpl] set role for userlogin");
		this.userOnlineInfo = onlineInfo;
		
		if(role != null && role.trim().length() != 0) {
			GrantedAuthority firstRole = new GrantedAuthorityImpl(role);
        	roles = new ArrayList<GrantedAuthority>();
        	roles.add(firstRole);
		}
		
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		
		return getUserOnlineInfo() == null ? null : getUserOnlineInfo().getPassword();
	}

	@Override
	public String getUsername() {
		return getUserOnlineInfo() == null ? null : getUserOnlineInfo().getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserOnlineInfo getUserOnlineInfo() {
		return userOnlineInfo;
	}

	public void setUserOnlineInfo(UserOnlineInfo userOnlineInfo) {
		this.userOnlineInfo = userOnlineInfo;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
}
