package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			BoardDao bDao = new BoardDao();
			
			List<BoardVo> bList = bDao.getList();
			request.setAttribute("bList", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		else if("writeForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}
		else if("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			
			BoardDao bDao = new BoardDao();
			BoardVo bVo = new BoardVo(title, content, userNo);
			bDao.insert(bVo);
			
			WebUtil.redirect(request, response, "./bc?action=list");
		}
		else if("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			BoardVo bVo = bDao.getBoard(no);
			bDao.plusView(no);
			
			//System.out.println(bVo);
			
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
		else if("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			BoardVo bVo = bDao.getBoard(no);
			
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
