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
		<thead><tr><th>序号</th><th>名称</th><th >关注次数</th><th >操作</th></tr></thead>
		<tbody>
		<c:forEach items="${tasks.content}" var="task"  varStatus="vstatus">
			<tr>
				<td align="center">${vstatus.index+1}</td>
				<td> ${task.title} </td>
				<td>1</td>
				<td><a href="${ctx}/task/update/${task.id}">查看</a></td>
				 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${tasks}" paginationSize="5"/>

	<div> </div>
</body>
</html>
