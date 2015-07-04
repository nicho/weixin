<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称</label> <input type="text" name="search_LIKE_title" class="input-medium" value="${param.search_LIKE_title}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>任务名称</th><th>任务描述</th><th>开始时间</th><th>结束时间</th><th>任务目标数</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${manageTasks.content}" var="task">
			<tr>
				<td><a href="${ctx}/manageTask/update/${task.id}">${task.title}</a></td>
				<td>${task.description}</td>
				<td><fmt:formatDate  value="${task.startDate}" type="both" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate  value="${task.endDate}" type="both" pattern="yyyy-MM-dd" /></td>
				<td>${task.taskCount}</td>
				<td><a href="${ctx}/manageQRcode/${task.id}">二维码管理</a> <a href="#" onclick="confirmDelete('${ctx}/manageTask/delete/${task.id}')">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${manageTasks}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/manageTask/create">创建任务</a></div>
</body>
</html>
