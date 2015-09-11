package com.vn.ld.btc.fe.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IWebUserDetailServices extends UserDetailsService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ;
	public UserDetails reloadPassword(String password, String username);
}
