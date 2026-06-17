<%--科目登録画面 --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">


	<c:param name="title">
		科目管理システム - 科目情報登録
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<%--メインコンテンツ --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>
			
			<%--科目登録 --%>
			<div class="mx-3">
				<form action="SubjectCreateExecute.action" method="post" class="needs-validation">
					
					<%--科目コード入力 --%>
					<div class="mb-3 row">
						<label for="subjectCd" class="col-sm-2 col-form-label">科目コード</label>
						<input type="text" class="form-control" id="subjectCd" name="cd" 
								placeholder="例: 科目コードを入力してください" required>
								
						<%--エラー表示 --%>		
						<div class="text-warning">
							${errors.cd}
						</div>
					
						
					</div>
					
					<%--科目名入力 --%>
					<div class="mb-3 row">
						<label for="subjectName" class="col-sm-2 col-form-label">科目名</label>
						<input type="text" class="form-control" id="subjectName" name="name" 
							placeholder="例: 科目名を入力してください" required>
							
							<%--エラー表示 --%>
						<div class="text-warning">
							${errors.name}
						</div>
					</div>
					
					<div class="mt-4">
						<button type="submit" class="btn btn-primary me-2">登録</button>
						<a href="SubjectList.action" class="btn btn-outline-secondary">戻る</a>
					</div>
					
				</form>
			</div>
		</section>
	</c:param>
</c:import>
<jsp:include page="../footer.html" />