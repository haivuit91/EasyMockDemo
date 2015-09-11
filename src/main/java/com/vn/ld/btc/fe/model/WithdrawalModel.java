package com.vn.ld.btc.fe.model;

import java.util.List;
import java.util.Map;

import com.vn.ld.api.context.WithdrawalSearchContext;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.WithdrawalInfo;

public class WithdrawalModel extends BaseModel {
	private static final long serialVersionUID = 1121296637261670122L;
	
	private WithdrawalInfo withdrawalInfo;
	private WithdrawalSearchContext searchContext;
	private List<WithdrawalInfo> listWithdrawal;
	private List<CustomerAssetsInfo> listAssetsInfo;
	private Map<Integer, String> mapWithdrawalStatus;
	
	public WithdrawalInfo getWithdrawalInfo() {
		return withdrawalInfo;
	}
	public void setWithdrawalInfo(WithdrawalInfo withdrawalInfo) {
		this.withdrawalInfo = withdrawalInfo;
	}
	public WithdrawalSearchContext getSearchContext() {
		return searchContext;
	}
	public void setSearchContext(WithdrawalSearchContext searchContext) {
		this.searchContext = searchContext;
	}
	public List<WithdrawalInfo> getListWithdrawal() {
		return listWithdrawal;
	}
	public void setListWithdrawal(List<WithdrawalInfo> listWithdrawal) {
		this.listWithdrawal = listWithdrawal;
	}
	public List<CustomerAssetsInfo> getListAssetsInfo() {
		return listAssetsInfo;
	}
	public void setListAssetsInfo(List<CustomerAssetsInfo> listAssetsInfo) {
		this.listAssetsInfo = listAssetsInfo;
	}
	public Map<Integer, String> getMapWithdrawalStatus() {
		return mapWithdrawalStatus;
	}
	public void setMapWithdrawalStatus(Map<Integer, String> mapWithdrawalStatus) {
		this.mapWithdrawalStatus = mapWithdrawalStatus;
	}
}
