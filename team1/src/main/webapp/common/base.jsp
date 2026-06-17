<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.title}</title>
    <%-- Bootstrap読み込み --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="d-flex flex-column min-vh-100">
<%-- ヘッダー（システム名・ユーザー情報・ログアウト） --%>
    <header class="bg-primary text-white p-3 mb-4 d-flex justify-content-between align-items-center">
    	<h1 class="h4 mb-0">得点管理システム</h1>
    	<div>
    	<%-- ログインユーザー表示 --%>
        	${sessionScope.user.name}様
        	<a href="Logout.action" class="text-white ms-3">ログアウト</a>
    	</div>
	</header>
	
    <div class="container-fluid flex-grow-1">
        <div class="row">
        <%-- サイドメニュー --%>
            <nav class="col-2">
            <%-- メニュー一覧 --%>
                <ul class="list-unstyled">
                    <li><a href="Menu.action">メニュー</a></li>
					<li><a href="StudentList.action">学生管理</a></li>
					<li>成績管理
    					<ul>
        					<li><a href="TestRegist.action">成績登録</a></li>
        					<li><a href="TestList.action">成績参照</a></li>
    					</ul>
					</li>
					<li><a href="SubjectList.action">科目管理</a></li>
                </ul>
            </nav>
            <%-- メインコンテンツ表示領域 --%>
            <main class="col-10">
                ${param.content}
            </main>
        </div>
    </div>
     <!-- フッター -->
    <footer class="bg-secondary text-white text-center p-3">
        © 2026 得点管理システム
    </footer>
</body>
</html>