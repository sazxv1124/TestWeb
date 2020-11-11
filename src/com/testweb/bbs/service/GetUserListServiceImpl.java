package com.testweb.bbs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testweb.bbs.model.BoardDAO;
import com.testweb.bbs.model.BoardVO;
import com.testweb.user.model.UserVO;
import com.testweb.util.PageVO;

public class GetUserListServiceImpl implements BoardService{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = BoardDAO.getInstance();
		int pageNum  = 1;
		int amount = 10;
		HttpSession session = request.getSession();
		UserVO vo = (UserVO)session.getAttribute("user");
		
		String id = vo.getId();
		
		if(request.getParameter("pageNum") != null || request.getParameter("amount") != null || request.getParameter("id") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			amount = Integer.parseInt(request.getParameter("amount"));
		}
		
		List<BoardVO> userlist = dao.getuserList(id, pageNum, amount);
		
		
		
		int total = dao.getTotal();
		PageVO pageVO = new PageVO(pageNum, amount, total);
		request.setAttribute("userlist", userlist);
		request.setAttribute("pageVO", pageVO);
		
		
		
	}

	
	
}
