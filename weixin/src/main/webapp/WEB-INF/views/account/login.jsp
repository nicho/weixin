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

 
</head>

<body>
	<div class="container">
	<div id="header">
	<div id="title">
	    <h1><a href="${ctx}">《离恨天》微信公众号推广系统</a><small>--二维码</small>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">管理会员</a></li>
						<li><a href="${ctx}/admin/task">管理会员二维码</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/index">我的二维码</a></li> 
					<li><a href="${ctx}/logout">退出</a></li>
				</ul>
			</div>
		</shiro:user>
		</h1>
	</div>
</div>
		<div id="content">
			<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
		<%
			String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if(error != null){
		%>
				<div class="alert alert-error controls input-large">
					<button class="close" data-dismiss="alert">×</button>
		<%
				if(error.contains("DisabledAccountException")){
					out.print("用户已被屏蔽,请登录其他用户.");
				}
				else{
					out.print("登录失败，请重试.");
				}
		%>		
				</div>
		<%
			}
		%>
		<br>
		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<input type="password" id="password" name="password" class="input-medium required"/>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
				<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/> <a class="btn" href="${ctx}/register">注册</a>
			 	 
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
		</div>
		<div id="footer">
	Copyright &copy; 2005-2020 <a href="http://gamewin.taobao.com">gamewin.taobao.com</a>
</div>
	</div>
	<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>