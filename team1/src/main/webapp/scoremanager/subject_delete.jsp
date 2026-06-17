<%--科目削除画面 --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">

	
	<c:param name="title">
		科目管理システム - 科目情報削除
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<%--メインコンテンツ --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			
			<div class="mx-3">
			
			<%--科目削除確認フォーム --%>
				<form action="SubjectDeleteExecute.action" method="post" class="needs-validation" novalidate>
					
					<%--削除対象の科目情報表示 --%>
					<div class="mb-3 row">
						<label for="subjectCd" class="col-sm-2 col-form-label">科目コード</label>
						
						<%--確認メッセージ --%>
						<div class="col-sm-4">
								「${subject.name}(${subject.cd})」を削除してもよろしいですか
								<%--削除処理 --%>
							<input type="hidden" name="cd" value="${subject.cd}">
							<input type="hidden" name="name" value="${subject.name}">
							
						</div>
					</div>
					
					<div class="mt-4">
						<button type="submit" class="btn btn-primary me-2">削除</button>
						<a href="SubjectList.action" class="btn btn-outline-secondary">戻る</a>
					</div>
					
				</form>
			</div>
		</section>
	</c:param>
</c:import>
