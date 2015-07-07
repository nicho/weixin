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
<title>《宝箱》推广系统:</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script> 
 <link rel="stylesheet" type="text/css" href="${ctx}/static/styles/loginstyle.css" />
</head>



<body>
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
				else if(error.contains("ConcurrentAccessException")){
					out.print("用户正在审批,请等待.");
				} 
				else{
					out.print("登录失败，请重试.");
				}
		%>		
				</div>
		<%
			}
		%>
  <fieldset>
  <legend>登&nbsp;录</legend>
  <label for="login">登录名</label>
<input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
  <div class="clear"></div>
  <label for="password">密码</label>
  <input type="password" id="password" name="password" class="input-medium required"/>
  <div class="clear"></div>
  <label for="remember_me" style="padding: 0;"> 记住我</label> 
  <input type="checkbox"  id="rememberMe" name="rememberMe" style="position: relative; top: 3px; margin: 0; " name="remember_me"/>
  <div class="clear"></div>
  <br />
  <input type="submit" style="margin: -20px 0 0 287px;" class="button" name="commit" value="登录"/>
  </fieldset>
</form>
<p align="center"><strong>&copy; http://bbs.lihentian.com</strong></p>
</body>
</html>