package com.vn.ld.btc.fe.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vn.ld.api.context.WithdrawalSearchContext;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.btc.fe.business.IWithdrawalManager;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.info.WithdrawalInfo;
import com.vn.ld.btc.fe.model.WithdrawalModel;
import com.vn.ld.btc.fe.security.UserOnlineUtils;
import com.vn.ld.common.Constants;

@Component
@Scope("prototype")
public class WithdrawalWebAction extends BaseAction<WithdrawalModel> {
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(WithdrawalWebAction.class);
	
	private WithdrawalModel model = new WithdrawalModel();
	
	@Autowired
	private IWithdrawalManager withdrawalManager;
	
	public String index() {
		init();
		return SUCCESS;
	}
	
	public String withdrawalConfirm() {
		init();
		if (!validateWithdrawalInfo()) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String withdrawalSubmit() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		// TODO: remove
		Integer customerId = 1;
		if (userOnline != null) {
			customerId = userOnline.getUserId();
		}
		WithdrawalInfo withdrawalInfo = model.getWithdrawalInfo();
		withdrawalInfo.setStatus(Constants.WithdrawalStatus.REQUESTING);
		withdrawalInfo.setCustomerId(customerId);
		Boolean isCreated = this.withdrawalManager.createWithdrawal(withdrawalInfo);
		init();
		if (!isCreated) {
			return ERROR;
		}
		
		withdrawalInfo.setAmount(new BigDecimal("0"));
		return SUCCESS;
	}
	
	public String withdrawalHistory() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			return ERROR;
		}
		Map<Integer, String> mapWithdrawalStatus = new LinkedHashMap<Integer, String>();
		mapWithdrawalStatus.put(Constants.WithdrawalStatus.REQUESTING, getText(Constants.WithdrawalStatusName.REQUESTING));
		mapWithdrawalStatus.put(Constants.WithdrawalStatus.IN_PROGRESS, getText(Constants.WithdrawalStatusName.IN_PROGRESS));
		mapWithdrawalStatus.put(Constants.WithdrawalStatus.COMPLETED, getText(Constants.WithdrawalStatusName.COMPLETED));
		mapWithdrawalStatus.put(Constants.WithdrawalStatus.FAILED, getText(Constants.WithdrawalStatusName.FAILED));
		mapWithdrawalStatus.put(Constants.WithdrawalStatus.CANCELLED, getText(Constants.WithdrawalStatusName.CANCELLED));
		this.model.setMapWithdrawalStatus(mapWithdrawalStatus);
		Integer customerId = userOnline.getUserId();
		WithdrawalSearchContext searchContext = this.model.getSearchContext();
		if (searchContext == null) {
			searchContext = new WithdrawalSearchContext();
		}
		searchContext.setCustomerId(customerId);
		PagingInfoDTO pagingInfo = new PagingInfoDTO();
		pagingInfo.setIndexPage(1);
		pagingInfo.setOffset(20);
		// TODO: paging
		List<WithdrawalInfo> listWithdrawal = this.withdrawalManager.searchWithdrawal(searchContext, pagingInfo);
		this.model.setSearchContext(searchContext);
		this.model.setListWithdrawal(listWithdrawal);
		return SUCCESS;
	}
	
	public String cancelWithdrawalAjax() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			LOG.error("User not login yet!");
			return ERROR;
		}
		String withdrawalId = request.getParameter("withdrawalId");
		if (StringUtils.isBlank(withdrawalId)) {
			LOG.error("Lack required parameter [depositId]");
			return ERROR;
		}
		Integer customerId = userOnline.getUserId();
		Boolean result = this.withdrawalManager.cancelWithdrawal(withdrawalId, customerId);
		
		return result ? SUCCESS : ERROR;
	}
	
	private void init() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		List<CustomerAssetsInfo> listAssetsInfo = null;
		if (userOnline != null) {
			listAssetsInfo = this.withdrawalManager.getCustomerAssets(userOnline.getUserId());
		} else {
			// TODO: remove
			listAssetsInfo = this.withdrawalManager.getCustomerAssets(1);
		}
		this.model.setListAssetsInfo(listAssetsInfo);
	}
	
	private Boolean validateWithdrawalInfo() {
		WithdrawalInfo withdrawalInfo = this.model.getWithdrawalInfo();
		List<String> errorMsgs = new ArrayList<String>();
		if (withdrawalInfo == null) {
			LOG.warn("Null withdrawal!");
			errorMsgs.add(getText("withdrawal.message.failed"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		boolean isValidSource = false;
		CustomerAssetsInfo source = null;
		List<CustomerAssetsInfo> listAssetsInfo = this.model.getListAssetsInfo();
		for (CustomerAssetsInfo assetsInfo : listAssetsInfo) {
			if (withdrawalInfo.getSourceId().equals(assetsInfo.getCustomerAssetsId())) {
				isValidSource = true;
				source = assetsInfo;
				break;
			}
		}
		if (!isValidSource || source == null) {
			LOG.warn("Invalid source!");
			errorMsgs = new ArrayList<String>();
			errorMsgs.add(getText("withdrawal.message.failed"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		BigDecimal amount = withdrawalInfo.getAmount();
		if (amount == null) {
			LOG.warn("Withdrawal amount is required!");
			errorMsgs = new ArrayList<String>();
			List<String> listValue = new ArrayList<String>();
			listValue.add(getText("withdrawal.label.amount"));
			errorMsgs.add(getText("message.error.required", listValue));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		BigDecimal maxWithdrawal = source.getMaxWithdraw();
		if (maxWithdrawal != null && amount.compareTo(maxWithdrawal) > 0) {
			LOG.warn("Withdrawal amount exceeds the max withdraw!");
			errorMsgs = new ArrayList<String>();
			List<String> listValue = new ArrayList<String>();
			listValue.add(maxWithdrawal.toString());
			errorMsgs.add(getText("withdrawal.message.error.max.amount", listValue));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		BigDecimal withdrawableAmount = source.getWithdrawableAmount();
		if (amount.compareTo(withdrawableAmount) > 0) {
			LOG.warn("Withdrawal amount exceeds the withdrawable amount!");
			errorMsgs = new ArrayList<String>();
			errorMsgs.add(getText("withdrawal.message.error.withdrawable.amount"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		return true;
	}

	@Override
	public WithdrawalModel getModel() {
		return this.model;
	}
}
