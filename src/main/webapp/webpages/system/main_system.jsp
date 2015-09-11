<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script src="<s:url value='../assets/custom/wssmainjs.js' />" type="text/javascript" ></script>
	<s:hidden id="username" name="model.userName" />
	<div class="tab-pane" id="pills-basic">
		<div class="tabbable">
			<ul class="nav nav-pills" id="btcTabChart">
				<li class="active"><a href="#tabs4-pane1" data-toggle="tab">PLN/BTC</a></li>
				<li><a href="#tabs4-pane3" data-toggle="tab">PLN/LTC</a></li>
			</ul>
			<div class="tab-content">
					<div id="tabs4-pane1" class="tab-pane active">
						<div class="row-fluid">
							<div class="span12">
								<div class="well">
									<div id="contentStock" style="height: 500px; min-width: 500px"></div>
								</div>
							</div>
						</div>
					</div>
					<div id="tabs4-pane3" class="tab-pane">
					</div>
			</div>
		</div>
	</div>
	
	<%-- Start Sell or by instance --%>
	<div class="row-fluid">
		<div class=" span3">
			<div class="well">
			<h4 class="primary-color">My Reserves</h4>
				<table class="table table-striped table-hover responsiv">
					<thead>
						<tr>
							<th>Symbol</th>
							<th>Avaliable Amount</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>PLN</td>
							<td>10000</td>
						</tr>
						<tr>
							<td>BTC</td>
							<td>10.99</td>
						</tr>
						<tr>
							<td>LTC</td>
							<td>5.3</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class=" span3">
			<div class="well">
				<h4 class="primary-color">Sell</h4>
				<form id="fromSellOrder">
					<s:hidden name="symbolSellPair" id="sellSymbolPair" value="PLNBTC" />
					<div class="input-block-level">
						<s:textfield cssClass="validate[custom[numberFloat]]" name="sellAmmount" id="sellAmmount" placeholder="Amount" />
					</div>
					<div class="input-block-level">
						<s:textfield cssClass="validate[custom[numberFloat]]" name="priceSellUnitMoney" id="priceSellUnitMoney" placeholder="Price per" />
					</div>
					<div class="input-block-level">
						<s:textfield name="totalSell" id="totalSell" placeholder="Total"/>
					</div>
					<div class="input-block-level">
						<s:textfield name="feeSell" id="feeSell" placeholder="Fee" />
					</div>
					<div class="input-block-level">
						<button class="btn custom-btn btn-primary" id="calSellBTC">Calculate</button>
						<button class="btn custom-btn btn-primary" id="btnSellOrder">Sell</button>
						<button class="btn custom-btn btn-primary" id="btnSellClear">Clear</button>
					</div>
				</form>														
			</div>
		</div>
		<div class="span3">
			<div class="well">
				<h4 class="primary-color">Buy</h4>
				<form id="fromBuyOrder">
					<s:hidden name="symbolBuyPair" id="buySymbolPair" value="PLNBTC" />
					<div class="input-block-level">
						<s:textfield cssClass="validate[custom[numberFloat]]" name="buyAmmount" id="buyAmmount" placeholder="Amount" />
					</div>
					<div class="input-block-level">
						<s:textfield cssClass="validate[custom[numberFloat]]" name="priceBuyUnitMoney" id="priceBuyUnitMoney" placeholder="Price per" />
					</div>
					<div class="input-block-level">
						<s:textfield name="totalBuy" id="totalBuy" placeholder="Total" />
					</div>
					<div class="input-block-level">
						<s:textfield name="feeBuy" id="feeBuy" placeholder="Fee" />
					</div>
					<div class="input-block-level">
						<button class="btn custom-btn btn-primary" id="calFeeBuy">Calculate</button> 
						<button  class="btn custom-btn btn-primary" id="btnBuyOrder">Buy</button> 
						<button  class="btn custom-btn btn-primary" id="btnclearBuyOrder">Clear</button> 
					</div>
				</form>
			</div>
		</div>		
	</div>
	<!-- Start block table -->
	<div class="row-fluid">
		<div class="span5">
			<div class="well">
				<h4 class="primary-color">SELL ORDERS()</h4>
				<table class="table table-striped table-hover responsiv">
					<thead>
						<tr>
							<th>Price</th>
							<th>Amount</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody id="sellBodyTmpl"></tbody>
				</table>
				<script id="sellTmpl" type="text/x-jsrender">
						<tr>
							<td data-link="unitPrice"></td>
							<td data-link="originalVolume"></td>
							<td data-link="totalMoney"></td>
						</tr>
				</script>
			</div>
		</div>
		<div class="span5">
			<div class="well">
			<h4 class="primary-color">BUY ORDERS()</h4>
				<table class="table table-striped table-hover responsiv">
					<thead>
						<tr>
							<th>Price</th>
							<th>Amount</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody id="buyBodyTmpl"></tbody>
				</table>
				<script id="buyTmpl" type="text/x-jsrender">
						<tr>
							<td data-link="unitPrice"></td>
							<td data-link="originalVolume"></td>
							<td data-link="totalMoney"></td>
						</tr>
				</script>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span10">
		<div class="well">
			<h4 class="primary-color">TRADE HISTORY (Last 50 Trades)</h4>
				<table class="table table-striped table-hover responsiv">
					<thead>
						<tr>
							<th>Date</th>
							<th>Type</th>
							<th>Unit Price (USD)</th>
							<th>Amount (BTC)</th>
							<th>Total (USD)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>17-02-14 16:22:52</td>
							<td>Sell</td>
							<td>495.99999996</td>
							<td>0.02</td>
							<td>9.91999999</td>
						</tr>
						<tr>
							<td>17-02-14 16:22:52</td>
							<td>Sell</td>
							<td>495.99999996</td>
							<td>344</td>
							<td>233</td>
						</tr>
						<tr>
							<td>17-02-14 15:38:40</td>
							<td>Sell</td>
							<td>222</td>
							<td>21</td>
							<td>46343</td>
						</tr>
						<tr>
							<td>17-02-14 15:38:40</td>
							<td>Sell</td>
							<td>1234</td>
							<td>23</td>
							<td>24455</td>
						</tr>
						<tr>
							<td>17-02-14 15:38:40</td>
							<td>Sell</td>
							<td>495.99999996</td>
							<td>0.02</td>
							<td>9.91999999</td>
						</tr>
						<tr>
							<td>17-02-14 15:38:40</td>
							<td>Buy</td>
							<td>495.99999996</td>
							<td>0.02</td>
							<td>9.91999999</td>
						</tr>
						<tr>
							<td>17-02-14 15:21:04</td>
							<td>Buy</td>
							<td>17-02-14 15:20:39</td>
							<td>0.02</td>
							<td>9.91999999</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%-- 
	<div class="row-fluid">
		<div class="span4">
			<form  method="post" id="frmLiveChat">
				<div class="nav-header">Live chat</div>
				<div class="row-fluid">
					<div class="row-fluid" id="chat_content">
						<ul style="list-style: none;" id="chatListItem">
						</ul>
						
					</div>
					<div class="row-fluid" style="padding-top: 10px;">
						<input type="text" id="msgTxt" maxlength="256" placeholder="Send a message!" style="float: left; width: 280px;"/>
					    <button type="button" class="btn" onclick="sendData();" style="float: left; margin-left: 5px;">Send</button>
					</div>
				</div>
			</form>
		</div>
	</div>--%>
	<div class="shout_box_chat">
		<div class="header_chat">Live chat<div class="close_btn_chat">&nbsp;</div></div>
		  <div class="toggle_chat">
		  <div class="message_box">
		  		<ul style="list-style: none;" id="chatListItem">
			    </ul>
		    </div>
		    <div class="user_info">
			    <input name="shout_message" id="msgTxt" type="text" placeholder="Type Message Hit Enter" maxlength="100" /> 
		    </div>
		</div>
	</div>
	<!-- End block table -->
<%--End  sell or by instance --%>
<script type="text/javascript">
$(document).ready(function() {
	//Set readonly textbox
	setReadOnlyControl();
	
	var isLogin = $('#isLogin').val();
	if(isLogin != null && isLogin == 1) {
		$('#frmLiveChat').show();
	} else {
		$('#frmLiveChat').hide();
	}

	//Tab chart
	$('#btcTabChart a').click(function(e){
		e.preventDefault();
		var data = e.currentTarget.text;
		console.log(e);
		console.log(e.currentTarget);
		changeLabelNameWhenTabChange(data);
	});

	//Check fee sell
	$('#calSellBTC').click(function(e){
		e.preventDefault();
		var isLogin = $('#isLogin').val();
		if(isLogin == 1) {
			callFeeSell();
		}else {
			$.confirm({
		        title:"Bitwalor Information!",
		        text: "Please login before place order. Thanks you!",
		        confirmButton: "OK"
		    });
		}	
	});

	$('#calFeeBuy').click(function(e){
		e.preventDefault();
		var isLogin = $('#isLogin').val();
		if(isLogin == 1) {
			callFeeBuy();
		} else {
			$.confirm({
		        title:"Bitwalor Information!",
		        text: "Please login before place order. Thanks you!",
		        confirmButton: "OK"
		    });
		}	
	});

	//Place sell order
	$('#btnSellOrder').click(function(e){
		e.preventDefault();
		callAjax_Sell_BTC();
	});

	//Place buy order
	$('#btnBuyOrder').click(function(e){
		e.preventDefault();
		callAjax_Buy_BTC();
	});

	$.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=aapl-ohlcv.json&callback=?', function(data) {

		// split the data set into ohlc and volume
		var ohlc = [],
			volume = [],
			dataLength = data.length;
			
		for (i = 0; i < dataLength; i++) {
			ohlc.push([
				data[i][0], // the date
				data[i][1], // open
				data[i][2], // high
				data[i][3], // low
				data[i][4] // close
			]);
			
			volume.push([
				data[i][0], // the date
				data[i][5] // the volume
			])
		}

		// set the allowed units for data grouping
		var groupingUnits = [[
			'week',                         // unit name
			[1]                             // allowed multiples
		], [
			'month',
			[1, 2, 3, 4, 6]
		]];

		// create the chart
		$('#contentStock').highcharts('StockChart', {
		    
		    rangeSelector: {
		        selected: 1
		    },

		    title: {
		        text: 'PLN/BTC'
		    },

		    yAxis: [{
		        title: {
		            text: 'Price'
		        },
		        height: 200,
		        lineWidth: 2
		    }, {
		        title: {
		            text: 'Volume'
		        },
		        top: 300,
		        height: 100,
		        offset: 0,
		        lineWidth: 2
		    }],
		    
		    series: [{
		        type: 'candlestick',
		        name: 'PLN/BTC',
		        data: ohlc,
		        dataGrouping: {
					units: groupingUnits
		        }
		    }, {
		        type: 'column',
		        name: 'Volume',
		        data: volume,
		        yAxis: 1,
		        dataGrouping: {
					units: groupingUnits
		        }
		    }]
		});
	});

	$('#msgTxt').keypress(function(e) {
		 if(e.which == 13){
			  	sendData();
			  	return false;
			} 
		});

	//toggle hide/show shout box
	$(".close_btn_chat").click(function (e) {
		//get CSS display state of .toggle_chat element
		var toggleState = $('.toggle_chat').css('display');
		
		//toggle show/hide chat box
		$('.toggle_chat').slideToggle();
		
		//use toggleState var to change close/open icon image
		if(toggleState == 'block')
		{
			$(".header_chat div").attr('class', 'open_btn_chat');
		}else{
			$(".header_chat div").attr('class', 'close_btn_chat');
		}
		 
		 
	});
});

//Send data
function sendData(){
	var name = $('#username').val();
	if(name != null && name.length > 0) {
		var msg = $('#msgTxt').val();
		if(msg != null && msg.length > 0) {
			var chatMsg = new Object();
			chatMsg.name = name;
			chatMsg.content = msg;
			var msgContent = "0"+JSON.stringify(chatMsg);
			send(msgContent);
			$('#msgTxt').val(null);
		}
	}
}

function setReadOnlyControl(){
	$('#totalSell').prop('readonly', true);
	$('#feeSell').prop('readonly', true);
	
	$('#totalBuy').prop('readonly', true);
	$('#feeBuy').prop('readonly', true);
}


function changeLabelNameWhenTabChange(name) {
	console.log(name);
	var newName = name.replace("/","");
	//Set new name symbol
	$('#buySymbolPair').val(newName);
	$('#sellSymbolPair').val(newName);
}

function callFeeSell(){
	var sellAmount = $('#sellAmmount').val();
	var priceSellUnit = $('#priceSellUnitMoney').val();
	var symborPair = $('#sellSymbolPair').val();
	if(sellAmount != null && sellAmount > 0 &&  priceSellUnit != null && priceSellUnit > 0){
		if(symborPair != null && symborPair.length > 0) {
			$.ajax({
				type : 'POST',
				url : 'order/order-sell-fee',
				cache : false,
				data : {
					action : 1,
					ammount: sellAmount,
					pricePer : priceSellUnit,
					symbol : symborPair
				},
				success : function (data) {
					var obj = jQuery.parseJSON(data);
					if(obj != null) {
						$('#totalSell').val(obj.total);
						$('#feeSell').val(obj.feevalue);
					}
			
				}, error : function (errorData) {
					console.log(errorData);
				}
			});
		}
	}
}

/**
 * Call fee buy
 */
function callFeeBuy(){
	var sellAmount = $('#buyAmmount').val();
	var priceSellUnit = $('#priceBuyUnitMoney').val();
	var symborPair = $('#buySymbolPair').val();
	if(sellAmount != null && sellAmount > 0 &&  priceSellUnit != null && priceSellUnit > 0){
		if(symborPair != null && symborPair.length > 0) {
			$.ajax({
				type : 'POST',
				url : 'order/order-sell-fee',
				cache : false,
				data : {
					action : 1,
					ammount: sellAmount,
					pricePer : priceSellUnit,
					symbol : symborPair
				},
				success : function (data) {
					var obj = jQuery.parseJSON(data);
					if(obj != null) {
						$('#totalBuy').val(obj.total);
						$('#feeBuy').val(obj.feevalue);
					}
			
				}, error : function (errorData) {
					console.log(errorData);
				}
			});
		}
	}
}

function callAjax_Sell_BTC() {
	var isLogin = $('#isLogin').val();
	if(isLogin == 1) {
		var sAmount = $('#sellAmmount').val();
		var sPricePer = $('#priceSellUnitMoney').val();
		var stotal = $('#totalSell').val();
		var sfeeValue = $('#feeSell').val();
	
		var isSell = true;
		if(sAmount == null || sAmount < 0 || 
		   sPricePer == null || sPricePer < 0 || 
		   stotal == null || stotal < 0 || 
		   sfeeValue == null || sfeeValue < 0) {
			isSell = false;
		}
			
		if(isSell) {
			var symborPair = $('#sellSymbolPair').val();
			$.ajax({
				type : 'POST',
				url : 'order/place-order',
				cache : false,
				data : {
					action : 1,
					ammount: sAmount,
					pricePer : sPricePer,
					symbol : symborPair,
					totalMoney : stotal,
					feeMoney : sfeeValue
				},
				success : function (data) {
					console.log(data);
					clearDataSell();
				}, error : function (errorData) {
					
				}
			});
		}
		
	} else {
		$.confirm({
	        title:"Bitwalor Information!",
	        text: "Please login before place order. Thanks you!",
	        confirmButton: "OK"
	    });
	}	
}

function callAjax_Buy_BTC() {
	var isLogin = $('#isLogin').val();
	if(isLogin == 1) {
		var sAmount = $('#buyAmmount').val();
		var sPricePer = $('#priceBuyUnitMoney').val();
		var stotalBuy = $('#totalBuy').val();
		var sfeeMoney = $('#feeBuy').val();
		
		var isBuy = true;
		if(sAmount == null || sAmount < 0 ||
		   sPricePer == null || sPricePer < 0 ||
		   stotalBuy == null || stotalBuy < 0 ||
		   sfeeMoney == null || sfeeMoney < 0 ) {
			isBuy = false;
		}
		

		if(isBuy) {
			var symbol = $('#buySymbolPair').val();
			$.ajax({
				type : 'POST',
				url : 'order/place-order',
				cache : false,
				data : {
					action : 2,
					ammount: sAmount,
					pricePer : sPricePer,
					symbol : symbol,
					totalMoney : stotalBuy,
					feeMoney : sfeeMoney
				},
				success : function (data) {
					console.log(data);
					clearDataBuy();
				}, error : function (errorData) {
					
				}
			});
		}	
		
	} else {
		$.confirm({
	        title:"Bitwalor Information!",
	        text: "Please login before place order. Thanks you!",
	        confirmButton: "OK"
	    });
	}

}

function clearDataSell(){
	$('#sellAmmount').val(null);
	$('#priceSellUnitMoney').val(null);
	$('#totalSell').val(null);
	$('#feeSell').val(null);
}

function clearDataBuy(){
	$('#buyAmmount').val(null);
	$('#priceBuyUnitMoney').val(null);
	$('#totalBuy').val(null);
	$('#feeBuy').val(null);
}

</script>
