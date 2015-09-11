package com.vn.ld.btc.fe.model;

import com.vn.ld.api.order.context.OrderContext;

public class OrderModel extends BaseModel {

	private static final long serialVersionUID = -5591416645567852590L;

	private OrderContext orderContext = new OrderContext();

	public OrderContext getOrderContext() {
		return orderContext;
	}

	public void setOrderContext(OrderContext orderContext) {
		this.orderContext = orderContext;
	}
	
}
