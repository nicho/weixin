<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar" class="span2">
	<h1>系统管理</h1>
	<div class="submenu">
		<shiro:hasAnyRoles name="admin,TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/admin/user">帐号管理</a>
		</shiro:hasAnyRoles>
		<shiro:hasRole name="user">
			<a id="account-tab"href="${ctx}/ApplyThreeAdmin/create">申请分销商</a>
		</shiro:hasRole>
		<shiro:hasAnyRoles name="TwoAdmin">
			<a id="account-tab"href="${ctx}/ApplyThreeAdmin">审核-分销商申请</a> 
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/admin/user/auditUserlist">审核-用户注册申请</a> 
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="TwoAdmin,ThreeAdmin">
			<a id="account-tab"href="${ctx}/activationCode">激活码管理</a> 
		</shiro:hasAnyRoles>
	</div>
	<h1>任务管理</h1>
	<div class="submenu">
		<a id="ManageTaskController" href="${ctx}/manageTask">任务列表</a> 
		<a id="manageQRcode" href="${ctx}/manageQRcode">任务二维码列表</a> 
		<a id="myTask" href="${ctx}/myTask">我的任务</a> 
	</div>
	<h1>分销商任务管理</h1>
	<div class="submenu">
		<a id="ManageTaskController" href="${ctx}/manageTask">任务列表</a> 
		<a id="ManageTaskController" href="${ctx}/manageTask">任务二维码列表</a> 
	</div>
</div>