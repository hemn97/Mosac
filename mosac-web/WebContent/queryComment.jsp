<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QueryPost</title>
</head>
<link rel="stylesheet" type="text/css" href="styles/queryComment.css"/>

<script type="text/javascript">
	function query_check() {
		var i, type;
		if(document.getElementById("keyword").value.length == 0) {
			document.getElementById("error").innerHTML = "查询帖子id不能为空！";
			return false;
		}
		return true;
	}
</script>

<body>
<div class="container">
	<div class="top">
		<img src="images/comment.png" />
		<h3>查询评论</h3>
	</div>
	<div class="clear">
		<hr />
	</div>
	<div class="data">
		<form action="<c:url value='/CommentServlet'/>" method="get">
			<input type="hidden" name="method" value="findComment"/>
			<input id="keyword" type="text" name="post_id" placeholder="根据帖子id进行查询"/>
			<input type="submit" class="button" value="查询" onclick="return query_check()"/>
			<span id="error">&nbsp;</span>
		</form>
	</div>
</div>
</body>
</html>