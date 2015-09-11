package com.vn.ld.btc.fe.model;

import java.util.List;
import java.util.Map;

import com.vn.ld.api.context.DepositSearchContext;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.DepositInfo;

public class DepositModel extends BaseModel {
	private static final long serialVersionUID = 1L;
	private DepositInfo depositInfo;
	private DepositSearchContext searchContext;
	private List<DepositInfo> listDeposit;
	private List<CustomerAssetsInfo> listAssetsInfo;
	private Map<Integer, String> mapDepositMethods;
	private Map<Integer, String> mapDepositStatus;
	
	public DepositInfo getDepositInfo() {
		return depositInfo;
	}
	public void setDepositInfo(DepositInfo depositInfo) {
		this.depositInfo = depositInfo;
	}
	public DepositSearchContext getSearchContext() {
		return searchContext;
	}
	public void setSearchContext(DepositSearchContext searchContext) {
		this.searchContext = searchContext;
	}
	public List<DepositInfo> getListDeposit() {
		return listDeposit;
	}
	public void setListDeposit(List<DepositInfo> listDeposit) {
		this.listDeposit = listDeposit;
	}
	public List<CustomerAssetsInfo> getListAssetsInfo() {
		return listAssetsInfo;
	}
	public void setListAssetsInfo(List<CustomerAssetsInfo> listAssetsInfo) {
		this.listAssetsInfo = listAssetsInfo;
	}
	public Map<Integer, String> getMapDepositMethods() {
		return mapDepositMethods;
	}
	public void setMapDepositMethods(Map<Integer, String> mapDepositMethods) {
		this.mapDepositMethods = mapDepositMethods;
	}
	public Map<Integer, String> getMapDepositStatus() {
		return mapDepositStatus;
	}
	public void setMapDepositStatus(Map<Integer, String> mapDepositStatus) {
		this.mapDepositStatus = mapDepositStatus;
	}
}
