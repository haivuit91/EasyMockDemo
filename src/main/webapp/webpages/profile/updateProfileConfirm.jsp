<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--  Start form register -->
<div class="container main-content">
 	<h3 class="short_headline text-center"><span>User Profile</span></h3>
	<s:form id="frm-update-profile-confirm" theme="simple" enctype="multipart/form-data">
		<fieldset>
			<s:token name="token" />
			<s:include value="../layout/alert_info.jsp" />
			<s:hidden id="hid-email" value="%{profileInfo.email}" name="profileInfo.email" />
			<s:hidden id="hid-username" value="%{profileInfo.username}" name="profileInfo.username" />
			<s:hidden id="hid-firstname" value="%{profileInfo.firstName}" name="profileInfo.firstName" />
			<s:hidden id="hid-lastname" value="%{profileInfo.lastName}" name="profileInfo.lastName" />
			<s:hidden id="hid-lastname" value="%{profileInfo.phoneNumber}" name="profileInfo.phoneNumber" />
			<div class="row-fluid" style="width: 50%; margin: 0 auto;">
				<div class="span12">
					<div class="form-horizontal">
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >Email</label>
							<div class="controls">
								<s:property value="profileInfo.email" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >Username</label>
							<div class="controls">
								<s:property value="profileInfo.username" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >First name</label>
							<div class="controls">
								<s:property value="profileInfo.firstName" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >Last name</label>
							<div class="controls">
								<s:property value="profileInfo.lastName" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >Phone number</label>
							<div class="controls">
								<span class="profile-info"><s:property value="profileInfo.phoneNumber" /></span>
							</div>
						</div>
						<div class="control-group" style="text-align: center;">
							<button type="button" style="width: 97px;" class="btn btn-primary btn-medium" id="btn-update-confirm" onclick="submitAction('frm-update-profile-confirm','update-profile-confirm')"><s:text name="button.confirm" /></button>
							<button type="button" style="width: 97px;" class="btn btn-medium" id="btn-cancel-confirm" ><s:text name="button.cancel" /></button>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</s:form>
</div>
<script type="text/javascript" src="<s:url value='../assets/custom/profile.js'/>"></script>