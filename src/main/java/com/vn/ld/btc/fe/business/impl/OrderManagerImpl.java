package com.vn.ld.btc.fe.business.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vn.ld.api.order.dto.OrderDTO;
import com.vn.ld.api.order.service.RSOrderService;
import com.vn.ld.api.user.dto.CustomerServiceDTO;
import com.vn.ld.btc.fe.business.IOrderManager;

@Component("orderManager")
public class OrderManagerImpl implements IOrderManager {
	private Logger log = Logger.getLogger(OrderManagerImpl.class);
	
	@Autowired
	private RSOrderService rsOrderService;

	@Override
	public boolean placeOderToSystem(OrderDTO orderDTO, Integer action, String serviceName) {
	log.info("placeOderToSystem at OrderManagerImpl");
	 CustomerServiceDTO customerServiceDTO = getCustomerService(orderDTO.getCustomer().getCustomerId(), serviceName);
	 if(customerServiceDTO != null && customerServiceDTO.getServiceName() != null) {
		 orderDTO.setCustomerService(customerServiceDTO);
	 } else {
		 log.info("Cannot get customer service dto");
		 return false;
	 }
	 return	rsOrderService.placeOrder(orderDTO);
	}
	
	private CustomerServiceDTO getCustomerService(Integer customerId,String serviceName) {
		return rsOrderService.getCustomerServicesDTO(customerId, serviceName);
	}	
	
	
}
