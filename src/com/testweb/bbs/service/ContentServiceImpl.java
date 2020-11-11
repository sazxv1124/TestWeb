package com.testweb.bbs.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.bbs.model.BoardDAO;
import com.testweb.bbs.model.BoardVO;

public class ContentServiceImpl implements BoardService{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		
		String bno = request.getParameter("bno");
		//DAO객체 생성, 상세보기 메서드 실행
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO vo = dao.getContent(bno);
		
		//request에 vo저장
		request.setAttribute("vo", vo);
		
	}
	
	

}
