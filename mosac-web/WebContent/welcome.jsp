<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Mosac后台管理系统</title>
</head>

<link rel="stylesheet" type="text/css" href="styles/welcome.css"/>
<body>
<!-- 头部 -->
<div id="header">
	<div class="header container">
		<div class="nav left">
		</div>
		<div class="nav logo">
			<img id="logo" src="images/mosac2.png" alt="logo" />
		</div>
		<div class="nav right">
		</div>
	</div>
</div>
<!-- Main -->
<div id="main">
	<div class="main container">
		<div class="content_container">
			<div class="content_empty"></div>
			<div class="login_container">
				<form action="<c:url value='/LoginServlet'/>" method="post">
					<input type="hidden" name="method" value="validate"/>
					<div class="title">登录</div>
					<ul>
						<li>
							<div class="text">
								<span class="username"></span>
								<input name="username" type="text" placeholder="用户名">
							</div>
						</li>
						<li>
							<div class="password">
								<span class="password"></span>
								<input name="password" type="password" placeholder="密码">
							</div>
						</li>
						<li>
							<input type="submit" value="Login">
						</li>
					</ul>
					<h5 style="color:red;">${alert}</h5>
				</form>
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