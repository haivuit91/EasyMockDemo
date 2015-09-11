package com.vn.ld.btc.fe.model;

import com.vn.ld.api.context.AccountContext;

public class AccountModel extends BaseModel {

	private static final long serialVersionUID = -3205525305310819112L;
	
	private AccountContext accountContext = new AccountContext();

	private String jCaptchaResponse;

	public String getjCaptchaResponse() {
		return jCaptchaResponse;
	}

	public void setjCaptchaResponse(String jCaptchaResponse) {
		this.jCaptchaResponse = jCaptchaResponse;
	}

	public AccountContext getAccountContext() {
		return accountContext;
	}

	public void setAccountContext(AccountContext accountContext) {
		this.accountContext = accountContext;
	}
	

}
