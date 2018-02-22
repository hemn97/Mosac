<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mosac后台管理系统</title>
</head>
<link rel="stylesheet" type="text/css" href="styles/editPost.css"/>

<script type="text/javascript">
	function edit_confirm()
	{
		if(document.getElementById('title').value.length < 5){
			document.getElementById('titleError').innerHTML = "标题字数不能小于5";
			return false;
		} else if(document.getElementById('title').value.length > 20){
			document.getElementById('titleError').innerHTML = "标题字数不能大于20";
			return false;
		}
		if(document.getElementById('content').value.length < 20){
			document.getElementById('contentError').innerHTML = "内容字数不能小于20";
			return false;
		} else if(document.getElementById('content').value.length > 120){
			document.getElementById('contentError').innerHTML = "内容字数不能大于120";
			return false;
		}
		var r=confirm("你确定要修改该帖子吗？");
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
<div id="container">
	<div class="top">
		<div class="left">
			<img src="images/edit.png" />
			<h3>编辑帖子</h3>
		</div>
		<div class="right">
			<a class="back" href="#" onclick="javascript:history.back(-1);">返回</a>
			<a class="back" href="#" onclick="javascript:history.back(-1);">
				<img src="images/back.png"/>
			</a>
		</div>
	</div>
	<div class="clear">
		<hr />
	</div>
	<div class="data">
		<form action="<c:url value='/PostServlet'/>" method="post">
	    	<input type="hidden" name="method" value="edit"/>
	    	<input type="hidden" name="post_id" value="${post.post_id}"/>
	    	<label>
				<span>标题：</span>
				<input id="title" type="text" name="title" value="${post.title}" />
				<span id="titleError" class="error">&nbsp;</span>
			</label>
			<label>
				<span>作者：</span>
				<span>${post.name}</span>
			</label>
			<label>
				<span>发帖时间：</span>
				<span>${post.time}</span>
			</label>
			<label>
				<span>内容：</span>
				<textarea id="content" name="content">${post.content}</textarea>
				<span id="contentError" class="error">&nbsp;</span>
			</label>
			<div class="buttongroup">
				<label>
					<span>&nbsp;</span>
					<input type="submit" class="button" value="确认修改" onclick="return edit_confirm()"/>
					<input type="reset" name="reset" value="重置"/>
				</label>
			</div>
		</form>
	</div>
</div>
</body>
</html>