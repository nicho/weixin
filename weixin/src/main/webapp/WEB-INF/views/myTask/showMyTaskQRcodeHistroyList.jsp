<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>二维码完成详细</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				 
		    </form>
	    </div>
	    
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>用户ID/用户IP</th><th>关注扫描时间</th> </tr></thead>
		<tbody>
		<c:forEach items="${qRcodeByHistory}" var="task">
			<tr>
				<td>${task.title} </td>
				<td>${task.userId}</td>
				<td><fmt:formatDate  value="${task.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:paginationMybatis  />

	
</body>
</html>
