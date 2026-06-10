<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 学生別成績一覧 --%>
<div class="mx-3 mt-3">
<%-- 学生情報表示 --%>
    <p class="mb-1">氏名：${student.name}（${student.no}）</p>
    <%-- 成績データ有無判定 --%>
    <c:choose>
    <%-- 成績データが存在しない場合 --%>
        <c:when test="${empty testList}">
            <p>成績情報が存在しませんでした</p>
        </c:when>
        <%-- 成績一覧表示 --%>
        <c:otherwise>
        <%-- 成績一覧テーブル --%>
            <table class="table">
                <thead>
                    <tr>
                        <th>科目名</th>
                        <th>科目コード</th>
                        <th>回数</th>
                        <th>点数</th>
                    </tr>
                </thead>
                <tbody>
                <%-- 科目ごとの成績表示 --%>
                    <c:forEach var="row" items="${testList}">
                        <tr>
                            <td>${row.subjectName}</td>
                            <td>${row.subjectCd}</td>
                            <td>${row.num}</td>
                            <td>${row.point}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../footer.html" />
<%-- test_list_student.jsp --%>