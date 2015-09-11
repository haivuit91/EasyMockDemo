package com.vn.ld.btc.fe.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vn.ld.btc.fe.business.IAccountManager;
import com.vn.ld.btc.fe.info.ProfileInfo;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.model.ProfileModel;
import com.vn.ld.btc.fe.security.UserOnlineUtils;

@Component
@Scope("prototype")
public class ProfileWebAction extends BaseAction<ProfileModel> {
	private static final long serialVersionUID = 4896622607035744009L;
	@Autowired
	private IAccountManager accountManager;
	
	private ProfileModel model = new ProfileModel();

	public String index() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			return ERROR;
		}
		ProfileInfo profile = this.accountManager.getProfileByCustomerId(userOnline.getUserId());
		if (profile == null) {
			LOG.warn("User not login.");
			return ERROR;
		}
		this.model.setProfileInfo(profile);
		return SUCCESS;
	}
	
	public String changePassword() {
		return SUCCESS;
	}
	
	public String updateProfile() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			LOG.warn("User not login.");
			return ERROR;
		}
		ProfileInfo profile = this.model.getProfileInfo();
		if (profile != null) {
			profile.setEmail(userOnline.getLoginEmail());
			this.model.setProfileInfo(profile);
		}
		return SUCCESS;
	}
	
	public String updateProfileConfirm() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			LOG.warn("User not login.");
			return ERROR;
		}
		ProfileInfo profile = this.model.getProfileInfo();
		if (profile != null) {
			profile.setCustomerId(userOnline.getUserId());
			this.accountManager.updateProfile(profile);
		}
		return SUCCESS;
	}
	
	@Override
	public ProfileModel getModel() {
		return this.model;
	}
}
