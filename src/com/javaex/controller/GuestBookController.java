package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import com.javaex.dao.*;
import com.javaex.vo.*;
import com.javaex.util.*;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		GuestBookDao gDao = new GuestBookDao();
		String action = request.getParameter("action");
		
		if("addList".equals(action)) { //방명록 페이지
			List<GuestVo> gList = gDao.getList();
			
			request.setAttribute("gList", gList);
			
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");
		}
		else if("deleteForm".equals(action)) { //삭제 확인 (비밀번호 입력)
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/deleteForm.jsp");
		}
		else if("add".equals(action)) { //방명록에 추가 시도
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestVo gVo = new GuestVo(name, password, content);
			
			gDao.insert(gVo);
			
			WebUtil.redirect(request, response, "./gbc?action=addList");
		}
		else if("delete".equals(action)) { //비밀번호 확인 후 삭제 시도
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			int confirm = gDao.delete(no, password);
			
			if(confirm > 0) {
				WebUtil.redirect(request, response, "./gbc?action=addList");
			} else {
				WebUtil.redirect(request, response, "./gbc?action=deleteForm&no=" + no);
			}
		}
		else {
			System.out.println("unknown action");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
