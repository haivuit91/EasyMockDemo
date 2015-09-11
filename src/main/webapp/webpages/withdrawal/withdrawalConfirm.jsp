<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container main-content">
	<h3 class="short_headline text-center"><span><s:text name="withdrawal.confirm.title" /></span></h3>
	<s:form id="frm-withdrawal-confirm" theme="simple" enctype="multipart/form-data">
		<fieldset>
			<s:token name="token"></s:token>
			<s:hidden name="withdrawalInfo.sourceId" />
			<s:hidden name="withdrawalInfo.amount" />
			<div class="row-fluid">
				<div class="span3"></div>
				<div class="span6 div-border">
					<div class="form-horizontal">
						<div class="control-group" style="margin-top: 20px;">
							<label class="col-sm-2 control-label control-label-form"><s:text name="withdrawal.label.source" /></label>
							<div class="controls">
								<s:property value="withdrawalInfo.sourceName" />
							</div>
						</div>
						<div class="control-group">
							<label class="col-sm-2 control-label control-label-form" for="withdrawal-amount"><s:text name="withdrawal.label.amount" /></label>
							<div class="controls">
								<s:property value="withdrawalInfo.amount" />
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button type="button" class="btn custom-btn btn-primary btn-medium" id="btnWithdrawalSubmit" onclick="submitAction('frm-withdrawal-confirm','withdrawal-submit')"><s:text name="button.confirm" /></button>
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