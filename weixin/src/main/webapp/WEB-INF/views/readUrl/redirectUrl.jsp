<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>页面跳转</title>
 <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
 
</head>

<body>   
	<script type="text/javascript"> 
		$(document).ready(function() {
			window.location.href='${redirectUrl}';
		});
 
	</script>
</body>
</html>
