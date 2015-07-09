<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>用户管理</title>
<script type="text/javascript">
function confirmtisheng(url)
{
	if(confirm("确认提升为二级经销商？"))
	{
		window.location.href=url;
	}
}
</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>

	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
			</form>
		</div>
		
	</div>
		<div>
		<shiro:hasAnyRoles name="admin">
			<a class="btn" href="${ctx}/admin/cteateUser">创建用户</a>
		</shiro:hasAnyRoles>
	</div><br>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户名</th>
				<th>注册时间</th>
				<th>角色</th>
				<th>地区</th>
				<th>状态</th>
				<th>上级分销商</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${usersx}" var="user">
				<tr>
					<td> ${user.loginName} </td>
					<td>${user.name}</td>
					<td><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<td>
					<c:choose>
						<c:when test="${user.roles eq 'admin'}">管理员</c:when>
						<c:when test="${user.roles eq 'TwoAdmin'}">二级分销商</c:when>
						<c:when test="${user.roles eq 'ThreeAdmin'}">三级分销商</c:when>
						<c:otherwise>用户</c:otherwise>
					</c:choose> 
					</td>
					<td>${user.manageAddress}</td>
					<td>${allStatus[user.status]}&nbsp;</td>
					<td>${user.upuser.loginName}</td>
					<td>
					<shiro:hasAnyRoles name="admin"> 
							<a href="${ctx}/admin/user/update/${user.id}">修改</a>&nbsp; 
							<a href="#" onclick="confirmDelete('${ctx}/admin/user/delete/${user.id}')">删除</a>&nbsp; 
					</shiro:hasAnyRoles>
					<a href="#" onclick="confirmDisabled('${ctx}/admin/user/disabled/${user.id}')">失效</a>&nbsp;
						<shiro:hasAnyRoles name="admin"> 
							<c:if test="${user.roles eq 'ThreeAdmin'}"> 
								<a href="#" onclick="confirmtisheng('${ctx}/admin/user/upTwoAdmin/${user.id}')">提升为二级分销商</a>&nbsp;
							</c:if> 
						</shiro:hasAnyRoles>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:paginationMybatis  />

</body>
</html>
