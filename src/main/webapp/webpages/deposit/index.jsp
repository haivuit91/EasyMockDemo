<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	div.assets.selected {
		background: #5676A5;
		color: #fff;
	}
	div.div-border {
		border: 1px solid;
		border-radius: 10px;
	}
</style>
<div class="container main-content">
	<h3 class="short_headline text-center"><span><s:text name="deposit.title" /></span></h3>
	<s:form id="frm-deposit" theme="simple" enctype="multipart/form-data">
		<fieldset>
			<s:token name="token"></s:token>
			<div>
				<div class="span3"></div>
				<div class="span6">
					<s:include value="../layout/alert_info.jsp" />
				</div>
			</div>
			<div class="row-fluid assets-container" style="display: inline-block;">
				<s:hidden id="destination-id" name="depositInfo.destinationId" />
				<s:hidden id="symbol-id" name="depositInfo.symbolId" />
				<s:hidden id="destination-name" name="depositInfo.destinationName" />
				<s:hidden id="symbol-cd" name="depositInfo.symbolCd" />
				<div class="span2"></div>
				<div class="span8" style="text-align: center; display: inline-block;">
					<s:iterator value="listAssetsInfo">
						<div data-assets-id="${customerAssetsId}" data-min-dep="${minDeposit}" data-symbol-id="${symbolId}" data-symbol-cd="${symbolCd}" data-destination-name="${assetsTypeName}" class="assets div-border" style="display: inline-block; padding: 10px 5px; width: 150px; margin-bottom: 10px;">
							<span><s:property value="assetsTypeName" /></span>
							<div>
								<span><s:property value="totalAmount" /> <s:property value="symbolCd" /></span>
							</div>
						</div>
					</s:iterator>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="span3"></div>
				<div class="span6">
					<div class="form-horizontal">
						<div class="control-group" style="margin-top: 20px;">
							<label class="col-sm-2 control-label control-label-form" for="deposit-method"><s:text name="deposit.label.method" /></label>
							<div class="controls">
								<s:select id="deposit-method" name="depositInfo.methodId" list="mapDepositMethods" headerKey="-1" headerValue="Select method" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="deposit-amount"><s:text name="deposit.label.amount" /></label>
							<div class="controls">
								<s:textfield id="deposit-amount" placeholder="Amount" name="depositInfo.amount" />
							</div>
							<div style="text-align: center;">
								<s:text name="deposit.label.min_deposit" /><span id="min-deposit"></span>
							</div>
						</div>
						<div class="btn-group control-group">
							<button type="button" class="btn custom-btn btn-primary btn-medium" id="btnDepositConfirm" onclick="submitAction('frm-deposit','deposit-confirm')"><s:text name="button.submit" /></button>
								<%-- <button class="btn btn-small btn-default" id="btnReset"><s:text name="button.reset"></s:text></button> --%>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</s:form>
</div>
<script type="text/javascript" src="<s:url value='../assets/custom/deposit.js'/>"></script>