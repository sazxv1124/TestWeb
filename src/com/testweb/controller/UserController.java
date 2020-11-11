package com.testweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testweb.bbs.service.BoardService;
import com.testweb.bbs.service.GetUserListServiceImpl;
import com.testweb.user.service.UserDeleteServiceImpl;
import com.testweb.user.service.UserJoinServiceImpl;
import com.testweb.user.service.UserLoginServiceImpl;
import com.testweb.user.service.UserService;
import com.testweb.user.service.UserUpdateServiceImpl;


@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public UserController() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchServlet(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchServlet(request, response);
	}
	
	protected void dispatchServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//요청분기
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		
		String command = uri.substring(conPath.length());
		
		System.out.println(command);
		
		//부모타입 선언
		UserService service;
		BoardService bservice;
		
		
		if(command.equals("/user/join.user")) { //회원가입 처리
			
			request.getRequestDispatcher("user_join.jsp").forward(request, response);
		
		} else if(command.equals("/user/login.user")) { // 로그인 화면처리
			
			request.getRequestDispatcher("user_login.jsp").forward(request, response);
			
		} else if(command.equals("/user/joinForm.user")) { //회원 가입 요청
			
			service = new UserJoinServiceImpl();
			int result = service.execute(request, response); //중복시 1, 성공시 0
			
			if(result == 1) { //중복
				request.setAttribute("msg", "이미 존재하는 회원입니다.");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);
				
			} else { //성공
				request.setAttribute("sussece", "회원가입이 성공적으로 처리되었습니다");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
			}
			
			
		} else if(command.equals("/user/loginForm.user")) {
			
			/*
			 * 1.UserLoginServiceImpl()로 연결
			 * 2. 폼값을 받아서 DAO의 로그인 메서드를 이용해서 로그인 처리를 합니다.
			 * 3. 로그인을 성공시 user라는 이름으로 세션에 UserVO를 저장(통째로)
			 *    request.getSession(); 자바에서 세션을 얻는 방법
			 *	  mypage로 MVC2방식으로 이동
			 * 4. 로그인 실패시 MSG에 "아이디, 비밀번호를 확인하세요"를 담아서 login.jsp로 이동
			 */
			service = new UserLoginServiceImpl();
			int result = service.execute(request, response);
			if(result == 1) {
				request.setAttribute("msg", "아이디 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
			} else {
				response.sendRedirect("mypage.user");
			}
			
		} else if(command.equals("/user/mypage.user")) {
		
			bservice = new GetUserListServiceImpl();
			bservice.execute(request, response);
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
			
		} else if(command.equals("/user/logout.user")) {
			
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/index.bbs");
			
		} else if(command.equals("/user/updateForm.user" )) { //정보 수정 요청
			
			/*
			 * 1.UserUpdateServiceImpl() 생성 호출
			 * 2. execute메서드에서는 update() 메서드를 실행시키고 성공실패 여부를 받아서 컨트롤로 리턴
			 */
			service = new UserUpdateServiceImpl();
			int result = service.execute(request, response);
			
			if(result == 1) {
				
				//문자열의 형태로 스크립트를 작성해서 out.println() 화면에 전달
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter(); //출력 스트림
				out.println("<script>");
				out.println("alert('업데이트가 정상 처리되었습니다');");
				out.println("location.href = 'mypage.user';");
				out.println("</script>");
				
				
				
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter(); //출력 스트림
				out.println("<script>");
				out.println("alert('유저 정보 업데이트에 실패했습니다');");
				out.println("location.href = 'mypage.user';");
				out.println("</script>");
			}
			
			
			
			
			
		} else if(command.equals("/user/deleteForm.user")) {
			
			service = new UserDeleteServiceImpl();
			int result = service.execute(request, response);
			
			if(result == 1) {
			
				
				response.sendRedirect(request.getContextPath()+"/index.bbs");
				
			} else {
			
				request.setAttribute("check", "비밀번호를 확인하세요");
				request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
				
			}
			/*
			 * 1.UserDeleteServiceImple()생성, 연결
			 * 2.delete() 메서드를 총해 삭제 처리
			 * 3. 성공실패 결과를 컨트롤러로 받아와서 성공시엔, 세션 삭제 후 홈화면
			 * 4. 실패시에는 실패 메세지를 delete.jsp로 처리해주세요
			 * 
			 * 
			 */
			
		} 
	
		
	}

}

