<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
 
	<c:param name="title">学生管理</c:param>
 
	<c:param name="scripts"></c:param>
 
	<c:param name="content">
		<section class="me-4">
			<div class="main">
 
				<%-- 画面タイトル --%>
				<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
 
				<%-- 新規登録画面へのリンク --%>
				<div class="mb-3 text-end mx-3">
					<a href="StudentCreate.action" class="btn btn-outline-primary">新規登録</a>
				</div>
 
				<%-- 検索条件入力エリア --%>
 
				<form method="post">
					<div class="row border mx-3 py-3 align-items-center rounded"
						id="filter">
						<%-- 入学年度選択 --%>
						<div class="col-2">
							<label class="form-label" for="student-f1-select">入学年度</label>
							<select class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set }">
									<option value="${year }"
										<c:if test="${year == f1}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
 
						<%-- クラス選択 --%>
						<div class="col-2">
							<label class="form-label" for="student-f2-select">クラス</label>
							<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set }">
									<option value="${num }"
										<c:if test="${num==f2 }">selected</c:if>>${num  }</option>
								</c:forEach>
							</select>
						</div>
 
 
						<%-- 在学中のみ表示するチェックボックス --%>
						<div class="col-2 form-check text-center">
							<label class="form-check-label" for="student-f3-check">在学中 
								<input class="form-check-input" type="checkbox"
									id="student-f3-check" name="f3" value="t" <c:if
									test="${!empty f3 }">checked</c:if>
							</label>
						</div>
 					
						<%-- 検索実行ボタン --%>
						<div class="col-2 text-center">
							<button class="btn btn-primary" id="filter-button">絞込み</button>
						</div>
						
						<%--エラーメッセージ--%>
						<div class="col-12">
							<div class="text-warning">
								${errors.f1}
							</div>
						</div>
 
					</div>
				</form>
 
				<%-- 学生データの有無を判定 --%>
				<c:choose>
 
					<%-- データが存在する場合 --%>
					<c:when test="${students.size() >0}">
 
						<%-- 検索件数表示 --%>
						<div class="count mt-3 mx-3">検索結果：${students.size()}件</div>
 
						<%-- 学生一覧テーブル --%>
						<table class="table table-bordered mx-3 mt-2">
							<thead class="table-light">
								<tr>
									<th>入学年度</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th>クラス</th>
									<th class="text-center">在学中</th>
									<th></th>
								</tr>
							</thead>
								<%-- 学生情報を1件ずつ表示 --%>
								<c:forEach var="student" items="${students}">
 
									<tr>
										<td>${student.entYear}</td>
										<td>${student.no}</td>
										<td>${student.name}</td>
										<td>${student.classNum}</td>
 
										<%-- 在学状態を○×で表示 --%>
										<td><c:choose>
												<c:when test="${student.attend}">
									○
								</c:when>
												<c:otherwise>
									×
								</c:otherwise>
											</c:choose></td>
 
										<%-- 学生情報変更画面へのリンク --%>
										<td><a href="StudentUpdate.action?no=${student.no}"> 変更 </a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
 
					<%-- データが存在しない場合 --%>
					<c:otherwise>
 
						<p class="no-data mx-3 mt-3">学生情報が存在しませんでした</p>
 
					</c:otherwise>
				</c:choose>
				
			</div>
		</section>
	</c:param>
</c:import>