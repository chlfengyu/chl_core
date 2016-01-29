<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>login</title>
</head>
<body style="font-size: 30px;">
<form action="sys/user/login" method="post">
用户名：<input type="text" name="user.email"><br/>
密码：<input type="text" name="user.password"><br/>
<input type="submit" value="登陆">
</form>
</body>
</html>