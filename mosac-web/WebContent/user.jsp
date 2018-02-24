<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Mosac后台管理系统</title>
</head>
<%
    Cookie[] cookies=request.getCookies();
    boolean flag=false;
    if(cookies!=null){
	    for(int i=0;i<cookies.length;i++) {
	        if(cookies[i].getName().equals("admin_id")){
		        //如果cookie与保存的相等，即找到cookie
		        flag=true;
		        break;
	    	}
	    }
    }
    if(flag==false){
    	response.sendRedirect("welcome.jsp");
    }//如果没找到cookie，就返回登陆界面 
%>
<link rel="stylesheet" type="text/css" href="styles/index.css"/>
<link rel="stylesheet" type="text/css" href="styles/group.css"/>
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