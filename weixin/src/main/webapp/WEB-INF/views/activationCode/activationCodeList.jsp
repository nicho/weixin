<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>激活码管理</title>
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
		<thead><tr><th>激活码名称</th><th>激活码类型</th><th>激活码</th><th>状态</th><th>创建时间</th><th>激活时间</th><th>激活用户</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${activationCodes.content}" var="task">
			<tr>
				<td>${task.title}</td>
				<td>${task.activationCodeType}</td>
				<td>${task.activationCode}</td>
				<td>${task.status}</td>
				<td><fmt:formatDate  value="${task.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate  value="${task.activationDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.activationUser.loginName}</td> 
 
				<td><a href="#" onclick="confirmDisabled('${ctx}/activationCode/disabled/${task.id}')">失效</a>&nbsp; <a href="#" onclick="confirmDelete('${ctx}/activationCode/delete/${task.id}')">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${activationCodes}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/activationCode/create">创建激活码</a></div>
</body>
</html>
