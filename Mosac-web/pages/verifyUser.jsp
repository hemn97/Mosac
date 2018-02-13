<%@ page contentType="text/html;charset=utf8" %>
<%@ page import="java.util.*,java.sql.*"%>
<%
	String user = request.getParameter("user");
	String passwd = request.getParameter("passwd");
	if(user.isEmpty() || passwd.isEmpty()) {
%>
	<jsp:forward page="login.jsp">
		<jsp:param name="warning" value="empty_msg"/>
	</jsp:forward>
<%
	}
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost:3306/Mosac";
    String username = "root";
    String password = "";
    Connection conn = DriverManager.getConnection(url,username,password);
	String sql = "select * from users where username='"+user+"' and password='"+passwd+"'";
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	if(rs.next())
	{
		session.setAttribute("username",rs.getString("username"));
		rs.close();
		conn.close();
%>
	<jsp:forward page="index.jsp"/>
<%
	} else {
		rs.close();
		conn.close();
%>
	<jsp:forward page="login.jsp">
		<jsp:param name="warning" value="wrong_msg"/>
	</jsp:forward>
<%
	}
%>
