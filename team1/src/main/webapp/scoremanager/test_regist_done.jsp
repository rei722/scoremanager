<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
    <%-- 成績登録完了画面 --%>
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            <%-- 登録完了メッセージ --%>
            <div class="alert alert-success mx-3">登録が完了しました</div>
            <%-- 画面遷移リンク --%>
            <div class="mx-3 mt-3">
                <a href="TestRegist.action">戻る</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="TestList.action">成績参照</a>
            </div>
        </section>
    </c:param>
</c:import>
<jsp:include page="../footer.html" />
<%-- test_regist_done.jsp --%>