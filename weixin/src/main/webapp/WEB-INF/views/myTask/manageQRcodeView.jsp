<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>我的二维码</title>
</head>

<body> 
	<form  class="form-horizontal">
		<fieldset>
			<legend><small>我的二维码</small><a href="javascript:history.go(-1);" style="  float: right;  font-size: 14px;">返回</a></legend> 
			 <div class="control-group">
				<label for="task_title" class="control-label">二维码名称:</label>
				<div class="controls">
					<label style="  font-size: 14px;  margin-top: 5px;">${entity.title}</label>
				</div>
			</div>	 
			 <div class="control-group">
				<label for="task_title" class="control-label">二维码类型:</label>
				<div class="controls">
				<label style="  font-size: 14px;  margin-top: 5px;">
				 <c:choose>
					<c:when test="${entity.qrcodeType eq 'WeixinGd'}">微信固定</c:when>
					<c:when test="${entity.qrcodeType eq 'WeixinLs'}">微信临时</c:when>
					<c:when test="${entity.qrcodeType eq 'WeixinApk'}">应用APK</c:when> 
					<c:when test="${entity.qrcodeType eq 'WeixinOther'}">外部跳转</c:when> 
				</c:choose> 
				</label>
				</div>
			</div>	 
		 <img src="${HttpImageUrl}/image/${entity.imageUrl}" />
		</fieldset>
 </form>
</body>
</html>
