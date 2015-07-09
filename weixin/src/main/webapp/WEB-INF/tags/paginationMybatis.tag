<%@tag pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<style>


/***************分页样式******************/
.fenye{ float:right; margin-top:10px;}
.fenye ul{ float:left; margin-left:32px; }
.fenye ul li{ float:left; margin-left:5px;padding: 4px 6px; border:1px solid #ccc;font-weight:bold; cursor:pointer; color:#999;}
.fenye ul li a{ color:#999;}
.fenye ul li.xifenye{ width:38px; text-align:center; float:left; position:relative;cursor: pointer;}
.fenye ul li .xab{ float:left; position:absolute; width:39px; border:1px solid #ccc;top:-125px; background-color: #fff; display:inline;left:-1px; width:50px; height:123px;}
.fenye ul li .xab ul{ margin-left:0; padding-bottom:0;overflow: auto;overflow-x: hidden; height:123px;}
.fenye ul li .xab ul li{ border:0; padding:4px 0px; color:#999; width:34px; margin-left:0px; text-align:center;}
</style>



 <div  >
 <div  >
<!--分页开始--->
    <div class="fenye">
    	<ul>
        	<li id="first" ><a href="?page=1&sortType=${sortType}&${searchParams}">首页</a></li>
            <li id="top" onClick="topclick()">
            <c:choose>
            	<c:when test="${page.prePage>0}"><a href="?page=${page.prePage}&sortType=${sortType}&${searchParams}">上一页</a></c:when>
            	<c:otherwise><a href="#">上一页</a></c:otherwise>
            </c:choose>
            
            </li>
            <li class="xifenye" id="xifenye">
            	<a id="xiye">${page.pageNum}</a>/<a id="mo">${page.pages}</a>
                <div class="xab" id="xab" style="display:none">
                	<ul id="uljia">	
                    </ul>
                </div>
            </li>
            <li id="down"  > 
             <c:choose>
            	<c:when test="${page.nextPage>0}"><a href="?page=${page.nextPage}&sortType=${sortType}&${searchParams}">下一页</a></c:when>
            	<c:otherwise><a href="#">下一页</a></c:otherwise>
            </c:choose>
            </li>
            <li id="last"><a href="?page=${page.pages}&sortType=${sortType}&${searchParams}">末页</a></li>
        </ul>    
    </div>
<!--分页结束--->	
</div>
 </div>
 
