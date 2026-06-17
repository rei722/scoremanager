<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		科目管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目一覧</h2>
			
			<div class="mb-3 text-end mx-3">
				<a href="SubjectCreate.action" class="btn btn-outline-primary">新規登録</a>
			</div>
			
			<table class="table mx-3">
				<thead>
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<th></th> <th></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when  test="${not empty subjectList}">
							<c:forEach var="subject" items="${subjectList}">
								<tr>
									<td>${subject.cd}</td>
									<td>${subject.name}</td>
									<td>
										<a href="SubjectUpdate.action?cd=${subject.cd}" class="btn btn-sm btn-success">変更</a>
									</td>
									<td>
										<a href="SubjectDelete.action?cd=${subject.cd}" class="btn btn-sm btn-danger">削除</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
<!--								<td colspan="4" class="text-center text-muted py-4">-->
									
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</section>
	</c:param>
</c:import>
<jsp:include page="../footer.html" />