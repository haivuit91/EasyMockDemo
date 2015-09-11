<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<s:if test="%{errorMessage != null && errorMessage.size() >0 }" >
	<div class="alertError">
	      <div class="alert alert-error">
		      <s:iterator value="errorMessage" status="statusVar">
		      	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        <strong><s:property/></strong>
		      </s:iterator>
	      </div>
	</div>
</s:if> 
<s:else>
	<s:if test="%{successMessage != null && successMessage.size() > 0}">
		<div class="alertSuccess">
	      <div class="alert alert-success">
	       <s:iterator value="successMessage" status="statusVar">
		      	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        <strong><s:property/></strong>
		      </s:iterator>
	      </div>
	</div>
	</s:if>
</s:else>


