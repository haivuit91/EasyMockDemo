package com.vn.ld.btc.fe.business;

import java.util.List;

import com.vn.ld.api.context.DepositSearchContext;
import com.vn.ld.api.dto.PagingInfoDTO;
import com.vn.ld.api.exception.EntityNotFoundException;
import com.vn.ld.api.exception.UnauthorizedException;
import com.vn.ld.btc.fe.info.CustomerAssetsInfo;
import com.vn.ld.btc.fe.info.DepositInfo;

public interface IDepositManager {
	public Boolean createDeposit(DepositInfo depositInfo);
	public List<CustomerAssetsInfo> getCustomerAssets(Integer customerId);
	public List<DepositInfo> searchDepositHistory(DepositSearchContext searchContext, PagingInfoDTO pagingInfo);
	public Boolean cancelDeposit(String depositId, Integer customerId) throws EntityNotFoundException, UnauthorizedException;
}
