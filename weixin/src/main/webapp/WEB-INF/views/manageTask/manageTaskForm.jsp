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

	<title>任务管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/manageTask/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}"/>
		<input type="hidden" id="viewrangeUsers" name="viewrangeUsers" value=""/>
		<input type="hidden" id="userIdArray" name="userIdArray" value="${userIdArray}"/> 
		<fieldset>
			<legend><small>管理任务</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">任务名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"  value="${task.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			 <div class="control-group">
				<label for="task_title" class="control-label">开始时间:</label>
				<div class="controls">
					<input type="text" id="task_startDate" name="startDateStr"  value="<fmt:formatDate  value="${task.startDate}" type="both" pattern="yyyy-MM-dd" />" class="input-large required" onclick="WdatePicker()"  />
				</div>
			</div>	
			 <div class="control-group">
				<label for="task_title" class="control-label">结束时间:</label>
				<div class="controls">
					<input type="text" id="task_endDate" name="endDateStr"  value="<fmt:formatDate  value="${task.endDate}" type="both" pattern="yyyy-MM-dd" />" class="input-large required"  onclick="WdatePicker()" />
				</div>
			</div>	
			<div class="control-group">
				<label for="task_title" class="control-label">任务目标数:</label>
				<div class="controls">
					<input type="text" id="task_taskCount" name="taskCount"  value="${task.taskCount}" class="input-large required number"  />
				</div>
			</div>	
		    <div class="control-group">
				<label for="task_title" class="control-label">任务范围:</label>
				<div class="controls">
					<input type="radio" id="viewrangeType1" name="viewrangeType" value="ALL" <c:if test="${task.viewrangeType eq 'ALL'}">checked</c:if> onclick="changeselectUser(1)"> 全部 &nbsp;&nbsp;&nbsp; <input type="radio" id="viewrangeType2"  name="viewrangeType" value="SELECT" <c:if test="${task.viewrangeType eq 'SELECT'}">checked</c:if> onclick="changeselectUser(2)">指定 	 
				</div>
			</div>	
		    <div class="control-group" id="selectDiv" style="display: none;">
				<label for="task_title" class="control-label">选择人员:</label>
				<div class="controls">
						<select id="cc" class="easyui-combotree"   multiple  style="width:200px;"    ></select>
				</div>
			</div>	
			
		    <div class="control-group">
				<label for="task_title" class="control-label">任务二维码类型:</label>
				<div class="controls">
					<input type="checkbox" name="weixinGd" value="Y" <c:if test="${task.weixinGd eq 'Y'}">checked</c:if>> 微信固定 &nbsp;&nbsp;
					<input type="checkbox" name="weixinLs" value="Y" <c:if test="${task.weixinLs eq 'Y'}">checked</c:if>> 微信临时 &nbsp;&nbsp;
					<input type="checkbox" id="weixinApk" name="weixinApk" value="Y" <c:if test="${task.weixinApk eq 'Y'}">checked</c:if> onclick="changeApkUrl(this)" > 应用APK &nbsp;&nbsp;
					<input type="checkbox" id="weixinOther" name="weixinOther" value="Y" <c:if test="${task.weixinOther eq 'Y'}">checked</c:if> onclick="changOtherUrl(this)"> 外部跳转 &nbsp;&nbsp;
				</div>
			</div>	
			<div class="control-group" id="apkUrlDiv" style="display: none;">
				<label for="task_title" class="control-label">应用APK连接:</label>
				<div class="controls">
					<input type="text" id="apkUrl" name="apkUrl"  value="${task.apkUrl}" class="input-large  required"  />
				</div>
			</div>
			<div class="control-group" id="otherUrlDiv" style="display: none;">
				<label for="task_title" class="control-label">外部跳转链接:</label>
				<div class="controls">
					<input type="text" id="otherUrl" name="otherUrl"  value="${task.otherUrl}" class="input-large required"  />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">任务描述:</label>
				<div class="controls">
					<textarea id="description" rows="5" name="description" style="  width: 500px;"  class="input-large">${task.description}</textarea>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
	function changeApkUrl(obj)
	{
		if(obj.checked==true)
		{
			$("#apkUrlDiv").attr("style","");
		}else
		{
			$("#apkUrlDiv").attr("style","display: none;");
		}
	}
	function changOtherUrl(obj)
	{
		if(obj.checked==true)
		{
			$("#otherUrlDiv").attr("style","");
		}else
		{
			$("#otherUrlDiv").attr("style","display: none;");
		}
	}
	function changeselectUser(fal)
	{
		if(fal==1)
		{
			$("#selectDiv").attr("style","display: none;");
		}
		else if(fal==2)
		{
			$("#selectDiv").attr("style","");
		}
	}
		$(document).ready(function() {
			
			if($("#weixinApk").attr("checked")=="checked")
			{
				$("#apkUrlDiv").attr("style","");
			}
			if($("#weixinOther").attr("checked")=="checked")
			{
				$("#otherUrlDiv").attr("style","");
			}
			if($("#viewrangeType2").attr("checked")=="checked")
			{
				$("#selectDiv").attr("style","");
			}
	
			
			$('#cc').combotree({  
				url: "${ctx}/admin/user/findUserTree?id=1",  
				onBeforeExpand: function(node, param) { 
					$('#cc').combotree("tree").tree("options").url = "${ctx}/admin/user/findUserTree?id="+node.id; 
				}
			});
			
			if($("#userIdArray").val()!=null && $("#userIdArray").val()!='')
			{ 
				$('#cc').combotree('setValues', eval($("#userIdArray").val()));
			}
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				  submitHandler: function(form) {  //通过之后回调 
					 $("input[type='submit']").attr("disabled","disabled");
					 var val = $('#cc').combotree('getValues'); 
					 $('#viewrangeUsers').val(val);
				  	form.submit();
				 } 
			});
		});
	</script>
</body>
</html>
