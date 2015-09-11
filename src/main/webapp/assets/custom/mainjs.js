/**
 * All function javascript call here
 */


/**
 * Submit from id with action
 * 
 * @param fromId
 * @param actionName
 */
function submitAction(fromId, actionName) {
	$("#" + fromId).attr('action', actionName);
	$("#" + fromId).attr('method', 'POST');
	$("#" + fromId).submit();
}


/**
 * Calculate order buy
 */
function calculateOrderBuy(){
	
}

function callAxjax_GetChangeData(symbol) {
	$.ajax({
		type : 'POST',
		url  : 'order/order-change-symbol',
		cache: true,
		data : {
			symbol : symbol
		},
		success : function (data) {
			
		}, error : function (errorData) {
			
		}
	});
}

function callAjax_Buy(){
	
}

function checkLogin(){
	var isTrue = true;
	if(isTrue) {
		if($('#juserEmail').val() == "") {
			isTrue = false;
		}	
		if($('#jpassword').val() == "") {
			isTrue = false;
		}
	}	
	if(isTrue) {
		$.ajax({
		 type: "POST",
		  url: '../admin/user-login-system',
		  data: {
			 juserEmail : $('#juserEmail').val(),
			 jpassWord: $('#jpassword').val()
		  },
		  beforeSend: function () {
			$('#loginInfo').html('<img src="../assets/images/ajax-loader.gif" />');  
		  },
		  success: function (data){
			  console.log(data);
			  if(data == '-1') {
				  $('#loginInfo').html('<b><i style="color: red; font-size:12px;">Email or password invalid!</i></b>');	  
			  } else if (data == '0') {
				  $('#loginInfo').html('<b><i style="color: red; font-size:12px;">Your email/password is wrong!</i></b>');
			  } else if (data == '1') {
				  // success
				  window.location="../order/customer-order";
			  } else if (data = '2') {
				  console.log("Need 2 factor authenticate.");
				  $('#2fa-auth-modal').modal();
			  }
		  }, error : function (errorData) {
				
		  }
		});
	}
	
	return false;
}

function logout_system() {
$.ajax({
	 type: "POST",
	  url: '../admin/user-logout-system',
	  success: function (data){
		  $('#menuCreate').html(data);
		  window.location="../order/customer-order";
	  }, error : function (errorData) {
			
	  }
	});
}

function ex_calculate(a, b) {
	"buy" == a && (tr = "b");
	"sell" == a && (tr = "s");
	var c = $("#" + tr + "_btc").val(), d = $("#" + tr + "_price").val();
	$("#" + tr + "_loading").html(loadingnorm);
	$.post(_url("order"), {
		calculate : a,
		btc_count : c,
		btc_price : d,
		pair : b
	}, function(a) {
		$("#" + tr + "_all").html(a.all);
		$("#" + tr + "_fee").html(a.fee);
		$("#" + tr + "_comm").html(a.comm);
		"y" == a.error ? $("#" + tr + "_error").show() : $("#" + tr + "_error")
				.hide();
		$("#" + tr + "_loading").html("");
	}, "json");
}


function verifyAuthenCode(params) {
	var data = params ? params.data : '',
		target = data ? data.target : '',
		enable = data ? data.enable : 0;
	
	if (!target.length) {
		return;
	}
	var $2faModal = $(target).closest('.modal'),
		$codeInput = $2faModal.length ? $2faModal.find('#code-input') : '',
		code = $codeInput.length ? $codeInput.val() : '';
	if (code) {
		$.ajax({
			url: '../account/verify-code?code=' + code + '&enable=' + enable,
			type: 'GET',
			dataType: 'json',
			complete: function (xhr) {
			    if (xhr.status != 200 || !xhr.responseText || xhr.responseText == '-1') {
			    	 console.log("Error while verifying Code.");
	//		    	 alert('Error!');
			    } else if (xhr.responseText == '0') {
			    	var $error = $2faModal.find('.error-msg');
			    	$error.length && $error.empty().html('Invalid authentication code.');
			    	console.log("Invalid code!");
			    } else {
			    	window.location="../order/customer-order";
			    	console.log("Succeed to verify the code!");
			    }
			}
		});
	} else {
		console.log("Enter the code!");
		alert('Enter the code!');
	}
};

function disableGauth() {
	$.ajax({
		url: '../account/disable-gauth',
		type: 'GET',
		dataType: 'json',
		complete: function (xhr) {
		    if (xhr.status != 200 || !xhr.responseText || xhr.responseText == '-1') {
		    	 console.log("Error while disable 2 factor authentication.");
		    } else if (xhr.responseText == '1') {
		    	window.location="../order/customer-order";
		    	console.log("Succeeded to disable 2 factor authentication!");
		    }
		}
	});
}

function displayGauthOptions() {
	var $2faModal = $('#2fa-options-modal'),
		$qrCode = $2faModal.find('#qr_code'),
		$error = $2faModal.length ? $2faModal.find('.error-msg') : '';
	$error.length && $error.empty();
	$.ajax({
		url: '../account/generate-qrcode',
		type: 'GET',
		dataType: 'image/png',
		complete: function (xhr) {
		    if (xhr.status != 200 || !xhr.responseText || xhr.responseText == '-1') {
		    	 console.log("Error while generating QR Code.");
		    	 alert('Error!');
		    } else {
		    	console.log("Generate QR Code successfully!");
		    	$qrCode.empty().html('<img width="125" height="125" src="data:image/png;base64,' + xhr.responseText + '" />');
		    	$2faModal.modal();
		    }
		}
	});
}


//Call custom javascript a here
$(function() {
	$("#frm-main-register").validationEngine();
	$("#jfrm_login").validationEngine();
	$('#frmLiveChat').validationEngine();
	$('#fromBuyOrder').validationEngine();
	$('#fromSellOrder').validationEngine();
	
	//loginkeypress
	$('#juserEmail').keypress(function(e) {
		 if(e.which == 13){
			checkLogin();
		 } 
	});
	$('#jpassword').keypress(function(e) {
		if(e.which == 13){
			checkLogin();
		 } 
	});
	
	$('#options-2fa').on('click', displayGauthOptions);
	$('#activate-auth-submit').on('click', {target: '#activate-auth-submit', enable: 1}, verifyAuthenCode);
	$('#auth-code-submit').on('click', {target:'#auth-code-submit', enable: 0}, verifyAuthenCode);
	$('#disable-2fa').on('click', disableGauth);
});