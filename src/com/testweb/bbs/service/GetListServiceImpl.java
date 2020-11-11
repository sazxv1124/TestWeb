package com.testweb.bbs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.bbs.model.BoardDAO;
import com.testweb.bbs.model.BoardVO;
import com.testweb.util.PageVO;

public class GetListServiceImpl implements BoardService{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = BoardDAO.getInstance();
	//	List<BoardVO> boardlist = dao.getList(); //호출결과를 리스트로 반환
		//첫번째 페이지 진입할때 값
		int pageNum = 1;
		int amount = 10;
		
		//pageNum이 넘어올때 pageNum, amount값을 세팅
		if(request.getParameter("pageNum") != null || request.getParameter("amount") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum") );
			amount = Integer.parseInt(request.getParameter("amount"));
		}
		
		List<BoardVO> boardlist = dao.getList(pageNum, amount);
		
				
		//2.pageVO생성
		int total = dao.getTotal(); //전체 게시글 수
		PageVO pageVO = new PageVO(pageNum, amount, total);
		
		
		
		//화면에 가져가기 위해 request에 list를 저장
		request.setAttribute("list", boardlist);
		
		//3. 페이지네이션을 화면에 전달
		request.setAttribute("pageVO", pageVO);
		
		
	}

	
	
}
