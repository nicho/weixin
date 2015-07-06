<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>主页</title>
<script type="text/javascript">
function updateStatus(fal)
{
	$("#status").val(fal);
	$("#inputForm").submit();
	
}

$(document).ready(function() {
	//聚焦第一个输入框
	$("#approvalOpinion").focus();
	//为inputForm注册validate函数
	$("#inputForm").validate();
});
</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		<form id="inputForm" action="${ctx}/ApplyThreeAdmin/auditPass" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${applyThreeAdmin.id}"/>
 <fieldset>
			<legend><small>正在审批的资料</small></legend>
			<div class="control-group">
				<label for="loginName" class="control-label">姓名:</label>
				<div class="controls">
					<input type="text" id="userName" name="userName" class="input-large required" value="${applyThreeAdmin.userName }" disabled="disabled" />
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">证件号:</label>
				<div class="controls">
					<input type="text" id="ssnumber" name="ssnumber" class="input-large required" value="${applyThreeAdmin.ssnumber }" disabled="disabled"/>
				</div>
			</div>
	 	<div class="control-group">
				<label for="name" class="control-label">地址:</label>
				<div class="controls"> 
					<input type="text" id="address" name="address" class="input-large required" value="${applyThreeAdmin.address }"  disabled="disabled"/>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">上级分销商:</label>
				<div class="controls">
					<input type="text" id="upuserName" name="upuserName" class="input-large required"  value="${applyThreeAdmin.upuser.loginName }"  disabled="disabled"/>
				</div>
			</div>
			 <div class="control-group">
				<label for="plainPassword" class="control-label">申请理由:</label>
				<div class="controls"> 
					<textarea rows="5" cols="5" name="description" class="required" disabled="disabled" style="  width: 500px;"> ${applyThreeAdmin.description } </textarea>
				</div>
			</div>
			
		
			<input type="hidden" id="status" name="status" value="" >
			 <div class="control-group">
				<label for="plainPassword" class="control-label">审批意见:</label>
				<div class="controls"> 
					<textarea rows="5" cols="5" name="approvalOpinion" class="required" style="  width: 500px;">  </textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn1" class="btn btn-primary" type="button" value="通过" onclick="updateStatus('pass')"/>&nbsp;	
				<input id="submit_btn2" class="btn btn-primary" type="button" value="驳回" onclick="updateStatus('reject')"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
	
		</fieldset>
				</form>
</body>
</html>
