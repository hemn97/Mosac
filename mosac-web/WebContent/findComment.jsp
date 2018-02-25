<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FindAllPosts</title>
</head>
<link rel="stylesheet" type="text/css" href="styles/findComment.css"/>

<script type="text/javascript">
	function del_confirm()
	{
		var r=confirm("你确定要删除该评论吗？");
		if (r==true)
	  	{
	  		return true;
	  	}
		else
	  	{
	  		return false;
	  	}
	}
</script>

<body>
<!-- 头部 -->
<div id="header">
	<div class="container">
		<div class="top">
			<div class="left">
				<img src="images/comment.png" />
				<h3>所有评论</h3>
			</div>
			<div class="right">
				<a href="queryComment.jsp">返回</a>
				<a href="queryComment.jsp">
					<img src="images/back.png"/>
				</a>
			</div>
	</div>
		<div class="clear">
			<hr />
		</div>
		<div class="data">
			<table>
        		<tr>
		            <th>id</th>
		            <th>评论人</th>
		            <th>评论内容</th>
		            <th>操作</th>
        		</tr>
        		<c:forEach items="${pb.beanList}" var="comment">
	        		<tr>
				    	<td>${comment.comment_id}</td>
				        <td>${comment.name}</td>
				        <td>${comment.comment}</td>
				        <td>
			            <a href="<c:url value='/CommentServlet?method=delete&comment_id=${comment.comment_id}'/> " onclick="return del_confirm()">删除</a>
		           		</td>
		        	</tr>
        		</c:forEach>
    		</table>
		</div>
		<div class="pagecontrol">
			<center>
				第${pb.pc}页/共${pb.tp}页
    			<a href="${pb.url}&pc=1">首页</a>
    			<c:if test="${pb.pc>1}">
        			<a href="${pb.url}&pc=${pb.pc-1}">上一页</a>
    			</c:if>

    			<c:choose>
        			<c:when test="${pb.tp<=10}">
            			<c:set var="begin" value="1"/>
            			<c:set var="end" value="${pb.tp}"/>
        			</c:when>
        			<c:otherwise>
            			<c:set var="begin" value="${pb.pc-5}"/>
            			<c:set var="end" value="${pb.pc+4}"/>
            			<%--头溢出--%>
            			<c:if test="${begin<1}">
                			<c:set var="begin" value="1"/>
                			<c:set var="end" value="10"/>
            				</c:if>
            			<%--尾溢出--%>
            			<c:if test="${end>pb.tp}">
                			<c:set var="end" value="${pb.tp}"/>
                			<c:set var="begin" value="${pb.tp-9}"/>
            			</c:if>
        			</c:otherwise>
    			</c:choose>

    			<%--循环遍历页码列表--%>
    			<c:forEach var="i" begin="${begin}" end="${end}">
        			<c:choose>
            			<c:when test="${i eq pb.pc}">
                			<b id="cur">[${i}]</b>
            			</c:when>
            			<c:otherwise>
                			<a href="${pb.url}&pc=${i}">[${i}]</a>
            			</c:otherwise>
        			</c:choose>
				</c:forEach>
				<c:if test="${pb.pc<pb.tp}">
    				<a href="${pb.url}&pc=${pb.pc+1}">下一页</a>
    			</c:if>
    			<a href="${pb.url}&pc=${pb.tp}">尾页</a>
			</center>
		</div>
	</div>
</div>
</body>
</html>