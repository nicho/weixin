<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>二维码管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				 
		    </form>
	    </div>
	   
	</div>
 	<br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>二维码名称</th> <th>创建日期</th><th>主任务</th><th>二维码类型</th><th>二维码状态</th><th>已完成关注数</th> <th>已完成关注数(管理)</th> <th>分配用户</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${manageQRcodes}" var="task">
			<tr>
				<td><a href="${ctx}/manageQRcode/update/${task.id}">${task.title}</a></td> 
				<td><fmt:formatDate  value="${task.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.task.title}</td>
				<td>
				<c:choose>
					<c:when test="${task.qrcodeType eq 'WeixinGd'}">微信固定</c:when>
					<c:when test="${task.qrcodeType eq 'WeixinLs'}">微信临时</c:when>
					<c:when test="${task.qrcodeType eq 'WeixinApk'}">应用APK</c:when> 
					<c:when test="${task.qrcodeType eq 'WeixinOther'}">外部跳转</c:when> 
				</c:choose>
				</td>
				<td>
				 <c:choose>
					<c:when test="${task.qrState eq 'Y'}">有效</c:when>
					<c:when test="${task.qrState eq 'N'}">失效</c:when> 
				</c:choose>
				</td>
				<td><a href="${ctx}/manageQRcode/showMyTaskQRcodeHistroy/${task.id}">${task.qrSubscribeCount}</a></td> 
				<td><a href="${ctx}/manageQRcode/showMyTaskQRcodeHistroyUp/${task.id}">${task.qrSubscribeAdminCount}</a></td> 
				<td>${task.user.name}</td>
				<td>
				<shiro:hasRole name="admin">
				<c:if test="${task.qrState eq 'Y'}"> <a href="#" onclick ="confirmDisabled('${ctx}/manageQRcode/disabled/${task.id}')">失效</a> </c:if> 
				 <a href="#" onclick="confirmDelete('${ctx}/manageQRcode/delete/${task.id}')">删除</a>
				 </shiro:hasRole>
				 <a href="${ctx}/manageQRcode/viewManageQRcode/${task.id}">查看</a>
				 </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:paginationMybatis  />


</body>
</html>
