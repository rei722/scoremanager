<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			<form method="post" action="TestListSubjectExecute.action">
				<div class="row border mx-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
        				<p class="mb-0 ms-4">科目情報</p>
    				</div>
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<option value="${year }"<c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<option value="${num }"<c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subject_set }">
								<option value="${subject.cd }"<c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-1 text-center mt-4">
                        <button class="btn btn-secondary">検索</button>
                    </div>
                    <input type="hidden" name="f" value="sj">
                </div>
			</form>
			<div class="text-danger ms-3">${errors.get("f1")}</div>
			<form method="post" action="TestListStudentExecute.action" class="mt-3">
                <div class="row border mx-3 py-2 align-items-center rounded">
                	<div class="col-2">
        				<p class="mb-0 mt-2 ms-4">学生情報</p>
    				</div>
                    <div class="col-3">
                        <label class="form-label" for="student-no-input">学生番号</label>
                        <input type="text" class="form-control" id="student-no-input"
    						name="studentNo" value="${studentNo}" placeholder="学生番号を入力してください" required>
                    </div>
                    <div class="col-1 text-center mt-4">
                        <button class="btn btn-secondary">検索</button>
                    </div>
                    <input type="hidden" name="f" value="st">
                    <div class="mt-2 text-warning">${errors.get("studentNo")}</div>
				</div>
			</form>
   			<c:choose>
			    <c:when test="${f == 'sj'}">
			        <jsp:include page="test_list_subject.jsp" />
			    </c:when>
			    <c:when test="${f == 'st'}">
			        <jsp:include page="test_list_student.jsp" />
			    </c:when>
			    <c:otherwise>
			        <p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
			    </c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
<jsp:include page="../footer.html" />
<!-- test_list.jsp -->