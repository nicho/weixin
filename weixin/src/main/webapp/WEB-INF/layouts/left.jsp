<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar" style="float:left;margin-left: 20px;width: 140px;">
	<h1>任务管理</h1>
	<div class="submenu"> 
		<a id="myTask" href="${ctx}/manageTask/myTask">我的任务</a> 
		<shiro:hasRole name="admin">
		<a id="ManageTaskController" href="${ctx}/manageTask">管理任务</a>  
		</shiro:hasRole>
	</div>
	<h1>系统管理</h1>
	<div class="submenu">
	
		<shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/admin/user">帐号管理</a>
		</shiro:hasAnyRoles>
		<shiro:hasRole name="user">
			<a id="account-tab"href="${ctx}/ApplyThreeAdmin/create">申请分销商</a>
		</shiro:hasRole>
		<shiro:hasAnyRoles name="admin,TwoAdmin">
			<a id="account-tab"href="${ctx}/ApplyThreeAdmin">审核-分销商申请</a> 
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/admin/user/auditUserlist">审核-用户注册申请</a> 
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/activationCode">激活码管理</a> 
		</shiro:hasAnyRoles>
			<a id="account-tab"href="${ctx}/profile">资料修改</a>
	</div>
	

 
</div>