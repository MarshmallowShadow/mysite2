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
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) { //수정폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}
		else if("join".equals(action)) { //가입폼 제출 후 가입
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserDao uDao = new UserDao();
			UserVo uVo = new UserVo(id, password, name, gender);
			System.out.println(uVo);
			uDao.userInsert(uVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}
		else if("loginForm".equals(action)) { //로그인 페이지
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		else if("login".equals(action)) { //로그인 시도
			String id = request.getParameter("id");
			String password = request.getParameter("password"); 
			
			UserDao uDao = new UserDao();
			UserVo uVo = new UserVo(id, password);
			UserVo authUser = uDao.getUser(uVo);
			
			if(authUser == null) {
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			} else {
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
			}
			
			WebUtil.redirect(request, response, "./main?");
		}
		else if("modifyForm".equals(action)) { //회원정보 수정
			HttpSession session = request.getSession();
			UserDao uDao = new UserDao();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			int no = authUser.getNo();
			
			UserVo uVo = uDao.getUser(no);
			request.setAttribute("uVo", uVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
		else if("modify".equals(action)) { //수정 시도
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			if(authUser != null)  {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String gender = request.getParameter("gender");
				int no = authUser.getNo();
				
				UserDao uDao = new UserDao();
				UserVo uVo = new UserVo(no, id, password, name, gender);
				
				uDao.update(uVo);
				
				authUser = uDao.getUser(uVo);
				
				session.setAttribute("authUser", authUser);
		    }
			
			WebUtil.redirect(request, response, "./main?");
		}
		else if("logout".equals(action)) { //로그아웃
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "./main?");
		}
		else {
			System.out.println("알 수 없는 action");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
