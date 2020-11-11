package com.myweb.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testweb.user.model.UserVO;



//게시글 수정, 삭제에 대한 필터
@WebFilter({"/board/modify.bbs","/board/update.bbs","/board/delete.bbs"})

public class BoardFilter2 implements Filter{

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//1. 등록화면에서는 작성자를 id값으로 고정
		//2. 각 요청으로 id가 parameter로 전달되는지 확인
		//3. write화면에서 작성자를 id값으로 고정
		//4. modify.board, update.voard, delete.board 요청으로 넘어갈때 writer를 담아서 보내주도록 처리
		
		
		request.setCharacterEncoding("utf-8");//한글인코딩
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		if(user == null) {
			res.sendRedirect("/TestWeb/user/login.user");
			return;
		}
		String id = user.getId(); //세션에서넘어온 아이디
		String writer = request.getParameter("writer"); //화면에서 넘어온 파라미터값
		
		
		if(writer == null || !id.equals(writer) ) { //writer가 null이거나 권한이 없는경우
			res.setContentType("text/html; charset=UTF-8"); //응답문서설정
			PrintWriter out =  res.getWriter();
			out.println("<script>");
			out.println("alert('권한이 없습니다'); ");
			out.println("location.href='/TestWeb/board/list.bbs';");
			out.println("</script>");
			
			return;
		}
		
		
		chain.doFilter(request, response);
		
		
		
		
	}

	
	
}
