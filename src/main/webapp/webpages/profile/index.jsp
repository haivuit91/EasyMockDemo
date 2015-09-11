<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--  Start form register -->
<div class="container main-content">
 	<h3 class="short_headline text-center"><span>User Profile</span></h3>
	<s:form id="frm-update-profile" theme="simple" enctype="multipart/form-data">
		<fieldset>
			<s:token name="token" />
			<s:include value="../layout/alert_info.jsp" />
			<div id="profile-container" class="row-fluid" style="width: 50%; margin: 0 auto;">
				<div class="span12">
					<div class="form-horizontal">
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" >Email</label>
							<div class="controls">
								<span><s:property value="profileInfo.email" /></span>
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="username-input" >Username</label>
							<div class="controls">
								<span class="profile-info"><s:property value="profileInfo.username" /></span>
								<s:textfield id="username-input" name="profileInfo.username" cssClass="hid profile-input"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="firstName-input" >First name</label>
							<div class="controls">
								<span class="profile-info"><s:property value="profileInfo.firstName" /></span>
								<s:textfield id="firstName-input" name="profileInfo.firstName" cssClass="hid profile-input"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="lastName-input" >Last name</label>
							<div class="controls">
								<span class="profile-info"><s:property value="profileInfo.lastName" /></span>
								<s:textfield id="lastName-input" name="profileInfo.lastName" cssClass="hid profile-input"></s:textfield>
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="lastName-input" >Phone number</label>
							<div class="controls">
								<span class="profile-info"><s:property value="profileInfo.phoneNumber" /></span>
								<s:textfield id="phoneNumber-input" name="profileInfo.phoneNumber" cssClass="hid profile-input"></s:textfield>
							</div>
						</div>
						<div class="control-group" style="text-align: center;">
							<button type="button" class="btn btn-primary btn-medium" id="btn-edit-profile" style="width: 97px;">Edit Profile</button>
							<button type="button" class="btn btn-primary btn-medium hid" id="btn-save-profile" style="width: 97px;" onclick="submitAction('frm-update-profile','update-profile')">Save Profile</button>
							<button type="button" class="btn btn-medium hid" id="btn-cancel-profile" style="width: 97px;">Cancel</button>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</s:form>
</div>
<script type="text/javascript" src="<s:url value='../assets/custom/profile.js'/>"></script>