<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 科目別成績一覧 --%>
<div class="mx-3 mt-3">
<%-- 選択科目表示 --%>
	<p class="mb-1">科目：${subject.name}</p>
	<%-- 成績一覧テーブル --%>
	<table class="table">
		<thead>
			<tr>
				<th>入学年度</th>
				<th>クラス</th>
				<th>学生番号</th>
				<th>氏名</th>
				<%-- 回数ごとの列を動的生成 --%>
				<c:forEach begin="1" end="${maxNo}" var="no">
					<th>${no}回</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		<%-- 学生ごとの成績情報表示 --%>
			<c:forEach var="row" items="${testList}">
				<tr>
					<td>${row.entYear}</td>
					<td>${row.classNum}</td>
					<td>${row.studentNo}</td>
					<td>${row.studentName}</td>
					<%-- 回数ごとの点数表示 --%>
					<c:forEach begin="1" end="${maxNo}" var="no">
					    <td>
					        <c:choose>
					            <c:when test="${not empty row.points[no]}">
					                ${row.points[no]}
					            </c:when>
					            <%-- 点数未登録時はハイフン表示 --%>
					            <c:otherwise>
					                ----
					            </c:otherwise>
					        </c:choose>
					    </td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<jsp:include page="../footer.html" />
<%-- test_list_subject.jsp --%>