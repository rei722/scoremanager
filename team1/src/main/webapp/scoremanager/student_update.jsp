<%@ page language="java" contentType="text/html; charset = UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">学生情報変更</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<div class="main">
				<%--タイトル --%>
				<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

				<%--入学年度 --%>
				<div class="mb-3">
					<form action="StudentUpdateExecute.action" method="post"
						class="needs-validation" >
							 <label>入学年度</label>
							<div>
								${student.entYear} <input type="hidden" name="entYear"
									value="${student.entYear}">
							</div>
					</div>
	
	
					<%--学生番号 --%>
					<div class="mb-3">
						<label>学生番号</label>
						<div>
							${student.no} <input type="hidden" name="no" value="${student.no}">
						</div>
					</div>
	
					<%--氏名 --%>
					<div class="form-group">
						<label>氏名</label> <input type="text" name="name" value="${student.name}"
							placeholder="氏名を入力してください" maxlength="30" required>
	
						<%--エラーメッセージ --%>
						<p class="error">${nameError}</p>
					</div>
	
					<%--クラス --%>
					<select class="form-select" name="classNum">
						<c:forEach var="num" items="${class_num_set}">
							<option value="${num}"
								<c:if test="${num == student.classNum}">
	                selected
	            </c:if>>
								${num}</option>
						</c:forEach>
					</select>
	
					<%--在学中 --%>
					<div class="form-group check-group mt-3">
	
						<label>在学中</label> <input type="checkbox" name="is_attend"
							value="true"
							<%if (Boolean.TRUE.equals(request.getAttribute("is_attend"))) {%>
							checked <%}%>>
	
					</div>
	
					<%--変更ボタン --%>
					<div class="button mt-3">
						<input type="submit" value="変更" class="btn btn-primary">
					</div>
	
	
					<%--戻る --%>
					<div class="back">
						<a href="StudentList.action">戻る</a>
					</div>

				</form>
			</div>
		</section>
	</c:param>
</c:import>
