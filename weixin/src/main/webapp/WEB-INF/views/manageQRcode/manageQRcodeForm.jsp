<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>任务二维码管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/manageQRcode/${action}"
		method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}" />
		<fieldset>
			<legend>
				<small>管理任务二维码</small>
			</legend>
			<div class="control-group">
				<label for="task_title" class="control-label">任务:</label>
				<div class="controls">
					<select name="taskId" class="required">
						<option value="">请选择</option>
						<c:forEach var="list" items="${ManageTaskList}" varStatus="name">
							<option value="${list.id}" <c:if test="${list.id eq task.task.id}">selected</c:if>>${list.title}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">二维码类型:</label>
				<div class="controls">
					<select name="qrcodeType">
						<option value="">请选择</option>
						<option value="WEIXIN_GD" <c:if test="${'WEIXIN_GD' eq task.qrcodeType}">selected</c:if>>微信固定</option>
						<option value="WEIXIN_LS" <c:if test="${'WEIXIN_LS' eq task.qrcodeType}">selected</c:if>>微信临时</option>
						<option value="WEIXIN_OTHER" <c:if test="${'WEIXIN_OTHER' eq task.qrcodeType}">selected</c:if>>外部跳转</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">二维码名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"
						value="${task.title}" class="input-large required" minlength="3" />
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">二维码URL:</label>
				<div class="controls">
					<input type="text" id="qrUrl" name="qrUrl"
						value="${task.qrUrl}" class="input-large " />
				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">二维码有效期至:</label>
				<div class="controls">
					<input type="text" id="qrValidityDateStr" name="qrValidityDateStr"
						value="<fmt:formatDate  value="${task.qrValidityDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />"
						class="input-large required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />

				</div>
			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">任务目标数:</label>
				<div class="controls">
					<input type="text" id="task_taskCount" name="taskCount"
						value="${task.taskCount}" class="input-large required number" />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">备注:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${task.description}</textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="提交" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="返回" onclick="history.back()" />
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
