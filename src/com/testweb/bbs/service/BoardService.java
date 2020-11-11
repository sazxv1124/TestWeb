package com.testweb.bbs.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardService {
	
	//추상메서드
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
