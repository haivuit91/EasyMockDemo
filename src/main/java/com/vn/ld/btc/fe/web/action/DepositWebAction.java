package com.vn.ld.btc.fe.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vn.ld.api.context.DepositSearchContext;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.btc.fe.business.IDepositManager;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.DepositInfo;
import com.vn.ld.btc.fe.info.UserOnlineInfo;
import com.vn.ld.btc.fe.model.DepositModel;
import com.vn.ld.btc.fe.security.UserOnlineUtils;
import com.vn.ld.common.Constants;

@Component
@Scope("prototype")
public class DepositWebAction extends BaseAction<DepositModel> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IDepositManager depositManager;
	
	private DepositModel model = new DepositModel();
	
	public String index() {
		init();
		return SUCCESS;
	}
	
	public String depositConfirm() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			LOG.error("User not login!");
			return ERROR;
		}
		init();
		if (!validateDepositInfo()) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String depositSubmit() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		// TODO: remove
		Integer customerId = 1;
		if (userOnline != null) {
			customerId = userOnline.getUserId();
		}
		DepositInfo depositInfo = this.model.getDepositInfo();
		depositInfo.setStatus(Constants.DepositStatus.REQUESTING);
		depositInfo.setCustomerId(customerId);
		Boolean isCreated = this.depositManager.createDeposit(depositInfo);
		init();
		if (!isCreated) {
			return ERROR;
		}
		depositInfo.setAmount("0");
		return SUCCESS;
	}
	
	public String depositHistory() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			return ERROR;
		}
		Map<Integer, String> mapDepositMethods = new LinkedHashMap<Integer, String>();
		mapDepositMethods.put(Constants.DepositMethod.METHOD_BANKWIRE, getText(Constants.DepositMethodName.METHOD_NAME_BANKWIRE));
		mapDepositMethods.put(Constants.DepositMethod.METHOD_CREDIT, getText(Constants.DepositMethodName.METHOD_NAME_CREDIT));
		this.model.setMapDepositMethods(mapDepositMethods);
		Map<Integer, String> mapDepositStatus = new LinkedHashMap<Integer, String>();
		mapDepositStatus.put(Constants.DepositStatus.REQUESTING, getText(Constants.DepositStatusName.REQUESTING));
		mapDepositStatus.put(Constants.DepositStatus.IN_PROGRESS, getText(Constants.DepositStatusName.IN_PROGRESS));
		mapDepositStatus.put(Constants.DepositStatus.COMPLETED, getText(Constants.DepositStatusName.COMPLETED));
		mapDepositStatus.put(Constants.DepositStatus.FAILED, getText(Constants.DepositStatusName.FAILED));
		mapDepositStatus.put(Constants.DepositStatus.CANCELLED, getText(Constants.DepositStatusName.CANCELLED));
		this.model.setMapDepositStatus(mapDepositStatus);
		
		Integer customerId = userOnline.getUserId();
		DepositSearchContext searchContext = this.model.getSearchContext();
		if (searchContext == null) {
			searchContext = new DepositSearchContext();
		}
		searchContext.setCustomerId(customerId);
		PagingInfoDTO pagingInfo = new PagingInfoDTO();
		pagingInfo.setIndexPage(1);
		pagingInfo.setOffset(20);
		// TODO: paging
		List<DepositInfo> listDeposit = this.depositManager.searchDepositHistory(searchContext, pagingInfo);
		this.model.setSearchContext(searchContext);
		this.model.setListDeposit(listDeposit);
		return SUCCESS;
	}
	
	public String cancelDepositAjax() {
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		if (userOnline == null) {
			LOG.error("User not login yet!");
			return ERROR;
		}
		String depositId = request.getParameter("depositId");
		if (StringUtils.isBlank(depositId)) {
			LOG.error("Lack required parameter [depositId]");
			return ERROR;
		}
		Integer customerId = userOnline.getUserId();
		Boolean result = this.depositManager.cancelDeposit(depositId, customerId);
		
		return result ? SUCCESS : ERROR;
	}
	
	private Boolean validateDepositInfo() {
		DepositInfo depositInfo = this.model.getDepositInfo();
		List<String> errorMsgs = null;
		if (depositInfo == null) {
			LOG.warn("Null deposit!");
			errorMsgs = new ArrayList<String>();
			errorMsgs.add(getText("deposit.message.failed"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		// Validate customer assets ID
		boolean isValidDest = false;
		CustomerAssetsInfo dest = null;
		List<CustomerAssetsInfo> listAssetsInfo = this.model.getListAssetsInfo();
		for (CustomerAssetsInfo assetsInfo : listAssetsInfo) {
			if (depositInfo.getDestinationId().equals(assetsInfo.getCustomerAssetsId())) {
				isValidDest = true;
				dest = assetsInfo;
				break;
			}
		}
		if (!isValidDest || dest == null) {
			LOG.warn("Invalid destination!");
			errorMsgs = new ArrayList<String>();
			errorMsgs.add(getText("deposit.message.failed"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		String amount = depositInfo.getAmount();
		if (StringUtils.isBlank(amount)) {
			LOG.warn("Deposit amount is required!");
			errorMsgs = new ArrayList<String>();
			List<String> listValue = new ArrayList<String>();
			listValue.add(getText("deposit.label.amount"));
			errorMsgs.add(getText("message.error.required", listValue));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		if (!NumberUtils.isNumber(amount)) {
			LOG.warn("Deposit amount is not a number!");
			errorMsgs = new ArrayList<String>();
			errorMsgs.add(getText("deposit.message.error.invalid.amount"));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		BigDecimal minDeposit = dest.getMinDeposit();
		BigDecimal convertedAmount = new BigDecimal(amount);
		if (minDeposit != null && minDeposit.compareTo(convertedAmount) > 0) {
			LOG.warn("Deposit amount less than min deposit!");
			errorMsgs = new ArrayList<String>();
			List<String> listValue = new ArrayList<String>();
			listValue.add(minDeposit.toString());
			errorMsgs.add(getText("deposit.message.error.min.amount", listValue));
			this.model.setErrorMessage(errorMsgs);
			return false;
		}
		return true;
	}
	
	private void init() {
		
		Map<Integer, String> mapDepositMethods = new LinkedHashMap<Integer, String>();
		mapDepositMethods.put(Constants.DepositMethod.METHOD_BANKWIRE, getText(Constants.DepositMethodName.METHOD_NAME_BANKWIRE));
		mapDepositMethods.put(Constants.DepositMethod.METHOD_CREDIT, getText(Constants.DepositMethodName.METHOD_NAME_CREDIT));
		this.model.setMapDepositMethods(mapDepositMethods);
		UserOnlineInfo userOnline = UserOnlineUtils.getUserOnlineInfo();
		List<CustomerAssetsInfo> listAssetsInfo = null;
		if (userOnline != null) {
			listAssetsInfo = this.depositManager.getCustomerAssets(userOnline.getUserId());
		} else {
			// TODO: Remove
			listAssetsInfo = this.depositManager.getCustomerAssets(1);
		}
		this.model.setListAssetsInfo(listAssetsInfo);
	}

	@Override
	public DepositModel getModel() {
		return this.model;
	}
	
}
