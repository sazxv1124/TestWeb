package com.testweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.bbs.service.BoardService;
import com.testweb.bbs.service.ContentServiceImpl;
import com.testweb.bbs.service.DeleteServiceImpl;
import com.testweb.bbs.service.GetListServiceImpl;
import com.testweb.bbs.service.RegistServiceImpl;
import com.testweb.bbs.service.UpdateServiceImpl;


@WebServlet("*.bbs")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public BoardController() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatcherServlet(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatcherServlet(request, response);
	}
	
	protected void dispatcherServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		//2.요청분기
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		
		String command = uri.substring( conPath.length());
		System.out.println(command);
		
		//*******************************************************************
		//MVC2에서는 기본적으로 forward이동을 사용하고, 다시 컨트롤러를 태울 때 redirect를 사용합니다
		//service 부모타입 선언
		
		
		BoardService service;
		
		if(command.equals("/board/list.bbs")) {
			service = new GetListServiceImpl();
			service.execute(request, response);
			//목록을 가지고 forward이동
			request.getRequestDispatcher("bbs.jsp").forward(request, response);
			
			//request.getRequestDispatcher("/board/board_list.jsp").forward(request, response);
		} else if(command.equals("/index.bbs")) {
			service = new GetListServiceImpl();
			service.execute(request, response);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		else if(command.equals("/board/write.bbs")) {
			
			request.getRequestDispatcher("bbs_write.jsp").forward(request, response);
			
		} else if(command.equals("/board/regist.bbs")) {
			
			service =  new RegistServiceImpl();
			service.execute(request, response);
			
			response.sendRedirect("list.bbs");
			
		} else if(command.equals("/board/content.bbs")) { //수정화면 요청
			
			
			service = new ContentServiceImpl();
			service.execute(request, response);
			
			request.getRequestDispatcher("bbs_content.jsp").forward(request, response);
			/*
			 * 1.ContentServiceImpl() 재활용
			 * 2. 포워드 형식으로 board_modify.jsp로 이동
			 * 3. 화면에서는 태그안에 데이터 값을 출력
			 * 
			 */
		} else if(command.equals("/board/modify.bbs")){
			
			service = new ContentServiceImpl();
			service.execute(request, response);
			
			request.getRequestDispatcher("bbs_modify.jsp").forward(request, response);
			/*
			 * 1. UpdateServiceImpl()을 생성하고 execute() 메서드 실행
			 * 2. 서비스에서bno, title, content를 받아서 DAO의 update() 메서드로 실행
			 * 3. update()는 sql문으로 수정을 진행
			 * 4. 컨트롤러에서는 페이지 이동을 content화면으로 이동
			 * 
			 */
		} else if(command.equals("/board/update.bbs")){
			
			service = new UpdateServiceImpl();
			service.execute(request, response);
			response.sendRedirect("content.bbs?bno=" + request.getParameter("bno"));
			/*
			 * 1. UpdateServiceImpl()을 생성하고 execute() 메서드 실행
			 * 2. 서비스에서bno, title, content를 받아서 DAO의 update() 메서드로 실행
			 * 3. update()는 sql문으로 수정을 진행
			 * 4. 컨트롤러에서는 페이지 이동을 content화면으로 이동
			 * 
			 */
		}
		
		else if(command.equals("/board/delete.bbs")) {
			/*
			1.화면에서 delete.board요청으로 필요한 값을 get방식으로 넘겨줍니다
			2.DeleteServiceImpl() 생성하고 dao의 delete() 메서드로 실행
			3. 삭제후에 목록 페이지로 이동
			*/
			service = new DeleteServiceImpl();
			service.execute(request, response);
			response.sendRedirect("list.bbs");
		} 
		
	//조회수 업데이트 메서드
	//update board set hit = hit + 1 where
		
			
		
		
		
	}

}
