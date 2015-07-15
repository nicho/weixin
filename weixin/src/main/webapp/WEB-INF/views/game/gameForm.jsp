<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>

	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>

	<title>创建游戏</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/game/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}"/> 
		<fieldset>
			<legend><small>创建游戏</small></legend>
			 <div class="control-group">
				<label for="task_title" class="control-label">游戏序号:</label>
				<div class="controls">
					<input type="text" id="xuhao" name="xuhao"  value="${task.xuhao}" class="input-large required digits"  />
				</div>
			</div>	
			<div class="control-group">
				<label for="task_title" class="control-label">游戏名称:</label>
				<div class="controls">
					<input type="text" id="task_gameName" name="gameName"  value="${task.gameName}" class="input-large required" />
				</div>
			</div>	
			 <div class="control-group">
				<label for="task_title" class="control-label">最大发码数:</label>
				<div class="controls">
					<input type="text" id="task_gameMessage" name="maximum"  value="${task.maximum}" class="input-large required digits"  />
				</div>
			</div>	

			<div class="control-group">
				<label for="description" class="control-label">游戏广告语:</label>
				<div class="controls">
					<textarea id="gameMessage" rows="5" name="gameMessage" style="  width: 500px;"  class="input-large">${task.gameMessage}</textarea>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
	 
		$(document).ready(function() {  
			//聚焦第一个输入框
			$("#task_gameName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				  submitHandler: function(form) {  //通过之后回调 
					 $("input[type='submit']").attr("disabled","disabled"); 
				  	form.submit();
				 } 
			});
		});
	</script>
</body>
</html>
