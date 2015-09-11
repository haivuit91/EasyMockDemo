package com.vn.ld.btc.fe.info;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.vn.ld.api.dto.SymbolDTO;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.common.ServiceType;

public class CustomerAssetsInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer customerAssetsId;
	private Integer symbolId;
	private String assetsTypeName;
	private String symbolCd;
	private BigDecimal availableAmount;
	private BigDecimal reservedAmount;
	private BigDecimal totalAmount;
	private BigDecimal depositFeeAbs;
	private BigDecimal depositFeePercent;
	private BigDecimal withdrawFeeAbs;
	private BigDecimal withdrawFeePercent;
	private BigDecimal withdrawableAmount;
	private BigDecimal maxWithdraw;
	private BigDecimal minDeposit;
	private Date inputDate;
	private Date updateDate;
	private Integer customerId;
	
//	private List<AssetsAttributeDTO> assetsAttributes;
	
	public CustomerAssetsInfo() {
	}
	
	public CustomerAssetsInfo(CustomerAssetsDTO customerAssetsDto) {
		this.customerAssetsId = customerAssetsDto.getCustomerAssetsId();
		SymbolDTO symbol = customerAssetsDto.getSymbol();
		if (symbol != null) {
			this.symbolId = symbol.getSymbolId();
			this.symbolCd = symbol.getSymbolCd();
			this.assetsTypeName = ServiceType.getById(symbolId).getTypeName();
		}
		this.availableAmount = customerAssetsDto.getAvailableAmount();
		this.reservedAmount = customerAssetsDto.getReservedAmount();
		this.totalAmount = customerAssetsDto.getTotalAmount();
		this.depositFeeAbs = customerAssetsDto.getDepositFeeAbs();
		this.depositFeePercent = customerAssetsDto.getDepositFeePercent();
		this.withdrawFeeAbs = customerAssetsDto.getDepositFeeAbs();
		this.withdrawFeePercent = customerAssetsDto.getDepositFeePercent();
		this.withdrawableAmount = customerAssetsDto.getWithdrawableAmount();
		this.maxWithdraw = customerAssetsDto.getMaxWithdraw();
		this.minDeposit = customerAssetsDto.getMinDeposit();
		this.customerId = customerAssetsDto.getCustomerId();
	}

	public Integer getCustomerAssetsId() {
		return customerAssetsId;
	}

	public void setCustomerAssetsId(Integer customerAssetsId) {
		this.customerAssetsId = customerAssetsId;
	}

	public Integer getSymbolId() {
		return symbolId;
	}

	public void setSymbolId(Integer symbolId) {
		this.symbolId = symbolId;
	}

	public String getAssetsTypeName() {
		if (StringUtils.isBlank(assetsTypeName) && symbolId != null) {
			assetsTypeName = ServiceType.getById(symbolId).getTypeName();
		}
		return assetsTypeName;
	}

	public void setAssetsTypeName(String assetsTypeName) {
		this.assetsTypeName = assetsTypeName;
	}

	public String getSymbolCd() {
		return symbolCd;
	}

	public void setSymbolCd(String symbolCd) {
		this.symbolCd = symbolCd;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(BigDecimal reservedAmount) {
		this.reservedAmount = reservedAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDepositFeeAbs() {
		return depositFeeAbs;
	}

	public void setDepositFeeAbs(BigDecimal depositFeeAbs) {
		this.depositFeeAbs = depositFeeAbs;
	}

	public BigDecimal getDepositFeePercent() {
		return depositFeePercent;
	}

	public void setDepositFeePercent(BigDecimal depositFeePercent) {
		this.depositFeePercent = depositFeePercent;
	}

	public BigDecimal getWithdrawFeeAbs() {
		return withdrawFeeAbs;
	}

	public void setWithdrawFeeAbs(BigDecimal withdrawFeeAbs) {
		this.withdrawFeeAbs = withdrawFeeAbs;
	}

	public BigDecimal getWithdrawFeePercent() {
		return withdrawFeePercent;
	}

	public void setWithdrawFeePercent(BigDecimal withdrawFeePercent) {
		this.withdrawFeePercent = withdrawFeePercent;
	}

	public BigDecimal getWithdrawableAmount() {
		return withdrawableAmount;
	}

	public void setWithdrawableAmount(BigDecimal withdrawableAmount) {
		this.withdrawableAmount = withdrawableAmount;
	}

	public BigDecimal getMaxWithdraw() {
		return maxWithdraw;
	}

	public void setMaxWithdraw(BigDecimal maxWithdraw) {
		this.maxWithdraw = maxWithdraw;
	}

	public BigDecimal getMinDeposit() {
		return minDeposit;
	}

	public void setMinDeposit(BigDecimal minDeposit) {
		this.minDeposit = minDeposit;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerAssetsDTO convertToDTO() {
		CustomerAssetsDTO dto = new CustomerAssetsDTO();
		dto.setCustomerAssetsId(this.customerAssetsId);
		SymbolDTO symbol = new SymbolDTO();
		symbol.setSymbolId(symbolId);
		symbol.setSymbolCd(symbolCd);
		dto.setSymbol(symbol);
		dto.setTotalAmount(totalAmount);
		dto.setAvailableAmount(availableAmount);
		dto.setReservedAmount(reservedAmount);
		dto.setDepositFeeAbs(depositFeeAbs);
		dto.setDepositFeePercent(depositFeePercent);
		dto.setWithdrawableAmount(withdrawableAmount);
		dto.setWithdrawFeeAbs(withdrawFeeAbs);
		dto.setWithdrawFeePercent(withdrawFeePercent);
		dto.setCustomerId(customerId);
		dto.setInputDate(inputDate);
		dto.setUpdateDate(updateDate);
		return dto;
	}
}
