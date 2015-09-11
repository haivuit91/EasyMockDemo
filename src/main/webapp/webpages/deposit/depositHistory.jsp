<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tabCustom" uri="/WEB-INF/taglib/customtag.tld" %>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="box span12">
			<div class="box-header">
				<h2><s:text name="deposit.history.title" /></h2>
			</div>
			<div class="box-content">
				<s:if test="%{listDeposit != null && listDeposit.size() > 0}">
					<table class="table table-striped table-bordered bootstrap-datatable datatable">
						<thead>
							<tr>
								<th width="70px;"><s:text name="deposit.history.label.deposit_id" /></th>
								<th><s:text name="deposit.history.label.amount" /> </th>
								<th><s:text name="deposit.history.label.base_currency" /> </th>
								<th><s:text name="deposit.history.label.status" /></th>
								<th><s:text name="deposit.history.label.method" /></th>
								<th><s:text name="deposit.history.label.destination_type" /></th>
								<th><s:text name="deposit.history.label.request_time"/> </th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="listDeposit" status="info">
								<s:if test="#info.odd == true">
									<tr class="odd">
								</s:if>
								<s:else>
									<tr class="even">
								</s:else>
										<td>
											<s:property value="depositId" />
										</td>
										<td><s:property value="amount"/></td>
										<td><s:property value="symbolCd"/></td>
										<td class="stt"><s:property value="%{mapDepositStatus.get(status)}"/></td>
										<td><s:property value="%{mapDepositMethods.get(methodId)}"/></td>
										<td><s:property value="destinationName"/></td>
										<td><s:property value="inputDate"/></td>
										<td width="60px" style="text-align: center;">
											<s:if test="%{status == 1 || status == 2}">
												<button type="button" class="cancel-deposit" data-deposit-id="${depositId}" data-status="${status}"><s:text name="button.cancel" /></button>
											</s:if>
										</td>
									</tr>
							</s:iterator>
						</tbody>
					</table>
					<%-- <div style="margin: auto; float: right;">
						<tabCustom:PaggingInfo actionName="deposit-history-search" pagingInfo="${pagingInfo}" fromId="frm-deposit-history" navAction="_goTo" />
					</div> --%>
				</s:if>
				<s:else>
					<h5><s:text name="deposit.history.no_result" /></h5>
				</s:else>
			</div>
			
		</div>				
	</div>
</div>
<script type="text/javascript" src="<s:url value='../assets/custom/deposit.js'/>"></script>