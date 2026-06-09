<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="mx-3 mt-3">
    <p class="mb-1">氏名：${student.name}（${student.no}）</p>
    <c:choose>
        <c:when test="${empty testList}">
            <p>成績情報が存在しませんでした</p>
        </c:when>
        <c:otherwise>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="mx-3 mt-3">
	<p class="mb-1">科目：${subject.name}</p>
	<table class="table">
		<thead>
			<tr>
				<th>入学年度</th>
				<th>クラス</th>
				<th>学生番号</th>
				<th>氏名</th>
				<c:forEach begin="1" end="${maxNo}" var="no">
					<th>${no}回</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${testList}">
				<tr>
					<td>${row.entYear}</td>
					<td>${row.classNum}</td>
					<td>${row.studentNo}</td>
					<td>${row.studentName}</td>
					<c:forEach begin="1" end="${maxNo}" var="no">
					    <td>
					        <c:choose>
					            <c:when test="${not empty row.points[no]}">
					                ${row.points[no]}
					            </c:when>
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
<!-- test_list_student.jsp -->