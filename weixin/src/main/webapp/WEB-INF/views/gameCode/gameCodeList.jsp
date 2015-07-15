<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>游戏码管理</title>
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
	<div><a class="btn" href="${ctx}/gameCode/create/${gameId}">创建游戏码</a></div> <br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>游戏名</th><th>游戏码</th><th>创建时间</th><th>状态</th><th>领取用户</th> <th>领取时间</th>  <th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${gameCodes}" var="task">
			<tr> 
				<td>${task.game.gameName}</td>
				<td>${task.code}</td>
				<td><fmt:formatDate value="${task.createDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>
				<c:if test="${task.status eq 'Y'}">有效</c:if>
				<c:if test="${task.status eq 'N'}">失效</c:if>
				</td>
				<td>${task.wxuserId}</td> 
				<td><fmt:formatDate value="${task.updateDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>
				 
				 <c:if test="${task.status eq 'Y'}"><a href="#" onclick ="confirmDisabled('${ctx}/gameCode/disabled/${task.id}')">失效</a> 
				 </c:if> 
				

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:paginationMybatis/>

	
</body>
</html>
