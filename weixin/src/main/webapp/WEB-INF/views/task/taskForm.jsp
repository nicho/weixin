<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>我的二维码</title>
</head>

<body>
	 
		<input type="hidden" name="id" value="${task.id}"/>
		<fieldset>
			<legend><small>我的二维码</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">二维码名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"  value="${task.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	 
		 <img src="${ctx}/image/${task.imageUrl}" />
		</fieldset>
 
</body>
</html>
