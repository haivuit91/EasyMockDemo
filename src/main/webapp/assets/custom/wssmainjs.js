	var socket;
	if (!window.WebSocket) {
	    window.WebSocket = window.MozWebSocket;
	}

	if (window.WebSocket) {
	    socket = new WebSocket("ws://162.243.219.251:8090/websocket");
	    socket.onopen = onopen;
	    socket.onmessage = onmessage;
	    socket.onclose = onclose;
	} else {
	    console.log("Cannot connect websocket");
	}

	function onopen(event) {
	    console.log("Websocket is opening");
	}

	function onmessage(event) {
		var data = event.data;
		var action = data.substring(0,1);
		if(action === '0') {
			responseChatMsg(data);
		} else {
			responsePlaceOrder(data);
		}
	  // appendTextArea(event.data);
	}

	function responseChatMsg(data){
		var msg = data.substring(1,data.length);
		var msgObject = JSON.parse(msg);
		var strMsg = "<li><b>"+msgObject.name +"</b>: "+msgObject.content+"</li>";
		$('#chatListItem').append(strMsg);
	}
	
	function responsePlaceOrder(data){
		console.log(data);
		var arrayStr = data.split("/");
		
		var sellStr = arrayStr[0];
		if(sellStr === "null") {
			
		} else {
			var objSell = JSON.parse(sellStr);
			var template = $.templates("#sellTmpl");
			template.link( "#sellBodyTmpl", objSell );
		}
		
		var buyStr = arrayStr[1];
		if(buyStr === "null"){
			
		} else {
			var objBuy = JSON.parse(buyStr);
			var template = $.templates("#buyTmpl");
			template.link( "#buyBodyTmpl", objBuy );
		}
	}
	function onclose(event) {
	}

	function appendTextArea(newData) {
		$('#chatListItem').append(newData);

	}

	
	function updateDataChange(data){
		console.log(data);
		$('#message').html('');
		$('#orderData').html('');
		$("#tmplPeople").tmpl(data).appendTo("#orderData");
	}
	

	function send(data) {
	    if (window.WebSocket) {
			if (socket.readyState == WebSocket.OPEN) {
			    socket.send(data);
			} else {
			    console.log("The socket is not open.");
			}
	    }
	}
	
    
 

