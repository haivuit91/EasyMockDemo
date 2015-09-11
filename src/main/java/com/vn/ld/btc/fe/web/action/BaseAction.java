package com.vn.ld.btc.fe.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.model.BaseModel;
import com.vn.ld.btc.fe.security.UserOnlineUtils;
import com.vn.ld.common.Constants;

public abstract class BaseAction<T extends BaseModel> extends ActionSupport implements ServletRequestAware, ServletResponseAware, ModelDriven<T>, Preparable {
	private static final long serialVersionUID = -7171589004244047895L;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@Override
	public void prepare() throws Exception {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline != null) {
			((BaseModel) getModel()).setLoginStatus(true);
			((BaseModel) getModel()).setIsEnabled2FA(Constants.ACTIVE_FLG.equals(userOnline.getGoogleAuthFlg()));
			((BaseModel) getModel()).setUserName(userOnline.getUserName());
		} else {
			((BaseModel) getModel()).setLoginStatus(false);
			((BaseModel) getModel()).setIsEnabled2FA(false);
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	protected String getCurrentUsername() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline != null) {
			return userOnline.getUserName();
		} else {
			return null;
		}
	}
	protected Integer getCurrentUserId() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline != null) {
			return userOnline.getUserId();
		} else {
			return null;
		}
	}
}
