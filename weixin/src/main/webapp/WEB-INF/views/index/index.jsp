<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title> </title>
 

	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/demo.css">
	<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
	 
</head>

<body>
	<div style="margin:20px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getValue()">GetValue</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="setValue()">SetValue</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disable()">Disable</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="enable()">Enable</a>
	</div>
 		<h2>Multiple ComboTree</h2>
	<p>Click the right arrow button to show the tree panel and select multiple nodes.</p>
	<div style="margin:20px 0">
		<span>Cascade Check: </span>
		<input type="checkbox" checked onclick="$('#cc').combotree({cascadeCheck:$(this).is(':checked')})">
	</div>
	<select id="cc" class="easyui-combotree" data-options="url:'${ctx}/static/easyui/demo/combotree/tree_data1.json',method:'get'" multiple style="width:200px;"></select>
	<script type="text/javascript">
		function getValue(){
			var val = $('#cc').combotree('getValues');
			alert(val);
		}
		function setValue(){
			$('#cc').combotree('setValues', [122,123]);
		}
		function disable(){
			$('#cc').combotree('disable');
		}
		function enable(){
			$('#cc').combotree('enable');
		}
	</script>
</body>
</html>
