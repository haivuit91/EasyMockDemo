package com.vn.ld.btc.fe.info;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.vn.ld.api.dto.SymbolDTO;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.api.transfer.dto.WithdrawalDTO;
import com.vn.ld.common.ServiceType;

public class WithdrawalInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String withdrawalId;
	private BigDecimal amount;
	private Integer status;
	private String statusString;
	private Integer methodId;
	private String methodName;
	private Integer sourceId;
	private Integer symbolId;
	private String symbolCd;
	private String sourceName;
	private Date inputDate;
	private Date acceptDate;
	private Date updateDate;
	private Integer customerId;
	
	public WithdrawalInfo() {
	}
	
	public WithdrawalInfo(WithdrawalDTO withdrawalDto) {
		this.withdrawalId = withdrawalDto.getWithdrawalId();
		this.amount = withdrawalDto.getAmount();
		this.status = withdrawalDto.getStatus();
		this.statusString = "";
		this.methodId = withdrawalDto.getWithdrawalMethod();
		this.methodName = "";
		CustomerAssetsDTO source = withdrawalDto.getSource();
		if (source != null) {
			this.sourceId = source.getCustomerAssetsId();
			SymbolDTO symbol = source.getSymbol();
			if (symbol != null) {
				this.symbolId = symbol.getSymbolId();
				this.symbolCd = symbol.getSymbolCd();
				this.sourceName = ServiceType.getById(this.symbolId).getTypeName();
			}
		}
		this.inputDate = withdrawalDto.getInputDate();
		this.acceptDate = withdrawalDto.getAcceptDate();
		this.updateDate = withdrawalDto.getUpdateDate();
		this.customerId = withdrawalDto.getCustomerId();
	}
	
	public String getWithdrawalId() {
		return withdrawalId;
	}
	public void setWithdrawalId(String withdrawalId) {
		this.withdrawalId = withdrawalId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public Integer getMethodId() {
		return methodId;
	}
	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(Integer symbolId) {
		this.symbolId = symbolId;
	}
	public String getSymbolCd() {
		return symbolCd;
	}

	public void setSymbolCd(String symbolCd) {
		this.symbolCd = symbolCd;
	}

	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
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
	
	public WithdrawalDTO convertToDto() {
		WithdrawalDTO dto = new WithdrawalDTO();
		dto.setWithdrawalId(withdrawalId);
		dto.setAmount(amount);
		dto.setStatus(status);
		dto.setWithdrawalMethod(methodId);
		CustomerAssetsDTO source = new CustomerAssetsDTO();
		source.setCustomerAssetsId(sourceId);
		dto.setSource(source);
		dto.setInputDate(inputDate);
		dto.setAcceptDate(acceptDate);
		dto.setUpdateDate(updateDate);
		dto.setCustomerId(customerId);
		
		return dto;
	}
}
