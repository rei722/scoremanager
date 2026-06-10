<%-- 検索条件入力フォーム --%>
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
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<form method="post">
				<div class="row border mx-3 py-2 align-items-center rounded" id="filter">
				<%-- 入学年度選択 --%>
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<option value="${year }"<c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>
					<%-- クラス選択 --%>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<option value="${num }"<c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					<%-- 科目選択 --%>
					<div class="col-3">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subject_set }">
								<option value="${subject.cd }"<c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
							</c:forEach>
						</select>
					</div>
					<%-- 回数選択 --%>
					<div class="col-2">
						<label class="form-label" for="student-f4-select">回数</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="">--------</option>
							<c:forEach var="no" items="${no_set }">
								<option value="${no }"<c:if test="${no==f4 }">selected</c:if>>${no }</option>
							</c:forEach>
						</select>
					</div>
					<%-- 検索ボタン --%>
					<div class="col-1 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<%-- 検索条件エラーメッセージ --%>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>
			<c:choose>
			<%-- 検索結果表示 --%>
    			<c:when test="${not empty test_list}">
        			<section class="me-4 mt-4">
        			<%-- 成績登録フォーム --%>
            			<form method="post" action="TestRegistExecute.action">
            				<input type="hidden" name="f1" value="${f1}">
            				<input type="hidden" name="f2" value="${f2}">
            				<input type="hidden" name="f3" value="${f3}">
            				<input type="hidden" name="f4" value="${f4}">
            				<%-- 科目名・回数表示 --%>
		        			<p>科目：${subject.name}（${f4}回）</p>
		        			<%-- 成績一覧 --%>
		        			<table class="table">
		            			<thead>
		                			<tr>
		                    			<th>入学年度</th>
		                    			<th>クラス</th>
		                    			<th>学生番号</th>
		                    			<th>氏名</th>
		                    			<th>点数</th>
		                    			<th>削除</th>
		                			</tr>
		            			</thead>
		            			<tbody>
		            			<%-- 学生ごとの成績表示 --%>
		                			<c:forEach var="test" items="${test_list}" varStatus="status">
		                    			<tr>
		                        			<td>
		                            			<input type="hidden" name="student_no_${status.index}" 
		                                   			value="${test.student.no}">
		                            				${test.student.entYear}
		                        			</td>
		                        			<td>${test.student.classNum}</td>
		                        			<td>${test.student.no}</td>
		                        			<td>${test.student.name}</td>
		                        			<td>
		                        			<%-- 点数入力 --%>
		                            			<input type="text" class="form-control" 
		                                   			name="point_${status.index}" 
		                                   			value="${test.point >= 0 ? test.point : ''}">
		                                   		<c:if test="${not empty errors.get('point_'.concat(status.index))}">
        											<div class="text-warning">
        											<%-- 点数入力エラー表示 --%>
            											${errors.get('point_'.concat(status.index))}
        											</div>
    											</c:if>
		                        			</td>
		                        			<%-- 成績削除ボタン --%>
		                        			<td>
											    <button
											        type="submit" class="btn btn-danger btn-sm" formaction="TestDeleteExecute.action"
											        name="student_no"
											        value="${test.student.no}"
											        onclick="return confirm('この成績を削除しますか？');">
											        削除
											    </button>
											</td>
		                    			</tr>
		                			</c:forEach>
		            			</tbody>
		        				</table>
		        			<button class="btn btn-primary">保存</button>
		        		</form>
    				</section>
    			</c:when>
    			<c:otherwise>
    			<%-- 検索結果が存在しない場合 --%>
        			<c:if test="${not empty f1 && f1 != 0}">
        			<%-- 該当データなしメッセージ --%>
            			<div class="mx-3 mt-3 text-muted">該当する学生情報がありません</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
<jsp:include page="../footer.html" />
<%-- test_regist.jsp --%>