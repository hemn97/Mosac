<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QueryPost</title>
</head>
<link rel="stylesheet" type="text/css" href="styles/queryPost.css"/>

<script type="text/javascript">
	function query_check() {
		var i, type;
		var radio = document.getElementsByName("type");
		if(document.getElementById("keyword").value.length == 0) {
			document.getElementById("error").innerHTML = "查询关键字不能为空！";
			return false;
		}
		for (i = 0; i < radio.length; i++) {  
	        if (radio[i].checked) {  
	        	document.getElementById("selectType").value = radio[i].value;
	        	break;
	        }  
	    }
		return true;
	}
</script>

<body>
<div class="container">
	<div class="top">
		<img src="images/search.png" />
		<h3>查询帖子</h3>
	</div>
	<div class="clear">
		<hr />
	</div>
	<div class="data">
		<form action="<c:url value='/PostServlet'/>" method="get">
			<input type="hidden" name="method" value="FuzzyQuery"/>
	    	<input type="hidden" id="selectType" name="selectType" value="title"/>
			<div class="button-holder">
				<input id="title" value="title" name="type" class="regular-radio" type="radio" checked="checked"><label for="title">标题</label>
				<input id="content" value="content" name="type" class="regular-radio" type="radio"><label for="content">内容</label>
				<input id="author" value="author" name="type" class="regular-radio" type="radio"><label for="author">作者</label>
			</div>
			<input id="keyword" type="text" name="keyword" placeholder="查询关键字"/>
			<input type="submit" class="button" value="查询" onclick="return query_check()"/>
			<span id="error">&nbsp;</span>
		</form>
	</div>
</div>
</body>
</html>