package com.vn.ld.btc.fe.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vn.ld.api.context.WithdrawalSearchContext;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.api.dto.WithdrawalsPageDTO;
import com.vn.ld.api.exception.EntityNotFoundException;
import com.vn.ld.api.exception.UnauthorizedException;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.api.transfer.dto.DepositDTO;
import com.vn.ld.api.transfer.dto.WithdrawalDTO;
import com.vn.ld.api.transfer.service.RSTransferService;
import com.vn.ld.btc.fe.business.IWithdrawalManager;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.DepositInfo;
import com.vn.ld.btc.fe.info.WithdrawalInfo;

@Component("withdrawalManager")
public class WithdrawalManagerImpl implements IWithdrawalManager {
	private final Logger LOG = Logger.getLogger(WithdrawalManagerImpl.class);
	@Autowired
	private RSTransferService transferServiceClient;
	
	@Override
	public Boolean createWithdrawal(WithdrawalInfo withdrawalInfo) {
		if (withdrawalInfo == null) {
			LOG.warn("Null withdrawal.");
			return false;
		}
		WithdrawalDTO withdrawalDto = withdrawalInfo.convertToDto();
		String result = this.transferServiceClient.createNewWithdrawal(withdrawalDto);
		if (StringUtils.isNotBlank(result)) {
			return true;
		}
		LOG.warn("Failed to create withdrawal.");
		return false;
	}
	
	@Override
	public List<WithdrawalInfo> searchWithdrawal(WithdrawalSearchContext searchContext, PagingInfoDTO pagingInfo) {
		List<WithdrawalInfo> listWithdrawal = new ArrayList<WithdrawalInfo>();
		WithdrawalsPageDTO withdrawalsPage = this.transferServiceClient.searchWithdrawal(searchContext, pagingInfo.getIndexPage(), pagingInfo.getOffset());
		if (withdrawalsPage != null) {
			pagingInfo = withdrawalsPage.getPagingInfo();
			WithdrawalInfo info = null;
			for (WithdrawalDTO dto : withdrawalsPage.getWithdrawals()) {
				info = new WithdrawalInfo(dto);
				CollectionUtils.addIgnoreNull(listWithdrawal, info);
			}
		}
		return listWithdrawal;
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
	public Boolean cancelWithdrawal(String withdrawalId, Integer customerId) throws EntityNotFoundException, UnauthorizedException {
		return this.transferServiceClient.cancelWithdrawal(withdrawalId, customerId);
	}
}
