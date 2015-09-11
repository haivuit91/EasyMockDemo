<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--  Start form register -->
<div class="container main-content">
	 <div class="form-register">
	 	 <h3 class="short_headline text-center"><span>Join Us</span></h3>
	 	 <div class="social text-center">
		 	 <div><!--div is for ie7-->
				<a class="fc-webicon facebook large round" href="#" title="Facebook"></a> 
				<a class="fc-webicon twitter large round" href="#" title="Twitter"></a> 
				<a class="fc-webicon googleplus large round" href="#" title="Google"></a>
			</div>
		  </div>	
		  <hr class="empty">
		  <p class="text-center">Don't have one of these accounts? Use the sign up form below.</p>
		
		<s:form id="frm-main-register" theme="simple" enctype="multipart/form-data">
			<fieldset>
				<s:token name="token" />
				<s:hidden id="isSubmit" value="0" />
				<s:include value="../layout/alert_info.jsp" />
				<s:textfield id="email" name="accountContext.email" type="email" cssClass=" validate[required,custom[email]] input-block-level" placeholder="Email (*)" />
				<s:password id="password" cssClass=" validate[required,minSize[6],custom[onlyLetterNumber]]  input-block-level" name="accountContext.password" placeholder="Password (*)" />
				<s:password id="retypePassword" cssClass=" validate[required,equals[password]] input-block-level" placeholder="Retype password (*)" />
				<%-- <s:textfield id="phone" name="accountContext.phoneNumber" cssClass=" validate[custom[phone]] input-block-level" placeholder="Phone number"/>
				<s:textfield id="zipCode" name="accountContext.zipCode" cssClass=" validate[custom[onlyNumberSp]] input-block-level"  placeholder="Zip Code" />
				<s:textfield id="firstName" name="accountContext.firstName" cssClass=" validate[custom[onlyLetterSp]] input-block-level" placeholder="First Name" />
				<s:textfield id="lastName" name="accountContext.lastName" cssClass=" validate[custom[onlyLetterSp]] input-block-level"  placeholder="Last Name"/>
				<s:textfield id="birthday" name="accountContext.birthday" cssClass=" validate[custom[dateFormat]] input-block-level" placeholder="Birthday" />
				<s:textfield id="address" name="accountContext.address" cssClass=" input-block-level" placeholder="Address"  /> --%>
				<s:textfield id="userName" cssClass="input-block-level" name="accountContext.userName" placeholder="User name"/>
				<p style="display: inline-block;" class=" input-block-level">
				   <img alt="src" src="../Captcha.jpg" style="float: left;" id="imageCaptcha" /><a href="#" style="text-decoration: none; float: left;" onclick="return refreshimg();"><i class="e-icon-arrows-ccw"></i></a>
				</p>
				<p style="display: inline;"><s:textfield id="captchaComfirm" cssClass="validate[required,minSize[6]" style="width: 110px; margin-top: -40px;" maxlength="6" size="6" placeholder="Security code (*)" /><label id="captchaResponse" style="margin-top: -20px;color: red;font-size: 13px;"></label></p>
					
				<label class="checkbox"><s:checkbox name="accountContext.termStt" cssClass=" validate[required]" />I agree with terms! </label>
				
				<button class="btn custom-btn btn-primary btn-large" onclick="sendInfoRegister();">Join</button>
			</fieldset>
		</s:form>
	 </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('#captchaComfirm').change(function(){
		var jcaptcha = $('#captchaComfirm').val();
		if(jcaptcha.length == 6) {
			$.ajax({
				 type: "POST",
				  url: '../account/captcha-confirm',
				  data: {
					  c: jcaptcha
				  },
				  success: function (data){
					 if(data == 0) {
						$('#captchaResponse').html('Security code is invalid');
						$('#captchaComfirm').val(null);
						$('#captchaComfirm').focus();
					 } else {
						 $('#isSubmit').val(1);
						 $('#captchaResponse').html('');
					 }
				  }, error : function (errorData) {
						console.log(errorData);
				  }
				});
		} else {
			$('#captchaComfirm').focus();
		}
	});
});

function sendInfoRegister() {
	var isSubmit = $('#isSubmit').val();
	if(isSubmit == 1) {
		submitAction('frm-main-register','customer-registration');	
	}
}
function refreshimg() {
	$("#imageCaptcha").attr("src","../Captcha.jpg?"+Date.now());
	return false;
}
</script>
