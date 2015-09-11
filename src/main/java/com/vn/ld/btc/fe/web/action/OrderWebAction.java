package com.vn.ld.btc.fe.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.vn.ld.api.order.dto.OrderDTO;
import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.btc.fe.business.IOrderManager;
import com.vn.ld.btc.fe.info.OrderWebInfo;
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.btc.fe.model.OrderModel;
import com.vn.ld.btc.fe.security.UserOnlineUtils;
import com.vn.ld.common.Constants;

public class OrderWebAction extends BaseAction<OrderModel> {
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(OrderWebAction.class);
	private OrderModel model = new OrderModel();
	@Autowired
	private IOrderManager orderManager;
	public String initOrderScreen(){
		initScreen();
		return SUCCESS;
	}
	
	
	private void initScreen() {
		WebappUser wepapp = UserOnlineUtils.getWebappUser();
		if(wepapp != null){
			model.setUserName(UserOnlineUtils.getWebappUser().getUsername());
		}
	}
	
	/**
	 * Get order fee
	 */
	public void getFeeOrder(){
		try {
			
			String action = request.getParameter(Constants.OrderInfo.ACTION);
			String amount = request.getParameter(Constants.OrderInfo.AMOUNT);
			String pricePer = request.getParameter(Constants.OrderInfo.PRICE_PER);
			String symbol = request.getParameter(Constants.OrderInfo.SYMBOL_PAIR);
			LOGGER.info("Get fee amount with action: " + action + " amount: "+amount + " Price per unit "+pricePer + " Symbol "+symbol);
			
			if(symbol != null && !symbol.isEmpty()) {
				Double configFee = getFeeViaSymbolFair(symbol);
				
				Double amountNum = null;
				if(amount != null && !amount.isEmpty()) {
					amountNum = Double.valueOf(amount);
				}
				
				Double pricePerNum = null;
				if(pricePer != null && !pricePer.isEmpty()) {
					pricePerNum = Double.valueOf(pricePer);
				}
				
				if(amountNum != null && amountNum > 0 && pricePerNum != null && pricePerNum > 0) {
					if(configFee != null && configFee > 0) {
						Double total = amountNum*pricePerNum;
						Double newFeeValue = configFee*total/100;
						Double newTotal = total - newFeeValue;
						OrderWebInfo orderWebInfo = new OrderWebInfo();
						orderWebInfo.setAction(new Integer(action));
						orderWebInfo.setAmount(amountNum);
						orderWebInfo.setPricePerUnit(pricePerNum);
						orderWebInfo.setTotal(newTotal);
						orderWebInfo.setFeevalue(newFeeValue);
						orderWebInfo.setSymbolPair(symbol);
						Gson gson = new Gson();
						String json = gson.toJson(orderWebInfo);
						PrintWriter printWriter = response.getWriter();
						printWriter.print(json);
						printWriter.flush();
						printWriter.close();
					}
				}
			}
			
			
			
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * TODO Get config fee
	 * @param symbolFair
	 * @return
	 */
	private Double getFeeViaSymbolFair(String symbolFair) {
		return 0.003;
	}
	
	/**
	 * Customer sell btc
	 * @return
	 */
	public String customerPlaceOrder() {
		try {
			LOGGER.info("Start place order");
			initScreen();
			String action = request.getParameter(Constants.OrderInfo.ACTION);
			String amount = request.getParameter(Constants.OrderInfo.AMOUNT);
			String pricePer = request.getParameter(Constants.OrderInfo.PRICE_PER);
			String totalMoney = request.getParameter(Constants.OrderInfo.TOTAL_MONEY);
			String feeMoney  = request.getParameter(Constants.OrderInfo.FEE_MONEY);
			String symbol = request.getParameter(Constants.OrderInfo.SYMBOL_PAIR);
			WebappUser user = UserOnlineUtils.getWebappUser();
			if(user != null) {
				//Validate object customer
				boolean checkStt = validateCustomer(action, amount, pricePer, totalMoney, feeMoney);
				if (!checkStt) {
					return ERROR;
				} else {
					OrderDTO orderDTO = new OrderDTO();
					
					orderDTO.setAssetType(1);
					orderDTO.setOriginalVolume(new BigDecimal(amount));
					orderDTO.setRemainVolume(new BigDecimal(amount));
					orderDTO.setInputDate(new Date(System.currentTimeMillis()));
					orderDTO.setUnitPrice(new BigDecimal(pricePer));
					orderDTO.setType(new Integer(action));
					orderDTO.setTotalMoney(new BigDecimal(totalMoney));
					orderDTO.setFeeMoney(new BigDecimal(feeMoney));
					
					//Set customer object
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setCustomerId(user.getUserOnlineInfo().getUserId());
					orderDTO.setCustomer(customerDTO);
					LOGGER.info("Place order with customer ID: " +user.getUserOnlineInfo().getUserId());
					LOGGER.info("Order Type: "+ action + " Order symbol " + symbol +" Order original "+amount + " Order unitPrice " + pricePer);
					boolean orderStt = orderManager.placeOderToSystem(orderDTO, new Integer(action), symbol);
					if(orderStt) {
						PrintWriter printWriter = response.getWriter();
						printWriter.print("1");
						printWriter.flush();
						printWriter.close();
					} else {
						PrintWriter printWriter = response.getWriter();
						printWriter.print("0");
						printWriter.flush();
						printWriter.close();
					}
					LOGGER.info("END PLACE ORDER");
				}
			} else {
				
			}
			
		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	 */
	public void calFeeAmmount() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean validateCustomer(String action, String ammount, String pricePer, String totalMoney, String feeMoney){
		model.clearErrorMesage();
		//Validate action
		if(!action.isEmpty()) {
			
		}
		return true;
	}
	
	/**
	 * When customer click action change symbol to get more data
	 * @return
	 */
	public String customerChangeSymbolData() {
		return SUCCESS;
	}
	
	//////////////////////////////////
	@Override
	public OrderModel getModel() {
		return model;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void setModel(OrderModel model) {
		this.model = model;
	}
	
}
