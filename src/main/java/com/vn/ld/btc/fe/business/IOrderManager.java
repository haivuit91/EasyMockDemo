package com.vn.ld.btc.fe.business;

import com.vn.ld.api.order.dto.OrderDTO;

public interface IOrderManager {
	public boolean placeOderToSystem(OrderDTO orderDTO, Integer action, String serviceName);
	
}
