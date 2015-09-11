package com.vn.ld.btc.fe.business;

import java.util.List;

import com.vn.ld.api.context.WithdrawalSearchContext;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.api.exception.EntityNotFoundException;
import com.vn.ld.api.exception.UnauthorizedException;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.WithdrawalInfo;

public interface IWithdrawalManager {
	public Boolean createWithdrawal(WithdrawalInfo withdrawalInfo);
	public List<WithdrawalInfo> searchWithdrawal(WithdrawalSearchContext searchContext, PagingInfoDTO pagingInfo);
	public List<CustomerAssetsInfo> getCustomerAssets(Integer customerId);
	public Boolean cancelWithdrawal(String withdrawalId, Integer customerId) throws EntityNotFoundException, UnauthorizedException;
}
