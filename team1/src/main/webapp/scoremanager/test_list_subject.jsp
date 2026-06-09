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

<!-- test_list_subject.jsp -->