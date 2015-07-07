<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>用户管理</title>
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
				<button type="submit" class="btn" id="search_btn">查询</button>
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
				<th>注册时间</th>
				<th>状态</th>
				<th>上级分销商</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${usersx}" var="user">
				<tr>
					<td><a href="${ctx}/admin/user/update/${user.id}">${user.loginName}</a></td>
					<td>${user.name}</td>
					<td><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<td>${allStatus[user.status]}&nbsp;</td>
					<td>${user.upuser.loginName}</td>
					<td><a href="${ctx}/admin/user/update/${user.id}">修改</a>&nbsp;
					<a href="#" onclick="confirmDisabled('${ctx}/admin/user/disabled/${user.id}')">失效</a>&nbsp;
						&nbsp;&nbsp;&nbsp;<a href="#" onclick="confirmDelete('${ctx}/admin/user/delete/${user.id}')">删除</a>
						&nbsp;</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:paginationMybatis  />
	<div>
		<a class="btn" href="${ctx}/admin/cteateUser">创建用户</a>
	</div>
</body>
</html>
