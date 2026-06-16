<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html lage="ja">
<head>
<meta charset="UTF-8">
<title>得点管理システム</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
 
<h2>ログイン</h2>
 
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
%>
 
<form action="LoginExecute.action" method="post">
	ID : <input type="text" name="id" value="${id}" required><br>
	PW : <input type="password" name="password" id="password" required><br>
	
	<div style="text-align:center;">
		<input type="checkbox" name="chk_d_ps" id="No4">
		<label for="No4">パスワードを表示</label>
	</div>
	
	<input type="submit" value="ログイン">
</form>

<script>
    document.getElementById('No4').addEventListener('change', function () {
        var pw = document.getElementById('password');
        pw.type = this.checked ? 'text' : 'password';
    });
</script>
 
</body>
</html>