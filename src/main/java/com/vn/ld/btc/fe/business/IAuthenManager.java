package com.vn.ld.btc.fe.business;

import com.vn.ld.btc.fe.info.WebappUser;

public interface IAuthenManager {
	public WebappUser findCustomerByLoginEmail(String email, String password); 
}
