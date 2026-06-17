<%@ page language = "java" contentType = "text/html; charset = UTF-8"
   	pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">
		学生情報登録
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
 
 		<section class="me-4">
			<%--タイトル--%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
		 
			<form action = "StudentCreateExecute.action" method = "post">
			
				<%-- 入学年度--%>
				<div class = "mb-3">
					<label class="form-label">入学年度</label>
					
					<select name="ent_year" class="form-select" required>
					    <option value="">----------</option>
					
					    <%
					    int currentYear = java.time.Year.now().getValue();
					
					    for (int year = currentYear; year <= currentYear + 10; year++) {
					    %>
					
					        <option value="<%= year %>"
					            <%= String.valueOf(year).equals(String.valueOf(request.getAttribute("ent_year")))
					                ? "selected" : "" %>>
					            <%= year %>
					        </option>
					
					    <%
					    }
					    %>
					
					</select>
					
					<%--入学年度未選択エラー --%>
					 <div class="text-warning">
                        ${entYearError}
                    </div>
				</div>
				
				<%--学生番号 --%>
				<div class = "mb-3">
					<label class="form-label">学生番号</label>
					
					<input type = "text"
							name = "number"
							value = "${number}"
							class="form-control"
							placeholder = "学生番号を入力してください"
							required>
							
					<%--学生番号未入力エラー  --%>
					<p class = "text-warning">${numberError}</p>
					<%--学生番号重複エラー --%>
					<p class = "text-warning">${duplicateError}</p>
				</div>
		 
				<%--氏名 --%>
				<div class = "mb-3">
					<label class="form-label">氏名</label>
					
					<input type = "text"
							name = "name"
							value = "${name}"
							class="form-control"
							placeholder = "氏名を入力してください"
							required>
								
					<%--氏名未入力エラー --%>
					<p class = "text-warning">${nameError}</p>
				</div>
				
				<%--クラス --%>
				<div class="mb-4">
				    <label class="form-label">クラス</label>
				
				    <select name="class_num" class="form-select">
				
				        <c:forEach var="num" items="${class_num_set}">
				            <option value="${num}">
				                ${num}
				            </option>
				        </c:forEach>
				    </select>
				</div>
				<%--登録ボタン --%>
				<div class = "mb-3">
					<input type = "submit" value = "登録して終了" class="btn btn-secondary">
				</div>
			</form>
			
			<%--戻る --%>
			<a href = "StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>