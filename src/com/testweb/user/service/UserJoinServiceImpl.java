package com.testweb.user.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testweb.user.model.UserDAO;
import com.testweb.user.model.UserVO;

public class UserJoinServiceImpl implements UserService{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone_1")+"-"+request.getParameter("phone_2")+"-"+request.getParameter("phone_3");
		String email = request.getParameter("email");
		String address = request.getParameter("address_1")+request.getParameter("address_2");
		
		
		//중복검사
		UserDAO dao = UserDAO.getInstance();
		int result = dao.checkId(id); //중복시 1, 아닐 시 0을 반환
		
		if(result == 1) { // 이미 존재하는 회원
			return 1;
		} else {
			UserVO vo = new UserVO(id, pw, name, phone, email, address, null);
			dao.join(vo); //성공이라고 가정
			return 0;
		}
				
					
	}

	
	
}
