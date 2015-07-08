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
 		<h2>Multiple ComboTree</h2>
	<p>Click the right arrow button to show the tree panel and select multiple nodes.</p>
	<div style="margin:20px 0">
		<span>Cascade Check: </span>
		<input type="checkbox" checked onclick="$('#cc').combotree({cascadeCheck:$(this).is(':checked')})">
	</div>
	<select id="cc" class="easyui-combotree" data-options="url:'${ctx}/static/easyui/demo/combotree/tree_data1.json',method:'get'" multiple style="width:200px;"></select>

</body>
</html>
