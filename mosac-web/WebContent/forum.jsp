<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Mosac后台管理系统</title>
</head>

<link rel="stylesheet" type="text/css" href="styles/index.css"/>
<link rel="stylesheet" type="text/css" href="styles/forum.css"/>
<!-- jQuery 2.2.3 -->
<script src="../resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
	window.onload=function(){
		var oBar=document.getElementById('sidebar');
        var aLi=oBar.getElementsByTagName('li');
        var oWork=document.getElementById('workspace');
        var aDiv=oWork.getElementsByTagName('div');

        for(var i=0;i<aLi.length;i++){
            aLi[i].index=i;   //为每个li添加对应的索引index
            aLi[i].onclick=function(){   //循环为每个li添加onclick事件
            for(var i=0;i<aLi.length;i++){
                aLi[i].className='';  //循环清空li样式
                aDiv[i].style.display='none';  //循环隐藏所有div
            }
            this.className='active';  //当前点击的元素样式变成active
            aDiv[this.index].style.display='block';    //this.index 获取当前li对应的索引
            }
        }
	};
	$(".des" ).load( "demo.html" );
</script>

<body>
<!-- 头部 -->
<div id="header">
	<div class="header container">
		<div class="nav left">
			<ul class="navigation">
				<li><a id="home" href="index.jsp">主页</a></li>
				<li><a id="forum" href="forum.jsp">论坛</a></li>
			</ul>
		</div>
		<div class="nav logo">
			<img id="logo" src="images/mosac2.png" alt="logo" />
		</div>
		<div class="nav right">
			<ul class="navigation">
				<li><a id="group" href="group.jsp">组队</a></li>
				<li><a id="user" href="user.jsp">用户</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- Main -->
<div id="main">
	<div class="main container">
		<div class="content_container">
			<div class="content_empty"></div>
			<div class="admin_box">
				<div class="left">
					<ul id="sidebar">
						<li class="active">帖子管理</li>
						<li>查询帖子</li>
					</ul>
				</div>
				<div class="right">
					<div id="workspace">
		                <div style="display:block">
							<div class="des"></div>
						</div>
		                <div style="display:none">Query</div>
            		</div>
				</div>
			</div>
			<div class="content_empty"></div>
		</div>
	</div>
</div>
<!-- 尾部 -->
<div class="footer">
	<div class="footer container">
		<div>Copyright © Hemn, Xuedh, Lingl</div>
	</div>
</div>
</body>
</html>