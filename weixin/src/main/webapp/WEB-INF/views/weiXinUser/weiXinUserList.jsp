<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称</label> <input type="text" name="search_LIKE_title" class="input-medium" value="${param.search_LIKE_title}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>序号</th><th>用户OPENID</th><th >事件</th><th >时间</th></tr></thead>
		<tbody>
		<c:forEach items="${weiXinUsers.content}" var="task"  varStatus="vstatus">
			<tr>
				<td align="center">${vstatus.index+1}</td>
				<td> ${task.fromUserName} </td>
				<td>${task.eventKey}</td>
				<td>${task.createDate}</td>
				  
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${weiXinUsers}" paginationSize="5"/>

	<div> </div>
</body>
</html>
