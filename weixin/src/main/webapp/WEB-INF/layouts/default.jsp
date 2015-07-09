<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>《宝箱》微信公众号推广系统:<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
<sitemesh:head />
<script type="text/javascript">
function confirmDelete(url)
{
	if(confirm("确认删除？"))
	{
		window.location.href=url;
	}
}
function confirmDisabled(url)
{
	if(confirm("确认失效？"))
	{
		window.location.href=url;
	}
}
function confirmPass(url)
{
	if(confirm("确认通过？"))
	{
		window.location.href=url;
	}
}


</script>
</head>

<body>
	<div class="container" style="width:95%">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div class="row">
			<%@ include file="/WEB-INF/layouts/left.jsp"%>
			<div id="main" class="span10" style="width:auto">
			  		  <sitemesh:body />  
			</div>
		</div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>	
</body>
</html>