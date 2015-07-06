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
				<label>姓名</label> <input type="text" name="search_LIKE_userName"
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
				<th>姓名</th>
				<th>申请时间</th>
				<th>状态</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${applyThreeAdmins.content}" var="user">
				<tr>
					<td> ${user.user.loginName} </td>
					<td>${user.user.name}</td>
					<td>${user.userName}</td>
					<td><fmt:formatDate value="${user.createDate}"	pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<td>
					 <c:if test="${user.status eq 'submit'}">审批中</c:if>
					 <c:if test="${user.status eq 'PASS'}">审批通过</c:if>
					&nbsp;</td>
					<td> 
					<a href="${ctx}/ApplyThreeAdmin/auditView/${user.id}" >审批</a>&nbsp; 
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${applyThreeAdmins}" paginationSize="5" />
 
</body>
</html>
