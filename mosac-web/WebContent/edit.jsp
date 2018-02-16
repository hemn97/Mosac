<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 align="center">编辑帖子</h3>
<form action="<c:url value='/PostServlet'/>" method="post">
    <input type="hidden" name="method" value="edit"/>
    <input type="hidden" name="post_id" value="${post.post_id}"/>
    <table border="0" align="center" width="80%" style="margin-left: 150px">
        <tr>
            <td width="100px">标题</td>
            <td width="40%">
                <input size="60" type="text" name="title" value="${post.title}"/>
            </td>
            <td align="left">
                <label id="titleError" class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>时间</td>
            <td>
                ${post.time}
            </td>
            <td>
                <label id="timeError"class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>作者id</td>
            <td>
                ${post.author_id}
            </td>
            <td>
                <label id="author_idError"class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>内容</td>
            <td>
                <textarea rows="5" cols="50" name="content">${post.content}</textarea>
            </td>
            <td>
                <label id="contentError"class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="submit" value="编辑帖子"/>
                <input type="reset" name="reset"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>