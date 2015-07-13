<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>《宝箱》微信公众号推广系统:</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
 
	<title>用户注册</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/register/checkLoginName"
					} 
			
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
					}  
				
				}
			});
		});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
<div class="container">
	<div id="header">
	<div id="title">
	    <h1><a href="${ctx}">《宝箱》微信公众号推广系统</a><small>--二维码</small>
	    </h1>
	    	</div>
</div>
	<form id="inputForm" action="${ctx}/register/registerByAdminCode" method="post" class="form-horizontal">
	<input type="hidden" value="${activationCode}" name="activationCode">
		<fieldset>
			<legend><small>用户注册</small></legend>
			<div class="control-group">
				<label for="loginName" class="control-label">登录名:</label>
				<div class="controls">
					<input type="text" id="loginName" name="loginName" class="input-large required" minlength="3"/>
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">用户名:</label>
				<div class="controls">
					<input type="text" id="name" name="name" class="input-large required"/>
				</div>
			</div> 
			<div class="control-group">
				<label for="plainPassword" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">确认密码:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword" class="input-large required" equalTo="#plainPassword"/>
				</div>
			</div> 
						<div class="control-group">
				<label for="loginName" class="control-label">姓名:</label>
				<div class="controls">
					<input type="text" id="userName" name="userName" class="input-large required" value="${applyThreeAdmin.userName }" />
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">证件号:</label>
				<div class="controls">
					<input type="text" id="ssnumber" name="ssnumber" class="input-large required" value="${applyThreeAdmin.ssnumber }"/>
				</div>
			</div>
	 		<div class="control-group">
				<label for="name" class="control-label">地址:</label>
				<div class="controls"> 
					<input type="text" id="address" name="address" class="input-large required" value="${applyThreeAdmin.address }" />
				</div>
			</div>
						 <div class="control-group">
				<label for="plainPassword" class="control-label">申请理由:</label>
				<div class="controls"> 
					<textarea rows="5" cols="5" style="  width: 500px;" name="description" class="required" ${applyThreeAdmin.description }></textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
 
		<div id="footer">
	Copyright &copy; 2005-2020 <a href="http://gamewin.taobao.com">gamewin.taobao.com</a>
</div>
	 </div>
	<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>