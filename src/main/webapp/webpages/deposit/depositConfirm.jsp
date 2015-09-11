<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container main-content">
	<s:form id="frm-deposit-submit" theme="simple" enctype="multipart/form-data">
		<fieldset>
			<s:token name="token"></s:token>
			<s:hidden name="depositInfo.methodId" />
			<s:hidden name="depositInfo.destinationId" />
			<s:hidden name="depositInfo.amount" />
			<div class="row-fluid" style="margin-top: 20px;">
				<div class="span3"></div>
				<div class="span6">
					<span><s:text name="deposit.title" /></span>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="span3"></div>
				<div class="span6 div-border">
					<div class="form-horizontal">
						<div class="control-group" style="margin-top: 20px;">
							<label class="col-sm-2 control-label control-label-form" for="deposit-destination"><s:text name="deposit.label.destination" /></label>
							<div class="controls">
								<s:property value="depositInfo.symbolName" />
							</div>
						</div>
						<div class="control-group" style="margin-top: 20px;">
							<label class="col-sm-2 control-label control-label-form" for="deposit-method"><s:text name="deposit.label.method" /></label>
							<div class="controls">
								<s:property value="%{mapDepositMethods.get(depositInfo.methodId)}" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="deposit-amount"><s:text name="deposit.label.amount" /></label>
							<div class="controls">
								<s:property value="depositInfo.amount" />
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button type="button" class="btn custom-btn btn-primary btn-medium" id="btnDepositSubmit" onclick="submitAction('frm-deposit-submit','deposit-submit')"><s:text name="button.confirm" /></button>
								<button type="button" class="btn custom-btn btn-medium" id="btnCancel"><s:text name="button.cancel"></s:text></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</s:form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnCancel').on('click', function() {
			window.location = 'index';
		});
	});
</script>