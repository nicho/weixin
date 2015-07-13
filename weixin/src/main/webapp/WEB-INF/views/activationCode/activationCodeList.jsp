<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>激活码管理</title>
	<script type="text/javascript">
		function copyUrl2(clipBoardContent)
		{ 
			copyToClipboard("http://www.gamewin.xyz"+clipBoardContent);  
		}
		
		function copyToClipboard(txt) {
            if (window.clipboardData) {
                window.clipboardData.clearData();
                clipboardData.setData("Text", txt);
                alert("复制成功！");

            } else if (navigator.userAgent.indexOf("Opera") != -1) {
                window.location = txt;
            } else if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (e) {
                    alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");
                }
                var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
                if (!clip)
                    return;
                var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
                if (!trans)
                    return;
                trans.addDataFlavor("text/unicode");
                var str = new Object();
                var len = new Object();
                var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                var copytext = txt;
                str.data = copytext;
                trans.setTransferData("text/unicode", str, copytext.length * 2);
                var clipid = Components.interfaces.nsIClipboard;
                if (!clip)
                    return false;
                clip.setData(trans, null, clipid.kGlobalClipboard);
                alert("复制成功！");
            }
        }
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>激活码</label> <input type="text" name="search_LIKE_activationCode" class="input-medium" value="${param.search_LIKE_activationCode}"> 
				<button type="submit" class="btn" id="search_btn">查询</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	<div><a class="btn" href="${ctx}/activationCode/create">创建激活码</a></div><br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>激活码名称</th><th>激活码类型</th><th>激活码</th><th>创建时间</th><th>注册链接</th> <th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${activationCodes.content}" var="task">
			<tr>
				<td>${task.title}</td>
				<td>
						<c:if test="${task.activationCodeType eq 'CREATEUSER'}">用户注册</c:if>
						<c:if test="${task.activationCodeType eq 'CREATETHREEADMIN'}">分销商注册</c:if>
				
				</td>
				<td>${task.activationCode}</td> 
				<td><fmt:formatDate  value="${task.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				 <td>http://www.gamewin.xyz${ctx}/register/code/${task.id}</td> 
				<td>
				 <c:if test="${task.status != 'disabled'}">
					<a href="#"  onClick="copyUrl2('${ctx}/register/code/${task.id}')" >复制注册链接(IE)</a>
				
					<a href="#" onclick="confirmDisabled('${ctx}/activationCode/disabled/${task.id}')">失效</a>
					</c:if>
				<a href="#" onclick="confirmDelete('${ctx}/activationCode/delete/${task.id}')">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${activationCodes}" paginationSize="5"/>

	
</body>
</html>
