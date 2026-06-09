<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.title}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <header class="bg-primary text-white p-3 mb-4 d-flex justify-content-between align-items-center">
        <h1 class="h4">得点管理システム</h1>
        <div>
        	${sessionScope.user.name}様
        	<a href="Logout.action" class="text-white ms-3">ログアウト</a>
        </div>
    </header>
    <div class="container-fluid">
        <div class="row">
            <nav class="col-2">
                <ul class="list-unstyled">
                    <li><a href="${pageContext.request.contextPath}/scoremanager/Menu.action">メニュー</a></li>
                    <li><a href="${pageContext.request.contextPath}/scoremanager/StudentList.action">学生管理</a></li>
                    <li>成績管理
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/scoremanager/TestRegist.action">成績登録</a></li>
                            <li><a href="${pageContext.request.contextPath}/scoremanager/TestList.action">成績参照</a></li>
                        </ul>
                    </li><li><a href="${pageContext.request.contextPath}/scoremanager/SubjectList.action">科目管理</a>
                </ul>
            </nav>
            <main class="col-10">
                ${param.content}
            </main>
        </div>
    </div>
</body>
</html>