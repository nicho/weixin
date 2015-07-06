<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>激活码管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/manageTask/create" method="post" class="form-horizontal">
	 
		<fieldset>
			<legend><small>新增激活码</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">激活码数量:</label>
				<div class="controls">
					<input type="text" id="activationCodeCount" name="activationCodeCount"   class="input-large required digits"  />
				</div>
			</div>	
	 		 <div class="control-group">
				<label for="task_title" class="control-label">激活码类型:</label>
				<div class="controls">
					 <select name="activationCodeType" class="required" >
						<option value="">请选择</option>
						<option value="CREATEUSER" >用户注册</option>
						<option value="CREATETHREEADMIN" >分销商注册</option>
					 </select>
				</div>
			</div>	
			<div class="control-group">
				<label for="task_title" class="control-label">激活码名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"  class="input-large required"  />
				</div>
			</div>	
		 <div class="control-group">
				<label for="task_title" class="control-label">激活码描述:</label>
				<div class="controls">
					<input type="text" id="task_title" name="description"  class="input-large required"  />
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
			$("#activationCodeCount").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
