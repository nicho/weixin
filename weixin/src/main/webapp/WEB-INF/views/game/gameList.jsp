<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>游戏管理</title>
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
	<div><a class="btn" href="${ctx}/game/create">创建游戏</a></div> <br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>游戏名称</th><th>游戏序号</th><th>游戏广告语</th><th>最大发码数</th> <th>总码数</th> <th>已发码数</th><th>剩余码数</th> <th>状态</th> <th>创建时间</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${games}" var="task">
			<tr> 
				<td>${task.gameName}</td>
				<td>${task.xuhao}</td>
				<td>${task.gameMessage}</td>
				<td>${task.maximum}</td> 
				<td>${task.totalCount}</td>
				<td>${task.postedCount}</td>
				<td>${task.surplusCount}</td>
				<td>
				<c:if test="${task.status eq 'Y'}">有效</c:if>
				<c:if test="${task.status eq 'N'}">失效</c:if>
				</td> 
				<td><fmt:formatDate value="${task.createDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>
				<a href="${ctx}/gameCode/showGame/${task.id}">游戏码管理</a>  &nbsp;
				 <c:if test="${task.status eq 'Y'}"><a href="#" onclick ="confirmDisabled('${ctx}/game/disabled/${task.id}')">失效</a>  &nbsp;
				 <a href="${ctx}/game/update/${task.id}">修改</a></c:if>  
				

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:paginationMybatis/>

	
</body>
</html>
