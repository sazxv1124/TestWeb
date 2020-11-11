<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp" %>
    
<section>
        <div class="container">
            <div class="row join-wrap">
                <!--join-form을 적용한다 float해제 margin:0 auto-->
                <div class="col-xs-12 col-md-9 join-form">
                    
                    <div class="titlebox">
                        MEMBER INFO                    
                    </div>
                    
                    <p>*표시는 필수 입력 표시입니다</p>
                    <form action = "updateForm.user" method = "post" name = "updateForm">
                    <table class="table">
                        <tbody class="m-control">
                            <tr>
                                <td class="m-title">*ID</td>
                                <td><input class="form-control input-sm" name = "id" value = "${sessionScope.user.id }" readonly></td>
                            </tr>
                            <tr>
                                <td class="m-title">*이름</td>
                                <td><input class="form-control input-sm" name = "name" value = "${sessionScope.user.name }" readonly></td>
                            </tr>
                            <tr>
                                <td class="m-title">*비밀번호</td>
                                <td><input class="form-control input-sm" type = "password" name = "pw" required></td>
                            </tr>
                            <tr>
                                <td class="m-title">*비밀번호확인</td>
                                <td><input class="form-control input-sm" type = "password" name = "pwCheck" required></td>
                            </tr>
                            <tr>
                                <td class="m-title">*E-mail</td>
                                <td>
                                    <input class="form-control input-sm" name = "email" required>@
                                    <select class="form-control input-sm sel" required>
                                        <option>naver.com</option>
                                        <option>gmail.com</option>
                                        <option>daum.net</option>
                                    </select>
                                    <button class="btn btn-info">중복확인</button>
                                </td>
                            </tr>
                            <tr>
                                <td class="m-title">*휴대폰</td>
                                <td>
                                    <input class="form-control input-sm sel" name = "phone_1" required> -
                                    <input class="form-control input-sm sel" name = "phone_2" required> -
                                    <input class="form-control input-sm sel" name = "phone_3" required>
                                </td>
                            </tr>
                            <tr>
                                <td class="m-title">*주소</td>
                                <td><input class="form-control input-sm add" name ="address_1" required></td>
                            </tr>
                            <tr>
                                <td class="m-title">*상세주소</td>
                                <td><input class="form-control input-sm add" name ="address_2" required></td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <div class="titlefoot">
                        <button type="button" class="btn" onclick = "check()">수정</button>
                        <button type="button" class="btn" onclick = "history.go(-1)">목록</button>
                    </div>
                    </form>
                </div>


            </div>

        </div>

    </section>
    
        <script>
    
    var exp1 = /^[a-zA-z0-9]{4,12}$/
    var exp2 = /[a-zA-Z]+/;
    var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/;
  	var exp3 = /^[0-9]{3}$/;
  	var exp4 = /^[0-9]{4}$/;
  	
  			
  	
	
	var flag = true;

    

  
   
	


	function check() {
		//form 태그는 유일하게 document.form 이름.이름... 접근이 가능합니다.
		if(!exp1.test(document.updateForm.id.value) || !exp2.test(document.updateForm.id.value)){
			alert('숫자와 영문자 조합으로 4~12자리를 사용해야 합니다.');
			flag = false;
			return;
		} else if(!regExpPw.test(document.updateForm.pw.value)) {
			alert("비밀번호는 영문 대,소문자, 특수문자, 숫자 세가지 조합으로 8글자 이상 입력해주세요")
			flag = false;
			return;
		} else if(!exp3.test(document.updateForm.phone_1.value) || !exp4.test(document.updateForm.phone_2.value) || !exp4.test(document.updateForm.phone_3.value)) {
			alert("폰 형식을 다시 확인하세요")
			flag = false;
			return;
		} else if(document.updateForm.pw.value != document.updateForm.pwCheck.value) {
			alert("비밀번호를 다시 확인하세요");
			flag = false;
			return;
		} else if(document.updateForm.name.value == '') {
			alert("이름은 필수 입니다.");
			flag = false;
			return;
		} else {
			flag = true;
		}
			if(flag == true) {
				document.updateForm.submit();
		} 
		
	}
	</script>
    
    

	
    
    
 <%@ include file = "../include/footer.jsp" %>