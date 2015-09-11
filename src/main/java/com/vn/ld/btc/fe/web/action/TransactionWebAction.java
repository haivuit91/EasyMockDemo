package com.vn.ld.btc.fe.web.action;

import com.vn.ld.btc.fe.model.TransactionModel;

public class TransactionWebAction extends BaseAction<TransactionModel> {
	private static final long serialVersionUID = 8702676197323430740L;
	private TransactionModel model;
	
	@Override
	public TransactionModel getModel() {
		return this.model;
	}

}
