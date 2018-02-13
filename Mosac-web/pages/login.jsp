<%@ page contentType="text/html;charset=utf8"%>
<html>
    <head>
		<title>Mosac管理平台</title>
	</head>
    <link rel="stylesheet" type="text/css" href="../style/loginStyle.css"/>
    <body>
    <!-- header end -->
	<div id="main">
		<div id="left">
			<img id="logo" alt="140x140" src="../src/logo.png" />
		</div>
		<div id="right">
			<div class="box">
				<h1>欢迎登录Mosac后台管理平台</h1>
				<table>
					<form method="post" action="verifyUser.jsp" autocomplete="off">
						<tr>
							<td>用户名：</td>
							<td><input type="text" name="user" placeholder="Username"></td>
						</tr>
						<tr>
							<td>密码：</td>
							<td><input type="password" name="passwd" placeholder="Password"></td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="submit" value="登录">
								<input type="reset" value="清空">
							</td>
						</tr>
					</form>
				</table>
				<div class="warning">
					<%
						String warning = request.getParameter("warning");
						if(warning != null) {
							if(warning.equals("wrong_msg"))
								out.println("您输入的用户名不存在\\密码有误，请重新登录!");
							else if(warning.equals("empty_msg"))
								out.println("请完善登录信息！");
						}
					%>
				</div>
			</div>
		</div>
		
	</div>
    <!-- main end -->

    <!-- footer end -->
    </body>
</html>