<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>我的任务</title>
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
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>任务名称</th><th>任务描述</th><th>开始时间</th><th>结束时间</th><th>任务目标数</th><th>任务状态</th><th>二维码类型</th><th>任务范围</th><th>总完成任务数</th><th>本人完成任务数</th>
		<shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
		<th>下级完成任务数</th>
		</shiro:hasAnyRoles>
		<th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${manageTasks}" var="task">
			<tr>
				<td> ${task.title} </td>
				<td>${task.description}</td>
				<td><fmt:formatDate  value="${task.startDate}" type="both" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate  value="${task.endDate}" type="both" pattern="yyyy-MM-dd" /></td>
				<td>${task.taskCount}</td>
				<td>
				<c:if test="${task.state eq 'Y'}">执行中</c:if>
				<c:if test="${task.state eq 'N'}">失效</c:if>
				</td>
				<td>
				<c:if test="${task.weixinGd eq 'Y'}">微信固定,</c:if>
				<c:if test="${task.weixinLs eq 'Y'}">微信临时,</c:if>
				<c:if test="${task.weixinApk eq 'Y'}">应用APK,</c:if>
				<c:if test="${task.weixinOther eq 'Y'}">外部跳转,</c:if>
				</td>
				<td> 
				<c:choose>
					<c:when test="${task.viewrangeType eq 'ALL'}">全部</c:when> 
					<c:otherwise>指定</c:otherwise>
				</c:choose>
				</td>
				<td>${task.finishTaskCount}</td> 
			    <td>${task.finishTaskMyCount}</td> 
			    <shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
			    <td>${task.finishTaskMyChildrenCount}</td> 
			    </shiro:hasAnyRoles>
				<td><a href="${ctx}/manageQRcode/showMyTaskQRcode/${task.id}">查看二维码</a> </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:paginationMybatis  />

	
</body>
</html>
