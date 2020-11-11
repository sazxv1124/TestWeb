package com.testweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.user.model.UserDAO;
import com.testweb.user.model.UserVO;

public class UserUpdateServiceImpl implements UserService{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone_1")+"-"+request.getParameter("phone_2")+"-"+request.getParameter("phone_3");
		String email = request.getParameter("email");
		String address = request.getParameter("address_1")+request.getParameter("address_2");
		UserVO user = new UserVO(id, pw, name, phone, email, address, null);
				
		UserDAO dao = UserDAO.getInstance();
		
		if(dao.update(user) == 1) {
			
			request.getSession().setAttribute("user", user);
			return 1;
			
		} else {
			
			return 0;
			
		}
		
		
		
	}

	
	
}
