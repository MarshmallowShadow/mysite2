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

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			String keyword = request.getParameter("keyword");
			BoardDao bDao = new BoardDao();
			
			List<BoardVo> bList = bDao.getList(keyword);
			
			request.setAttribute("bList", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			bDao.delete(no);
			
			WebUtil.redirect(request, response, "./board?action=list");
		}
		else if("writeForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}
		else if("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content").replace("\n", "<br>");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			
			BoardDao bDao = new BoardDao();
			BoardVo bVo = new BoardVo();
			bVo.setTitle(title);
			bVo.setContent(content);
			bVo.setUserNo(userNo);
			bDao.insert(bVo);
			
			WebUtil.redirect(request, response, "./board?action=list");
		}
		else if("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			bDao.plusView(no);
			BoardVo bVo = bDao.getBoard(no);
			//System.out.println(bVo);
			
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
		else if("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			BoardVo bVo = bDao.getBoard(no);
			
			bVo.setContent(bVo.getContent().replace("<br>", ""));
					
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		else if("modify".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content").replace("\n", "<br>");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			BoardVo bVo = new BoardVo();
			bVo.setTitle(title);
			bVo.setContent(content);
			bVo.setNo(no);
			bDao.modify(bVo);
			
			WebUtil.redirect(request, response, "./board?action=read&no=" + no);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
