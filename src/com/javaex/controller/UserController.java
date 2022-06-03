package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDao uDao = new UserDao();
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}
		else if("join".equals(action)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo uVo = new UserVo(id, password, name, gender);
			System.out.println(uVo);
			uDao.userInsert(uVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}
		else if("loginForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		else if("login".equals(action)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserVo uVo = new UserVo(id, password);
			UserVo authUser = uDao.getUser(uVo);
			
			if(authUser == null) {
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			} else {
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				WebUtil.redirect(request, response, "./main?");
			}
		}
		else if("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			UserVo uVo = uDao.getUser(no);
			request.setAttribute("uVo", uVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
		else if("modify".equals(action)) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			int no = Integer.parseInt(request.getParameter("no"));
			
			UserVo authUser = new UserVo();
			authUser.setId(id);
			authUser.setName(name);
			authUser.setPassword(password);
			authUser.setGender(gender);
			authUser.setNo(no);
			
			uDao.update(authUser);
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authUser);
			
			WebUtil.redirect(request, response, "./main?");
		}
		else if("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			
			WebUtil.redirect(request, response, "./main?");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
