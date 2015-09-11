package com.vn.ld.btc.fe.model;

import com.vn.ld.btc.fe.info.ProfileInfo;

public class ProfileModel extends BaseModel {
	private static final long serialVersionUID = -2164388052293216927L;
	private ProfileInfo profileInfo;
	public ProfileInfo getProfileInfo() {
		return profileInfo;
	}
	public void setProfileInfo(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
	}
}
