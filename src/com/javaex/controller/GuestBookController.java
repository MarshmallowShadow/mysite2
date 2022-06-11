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
		String action = request.getParameter("action");
		
		if("addList".equals(action)) { //방명록 페이지
			//다오로 글 목록 가져오기
			GuestBookDao gDao = new GuestBookDao();
			List<GuestVo> gList = gDao.getList();
			
			//request를 통해서 보내기
			request.setAttribute("gList", gList);
			
			//방명록 페이지 이동
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");
		}
		else if("add".equals(action)) { //방명록에 추가 시도
			//입력한 정보들 addList.jsp에서 가져오기
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//정보를 Vo에 저장
			GuestVo gVo = new GuestVo(name, password, content);
			
			//다오로 db에 정보 추가
			GuestBookDao gDao = new GuestBookDao();
			gDao.insert(gVo);
			
			//메인페이지로 이동
			WebUtil.redirect(request, response, "./gbc?action=addList");
		}
		else if("deleteForm".equals(action)) { //삭제 확인 (비밀번호 입력)
			//삭제 확인 페이지로 이동
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/deleteForm.jsp");
		}
		else if("delete".equals(action)) { //비밀번호 확인 후 삭제 시도
			//deleteForm에 no랑 password 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			//System.out.println(no + ", " + password);
			
			//삭제 시도 (성공이면 confirm 은 1, 실패면 0 이하)
			GuestBookDao gDao = new GuestBookDao();
			int confirm = gDao.delete(no, password);
			
			if(confirm > 0) { //성공일 경우
				WebUtil.redirect(request, response, "./gbc?action=addList");
			} else { //실패일 경우
				WebUtil.redirect(request, response, ("./gbc?action=deleteForm&no=" + no));
			}
		}
		else {
			System.out.println("알 수 없는 action");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
