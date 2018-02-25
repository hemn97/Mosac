<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mosac后台管理系统</title>
</head>
<script type="text/javascript">  
    var time = 4;  
  
    function returnUrlByTime() {  
  
        window.setTimeout('returnUrlByTime()', 1000);  
  
        time = time - 1;  
  
        document.getElementById("layer").innerHTML = time;  
    }  
    function go()
    {
    	window.history.go(-1); 
    }
    setTimeout("go()",3000);
</script> 

<body style="text-align: center;" onload="returnUrlByTime()">
<h1 style="color:green;">${msg}</h1>
<h3><span id="layer">3</span>秒后，自动返回上一页</h3>
<a href="#" onclick="javascript:history.go(-1);">立即返回</a>
</body>
</html>