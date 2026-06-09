<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ include file="../header.html" %>

 <!DOCTYPE html>
<html lage="ja">
<head>
<meta charset="UTF-8">
<title>得点管理システム</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
 
<h2>得点管理システム</h2>
 
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
%>
 
<form action="LoginExecute.action" method="post">
	ID : <input type="text" name="id" required><br>
	PW : <input type="password" name="password" required><br>
	
	<input type="submit" value="ログイン">
</form>
</body>
</html>
