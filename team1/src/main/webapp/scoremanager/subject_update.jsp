<%--科目情報変更画面 --%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		科目管理システム - 科目情報変更
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<%--メインコンテンツ --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			
			<%--変更 --%>
			<div class="mx-3">
				<form action="SubjectUpdateExecute.action" method="post" class="needs-validation" novalidate>
					
					<%--科目コード(表示のみ) --%>
					<div class="mb-3">
						<label for="subjectCd" class="form-label">科目コード</label>
						<div>
							${subject.cd}
							<%--送信 --%>
							<input type="hidden" name="cd" value="${subject.cd}">
						</div>
					</div>
					
					<%--科目名入力 --%>
					<div class="mb-3 row">
						<label for="subjectName" class="col-sm-2 col-form-label">科目名</label>
							<input type="text" class="form-control" id="subjectName" name="name" value="${subject.name}" 
								placeholder="例: 科目名を入力してください" required>
								
							<%--エラー --%>
							<div style="color:orange";>
								${errors.name}
							</div>
						</div>
					</div>
					
					<div class="mt-4">
						<button type="submit" class="btn btn-primary me-2">変更</button>
						<a href="SubjectList.action" class="btn btn-outline-secondary">戻る</a>
					</div>
					
				</form>
			</div>
		</section>
	</c:param>
</c:import>
<jsp:include page="../footer.html" />