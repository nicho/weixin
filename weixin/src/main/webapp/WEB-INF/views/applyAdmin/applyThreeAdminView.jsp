<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>主页</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm"   class="form-horizontal">
 <fieldset>
			<legend><small>正在审批的资料</small><c:if test="${applyThreeAdmin.status eq 'reject'}"><a href="${ctx}/ApplyThreeAdmin/update/${applyThreeAdmin.id }" style="float: right;">重新申请</a></c:if></legend>
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
					<textarea rows="5" cols="5" style="  width: 500px;" name="description" class="required" disabled="disabled"> ${applyThreeAdmin.description } </textarea>
				</div>
			</div>
		    <div class="control-group">
				<label for="plainPassword" class="control-label">审批意见:</label>
				<div class="controls"> 
					<textarea rows="5" cols="5" name="approvalOpinion" class="required" style="  width: 500px;"  disabled="disabled"> ${applyThreeAdmin.approvalOpinion } </textarea>
				</div>
			</div>
		</fieldset>
		</form>
</body>
</html>
