<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>二维码管理</title>
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
		<thead><tr><th>二维码名称</th> <th>有效期</th><th>主任务</th><th>二维码类型</th><th>二维码状态</th><th>任务目标数</th><th>二维码关注数</th><th>分配用户</th><th>创建者</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${manageQRcodes.content}" var="task">
			<tr>
				<td><a href="${ctx}/manageQRcode/update/${task.id}">${task.title}</a></td> 
				<td><fmt:formatDate  value="${task.qrValidityDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.task.title}</td>
				<td>
				<c:choose>
					<c:when test="${task.qrcodeType eq 'WEIXIN_GD'}">微信固定</c:when>
					<c:when test="${task.qrcodeType eq 'WEIXIN_LS'}">微信临时</c:when>
					<c:when test="${task.qrcodeType eq 'WEIXIN_OTHER'}">外部跳转</c:when> 
				</c:choose>
				</td>
				<td>
				 <c:choose>
					<c:when test="${task.qrState eq 'Y'}">有效</c:when>
					<c:when test="${task.qrState eq 'N'}">无效</c:when> 
				</c:choose>
				</td>
				<td>${task.taskCount}</td>
				<td>${task.qrSubscribeCount}</td>
				<td>${task.distributionUser.name}</td>
				<td>${task.user.name}</td>
				<td> <a href="#" onclick="confirmDelete('${ctx}/manageQRcode/delete/${task.id}')">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${manageQRcodes}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/manageQRcode/create">创建二维码</a></div>
</body>
</html>
