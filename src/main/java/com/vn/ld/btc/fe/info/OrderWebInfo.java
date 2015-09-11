package com.vn.ld.btc.fe.info;

import java.io.Serializable;

public class OrderWebInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Double orderId;
	private Double customerId;
	private Integer action;
	private Double amount;
	private Double pricePerUnit;
	private Double total;
	private Double feevalue;
	private String symbolPair;
	public Double getOrderId() {
		return orderId;
	}
	public void setOrderId(Double orderId) {
		this.orderId = orderId;
	}
	public Double getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Double customerId) {
		this.customerId = customerId;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getFeevalue() {
		return feevalue;
	}
	public void setFeevalue(Double feevalue) {
		this.feevalue = feevalue;
	}
	public String getSymbolPair() {
		return symbolPair;
	}
	public void setSymbolPair(String symbolPair) {
		this.symbolPair = symbolPair;
	}
}
