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

//유저 서블릿 이르 정해주기
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//거의 항상 필수 사항 (encoding지정, action 가져오기)
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) { //가입 폼 (jsp로 보낼 정보는 없음)
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}
		else if("join".equals(action)) { //가입폼(joinForm) 제출 후 가입 진행
			//joinForm.jsp에서 입력한 정보들 가져오기 (아이디, 비밀번호, 이름, 성별)
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//다오 생성 (db에 연결 준비)
			UserDao uDao = new UserDao();
			
			//UserVo생성(필드 저장)
			UserVo uVo = new UserVo(id, password, name, gender);
			//UserVo 내용들이 제대로 보관되었는지 확인
			System.out.println(uVo);
			//다오를 통해 새로운 유저 db에 저장
			uDao.userInsert(uVo);
			
			//가입성공했다고 알리기 위해서 joinOk.jsp보여주기
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}
		else if("loginForm".equals(action)) { //로그인 페이지
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		else if("login".equals(action)) { //로그인 시도
			//loginForm.jsp에 정보 가져오기
			String id = request.getParameter("id");
			String password = request.getParameter("password"); 
			
			//다오 생성 (db에 연결 준비)
			UserDao uDao = new UserDao();
			//UserVo생성(필드 저장)
			UserVo uVo = new UserVo(id, password);
			//authUser라는 UserVo생성 (다오로 password, id 확인해서 생성)
			UserVo authUser = uDao.getUser(uVo);
			
			if(authUser == null) { //id, password가 일치하지 않을 경우 loginForm으로 이동
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			} else { //일치할 경우
				System.out.println("로그인 성공");
				
				//session만들기
				HttpSession session = request.getSession();
				//session에 속성값 저장
				session.setAttribute("authUser", authUser);
				
				//main으로 redirect
				WebUtil.redirect(request, response, "./main?");
			}
			
		}
		else if("logout".equals(action)) { //로그아웃
			//session 가져오기
			HttpSession session = request.getSession();
			
			//authUser제거
			session.removeAttribute("authUser");
			//session없에기
			session.invalidate();
			
			//main으로 이동
			WebUtil.redirect(request, response, "./main?");
		}
		else if("modifyForm".equals(action)) { //회원정보 수정
			//no를 가져오기 위해서 session가져오기
			HttpSession session = request.getSession();
			//session에 authUser가져와서 no저장
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//다오 생성
			UserDao uDao = new UserDao();
			//uVo생성해서 getUser로 정보 저장
			UserVo uVo = uDao.getUser(no);
			//request로 보내기
			request.setAttribute("uVo", uVo);
			
			//modifyForm으로 정보들 forward
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
		else if("modify".equals(action)) { //수정 시도
			//로그인 되어있는지 확인 위해서 session 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인 상태일 경우에만 수정 실행
			if(authUser != null)  {
				//modifyForm이 보낸 정보들 가져오기
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String gender = request.getParameter("gender");
				
				//다오랑 Vo만들기
				UserDao uDao = new UserDao();
				UserVo uVo = new UserVo(id, password, name, gender);
				
				//uDao로 uVo에 있는 정보로 db데이터 수정
				uDao.update(uVo);
				
				//혹시나 이름도 변경 되었으면 authUser다시 저장하기
				authUser = uDao.getUser(uVo);
				session.setAttribute("authUser", authUser);
		    }
			
			//메인으로 이동
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
