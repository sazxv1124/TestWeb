<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ include file = "../include/header.jsp" %>
        
    <section>
        
        <div class="container">
        <div class = "container">
			<select onchange = "change(this)">
				<option value = "10" ${pageVO.amount == 10 ? 'selected' : ''}>10개씩 보기</option>
				<option value = "20" ${pageVO.amount == 20 ? 'selected' : ''}>20개씩 보기</option>
				<option value = "50" ${pageVO.amount == 50 ? 'selected' : ''}>50개씩 보기</option>
				<option value = "100" ${pageVO.amount == 100 ? 'selected' : ''}>100개씩 보기</option>
			</select>
		
		</div>
            <div class="row">
                
                <h2>게시판 목록</h2>
                <table class="table table-striped" style="text-align: center; border: 2px solid #737373">
                    <thead>
                        <tr>
                            <th style="background-color: #9DCAFF; text-align: center;">번호</th>
                            <th style="background-color: #9DCAFF; text-align: center;">제목</th>
                            <th style="background-color: #9DCAFF; text-align: center;">작성자</th>
                            <th style="background-color: #9DCAFF; text-align: center;">작성일</th>
                        </tr>
                    </thead>
                   <c:forEach var = "vo" items = "${list }">
					<tbody>
						<tr>
						<td>${vo.bno }</td>
						<td>
							<a href = "content.bbs?bno=${vo.bno }">${vo.title }</a>
						</td>
						<td>${vo.writer }</td>
						<td><fmt:formatDate value = "${vo.regdate }" pattern = "yyyy년 MM월dd일"/></td>
					
					
				</tr>
			</tbody>
			
			</c:forEach>
                        
                </table>

                <div class="text-center">
                    <ul class="pagination pagination-sm">
                       	<!-- 2. 이전버튼 활성화 여부 -->
	               				<c:if test="${pageVO.prev }">
                        		<li><a href="list.bbs?pageNum=${pageVO.startPage - 1 }&amount=${pageVO.amount}">이전</a></li>
                        		</c:if>
                        		<!-- 1. 페이지 번호처리, li엑티브 여부-->
                        		<c:forEach var = "num" begin ="${pageVO.startPage }" end = "${pageVO.endPage }">
                        		<li class = "${num eq pageVO.pageNum ? 'active' : '' }">
                        		<a href="list.bbs?pageNum=${num }&amount=${pageVO.amount}">${num }</a></li>
                        		</c:forEach>
                        		
                        		<!-- 3. 다음버튼 활성화 여부 -->
                        		<c:if test ="${pageVO.next }">
                        			<li><a href="list.board?pageNum=${pageVO.endPage+1}&amount=${pageVO.amount}">다음</a></li>
                        		</c:if>
                    			</ul>
                   <button class="btn btn-info pull-right" onclick = "location.href = 'bbs_write.jsp'">글쓰기</button>
                </div>
                
            </div>
        </div>
    </section>
    
    <script>
	function change(a) {
		//console.log(a)
		//console.log(a.value)
		location.href = "list.bbs?pageNum=1&amount=" + a.value;
	}
	</script>
        
<%@ include file = "../include/footer.jsp" %>