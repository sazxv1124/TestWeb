<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp" %>

    <section>
        <div class="container" style="margin-top: 5%;">
            <div class="row">
                <form action = "regist.bbs">
                    <table class="table table-striped" >
                        <thead>
                            <tr>
                                <th colspan="2" style="background-color: #9DCAFF; text-align: center;">게시판 글쓰기</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="text" class="form-control" placeholder="작성자" name="writer" value = "${sessionScope.user.id }" maxlength="50" readonly></td>
                            </tr>
                            <tr>
                                <td><input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50" required></td>
                            </tr>
                            <tr>
                                <td><textarea rows="15" class="form-control" placeholder="1000 글자 이하" name="content" maxlength="1000" required></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <input type="button" class="btn btn-primary pull-left" value="목록" onclick = "history.go(-1)">
                    <input type="submit" class="btn btn-primary pull-right" value="글쓰기">
                </form>
            </div>
        </div>
    </section>
 <%@ include file = "../include/footer.jsp" %>