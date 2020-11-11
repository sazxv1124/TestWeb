<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/include/header.jsp" %>
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-9 col-sm-12 join-form">
                    <h2>회원가입<small>(가운데정렬)</small></h2>

                    <form action="joinForm.user" name = "regForm" method = "post">
                        <div class="form-group">
                            <label for="id">아이디</label>
                            <input type="text" class="form-control" id="id" name = "id" placeholder="아이디를 (영문포함 4~12자 이상)" required>
                        </div>
                        <div class="form-group">
                            <label for="password">비밀번호</label>
                            <input type="password" class="form-control" id="password" name = "pw" placeholder="비밀번호 (영 대/소문자, 숫자, 특수문자 3종류 이상 조합 8자 이상)" required>
                        </div>
                        <div class="form-group">
                            <label for="password-confrim">비밀번호 확인</label>
                            <input type="password" class="form-control" id="password-confrim" name = "pwCheck" placeholder="비밀번호를 확인해주세요." required>
                        </div>
                        <div class="form-group">
                            <label for="name">이름</label>
                            <input type="text" class="form-control" id="name" name = "name" placeholder="이름을 입력하세요." required>
                        </div>
                        <!--input2탭의 input-addon을 가져온다 -->
                        <div class="form-group">
                            <label for="hp">휴대폰번호</label><br>
                            
                            <input class="form-control sel" placeholder="010" name = "phone_1" required> -
                            <input class="form-control sel" placeholder="xxxx" name = "phone_2" required> -
                            <input class="form-control sel" placeholder="xxxx" name = "phone_3" required>
                        
                        </div>
                        <div class="form-group">
                             <label for="hp">이메일</label><br>
                            <input class="form-control sel" name = "email" required>@
                            <select class="form-control sel" required>
                                <option>naver.com</option>
                                <option>gmail.com</option>
                                <option>daum.net</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="addr-num">주소</label>
                            <input type="text" class="form-control" id="addr-basic" name = "address_1" placeholder="기본주소">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="addr-detail" name = "address_2" placeholder="상세주소">
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-lg btn-success btn-block" onclick = "check()">회원가입</button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-lg btn-info btn-block" onclick = "location.href = 'login.user'">로그인</button>
                        </div>
                        ${msg }
                        
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
		if(!exp1.test(document.regForm.id.value) || !exp2.test(document.regForm.id.value)){
			alert('숫자와 영문자 조합으로 4~12자리를 사용해야 합니다.');
			flag = false;
			return;
		} else if(!regExpPw.test(document.regForm.pw.value)) {
			alert("비밀번호는 영문 대,소문자, 특수문자, 숫자 세가지 조합으로 8글자 이상 입력해주세요")
			flag = false;
			return;
		} else if(!exp3.test(document.regForm.phone_1.value) || !exp4.test(document.regForm.phone_2.value) || !exp4.test(document.regForm.phone_3.value)) {
			alert("폰 형식을 다시 확인하세요")
			flag = false;
			return;
		} else if(document.regForm.pw.value != document.regForm.pwCheck.value) {
			alert("비밀번호를 다시 확인하세요");
			flag = false;
			return;
		} else if(document.regForm.name.value == '') {
			alert("이름은 필수 입니다.");
			flag = false;
			return;
		} else {
			flag = true;
		}
			if(flag == true) {
				document.regForm.submit();
		} 
		
	}
	</script>



<%@ include file = "../include/footer.jsp" %>