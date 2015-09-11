package com.vn.ld.btc.fe.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vn.ld.api.context.DepositSearchContext;
import com.vn.ld.api.dto.DepositsPageDTO;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.api.exception.EntityNotFoundException;
import com.vn.ld.api.exception.UnauthorizedException;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.api.transfer.dto.DepositDTO;
import com.vn.ld.api.transfer.service.RSTransferService;
import com.vn.ld.btc.fe.business.IDepositManager;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.DepositInfo;

@Component("depositManager")
public class DepositManagerImpl implements IDepositManager {
	private final Logger LOG = Logger.getLogger(DepositManagerImpl.class);
	
	@Autowired
	private RSTransferService transferServiceClient;
	
	@Override
	public Boolean createDeposit(DepositInfo depositInfo) {
		if (depositInfo == null) {
			LOG.warn("Null deposit!");
			return false;
		}
		DepositDTO dto = depositInfo.convertToDto();
		String result = this.transferServiceClient.createNewDeposit(dto);
		if (StringUtils.isNotBlank(result)) {
			return true;
		}
		
		LOG.error("Deposit failed!");
		return false;
	}
	
	@Override
	public List<CustomerAssetsInfo> getCustomerAssets(Integer customerId) {
		if (customerId == null) {
			LOG.warn("Null customer id!");
			return null;
		}
		List<CustomerAssetsDTO> assetsList = this.transferServiceClient.findCustomerAssets(customerId);
		if (assetsList != null && !assetsList.isEmpty()) {
			List<CustomerAssetsInfo> resultList = new ArrayList<CustomerAssetsInfo>();
			for (CustomerAssetsDTO assets : assetsList) {
				CollectionUtils.addIgnoreNull(resultList, new CustomerAssetsInfo(assets));
			}
			return resultList;
		}
		LOG.warn("No assets found with customer id: " + customerId);
		return null;
	}
	
	@Override
	public List<DepositInfo> searchDepositHistory(DepositSearchContext searchContext, PagingInfoDTO pagingInfo) {
		List<DepositInfo> listDeposit = new ArrayList<DepositInfo>();
		DepositsPageDTO depositPage = this.transferServiceClient.searchDeposit(searchContext, pagingInfo.getIndexPage(), pagingInfo.getOffset());
		if (depositPage != null) {
			pagingInfo = depositPage.getPagingInfo();
			DepositInfo info = null;
			for (DepositDTO dto : depositPage.getDeposits()) {
				info = new DepositInfo(dto);
				CollectionUtils.addIgnoreNull(listDeposit, info);
			}
		}
		return listDeposit;
	}
	
	@Override
	public Boolean cancelDeposit(String depositId, Integer customerId) throws EntityNotFoundException, UnauthorizedException {
		return this.transferServiceClient.cancelDeposit(depositId, customerId);
	}
}
