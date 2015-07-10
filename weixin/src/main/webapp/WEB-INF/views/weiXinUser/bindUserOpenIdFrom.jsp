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
 
   <form id="loginForm" action="${ctx}/weixinUser/updateBindUserOpenId" method="post" class="form-horizontal">
   <input type="hidden" name="code" value="${code}">
   <input type="hidden" name="grant_type" value="${grant_type}">
    <div class="container"> 
    	 
			<section class="loginBox row-fluid">
					<div class="tabbable" id="tabs-634549">
						<ul class="nav nav-tabs"> 
							<li class="active">
								<a href="#panel-549981" data-toggle="tab">绑定微信用户</a>
							</li> 
						</ul>
						<div class="tab-content">
							<div class="tab-pane" id="panel-60560">
							
							</div>
							<div class="tab-pane active" id="panel-549981">
								<DIV><input type="text" name="username" placeholder="用户名" value="${username}" class="input-medium required"/></DIV><br>
								<DIV>
									<input type="password" name="password" placeholder="密码"  class="input-medium required"/><br>
								</DIV>
								 <DIV class="span6"><br><label> </label></DIV><br>
									<DIV class="span1"><input type="submit" value=" 绑定 " class="btn btn-primary"></DIV>  
									
							</div>
						</div>
					</div>
        </section><!-- /loginBox -->
    </div> <!-- /container -->
    	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
    </form>
    
  </body>
</html>