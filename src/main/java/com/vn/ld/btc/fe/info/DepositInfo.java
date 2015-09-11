package com.vn.ld.btc.fe.info;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.vn.ld.api.dto.SymbolDTO;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.api.transfer.dto.DepositDTO;
import com.vn.ld.common.ServiceType;

public class DepositInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String depositId;
	private String amount;
	private Integer status;
	private String statusString;
	private Integer methodId;
	private String methodName;
	private Integer destinationId;
	private Integer symbolId;
	private String symbolCd;
	private String destinationName;
	private Date inputDate;
	private Date acceptDate;
	private Date updateDate;
	private Integer customerId;
	
	public DepositInfo() {
	}
	
	public DepositInfo(DepositDTO depositDto) {
		this.depositId = depositDto.getDepositId();
		this.amount = depositDto.getAmount().toString();
		this.status = depositDto.getStatus();
		this.statusString = "";
		this.methodId = depositDto.getDepositMethod();
		this.methodName = "";
		CustomerAssetsDTO dest = depositDto.getDestination();
		if (dest != null) {
			this.destinationId = dest.getCustomerAssetsId();
			SymbolDTO symbol = dest.getSymbol();
			if (symbol != null) {
				this.symbolId = symbol.getSymbolId();
				this.symbolCd = symbol.getSymbolCd();
				this.destinationName = ServiceType.getById(this.symbolId).getTypeName();
			}
		}
		this.inputDate = depositDto.getInputDate();
		this.acceptDate = depositDto.getAcceptDate();
		this.updateDate = depositDto.getUpdateDate();
		this.customerId = depositDto.getCustomerId();
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
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

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
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
	
	public DepositDTO convertToDto() {
		DepositDTO dto = new DepositDTO();
		dto.setDepositId(depositId);
		dto.setAmount(new BigDecimal(amount));
		dto.setStatus(status);
		dto.setDepositMethod(methodId);
		CustomerAssetsDTO destination = new CustomerAssetsDTO();
		destination.setCustomerAssetsId(destinationId);
		dto.setDestination(destination);
		dto.setInputDate(inputDate);
		dto.setAcceptDate(acceptDate);
		dto.setUpdateDate(updateDate);
		dto.setCustomerId(customerId);
		return dto;
	}
}
