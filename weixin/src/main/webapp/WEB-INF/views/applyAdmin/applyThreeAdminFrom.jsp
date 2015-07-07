<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>申请成为分销商</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#userName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/ApplyThreeAdmin/${action}" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>申请成为分销商</small></legend>
			<div class="control-group">
				<label for="loginName" class="control-label">姓名:</label>
				<div class="controls">
					<input type="text" id="userName" name="userName" class="input-large required"  />
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">证件号:</label>
				<div class="controls">
					<input type="text" id="ssnumber" name="ssnumber" class="input-large required"/>
				</div>
			</div>
	 	<div class="control-group">
				<label for="name" class="control-label">地址:</label>
				<div class="controls"> 
					<input type="text" id="address" name="address" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">上级分销商:</label>
				<div class="controls">
				    <select name="upuserId">
						<option value="">请选择</option>
						 <c:forEach var="list" items="${userdto}" varStatus="name">
							<option value="${list.id}" >${list.manageAddress}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			 <div class="control-group">
				<label for="plainPassword" class="control-label">申请理由:</label>
				<div class="controls"> 
					<textarea rows="5" cols="5" style="  width: 500px;" name="description" class="required"></textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
