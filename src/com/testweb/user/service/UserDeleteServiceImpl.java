package com.testweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.user.model.UserDAO;
import com.testweb.user.model.UserVO;

public class UserDeleteServiceImpl implements UserService{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
	
		UserVO user = (UserVO)request.getSession().getAttribute("user");
		UserDAO dao = UserDAO.getInstance();
		String pw = request.getParameter("pw");
		if(user != null) {
			
			UserVO check = dao.login(user.getId(), pw);
			if(check == null) {
				return 0;
			} else {
				dao.delete(user);
				request.getSession().invalidate();
				return 1;
			}
		}
		
		
		return 0;
		
		
				
		
		
	}

	
	
}
