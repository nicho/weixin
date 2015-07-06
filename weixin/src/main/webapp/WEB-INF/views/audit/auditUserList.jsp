<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>待审批用户</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>

	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>用户名</label> <input type="text" name="search_LIKE_name"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户名</th>
				<th>申请时间</th>
				<th>状态</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users.content}" var="user">
				<tr>
					<td> ${user.loginName} </td>
					<td>${user.name}</td>
					<td><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<td>${allStatus[user.status]}&nbsp;</td>
					<td> 
					<a href="#" onclick="confirmPass('${ctx}/admin/user/auditPass/${user.id}')">审批通过</a>&nbsp; 
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${users}" paginationSize="5" />
 
</body>
</html>
