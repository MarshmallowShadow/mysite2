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
		
		if("list".equals(action)) { //게시판
			//검색기능에 해당
			String keyword = request.getParameter("keyword");
			
			//다오로 게시글 목록 가져오기
			BoardDao bDao = new BoardDao();
			List<BoardVo> bList = bDao.getList(keyword);
			//System.out.println(bList);
			
			//request에 속성 저장
			request.setAttribute("bList", bList);
			
			//list.jsp로 보내기
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		else if("delete".equals(action)) { //게시글 삭제 (게시글 번호(no) 필수)
			//게시글 번호 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			//System.out.println(no);
			
			//게시글 다오로 삭제 진행
			BoardDao bDao = new BoardDao();
			bDao.delete(no);
			
			//목록으로 다시 돌아가기
			WebUtil.redirect(request, response, "./board?action=list");
		}
		else if("writeForm".equals(action)) { //게시글 작성 폼 (추가 정보 필요 없음)
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}
		else if("write".equals(action)) { //게시글 작성
			//writeForm에서 정보 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content").replace("\n", "<br>");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			
			//Vo에 저장
			BoardVo bVo = new BoardVo();
			bVo.setTitle(title);
			bVo.setContent(content);
			bVo.setUserNo(userNo);
			//System.out.println(bVo);
			
			//다오로 db에 추가
			BoardDao bDao = new BoardDao();
			bDao.insert(bVo);
			
			//목록으로 돌아가기
			WebUtil.redirect(request, response, "./board?action=list");
		}
		else if("read".equals(action)) { //게시글 조회
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao bDao = new BoardDao();
			bDao.plusView(no);
			BoardVo bVo = bDao.getBoard(no);
			//System.out.println(bVo);
			
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
		else if("modifyForm".equals(action)) { //게시글 수정폼
			//게시글 번호 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//다오로 정보 가져오고 Vo에 저장
			BoardDao bDao = new BoardDao();
			BoardVo bVo = bDao.getBoard(no);
			
			//bVo에 html 줄바꿈 태그 삭제
			bVo.setContent(bVo.getContent().replace("<br>", ""));
			
			//bVo request로 보내기
			request.setAttribute("bVo", bVo);
			
			//modifyForm으로 정보 보내고 이동
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		else if("modify".equals(action)) { //게시글 수정
			//수정된 제목, 내용, 그리고 게시글 번호 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content").replace("\n", "<br>"); //content는 일반 줄바꿈에서 html <br> 태그로 줄바꿈 전환
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Vo에 내용 저장
			BoardVo bVo = new BoardVo();
			bVo.setTitle(title);
			bVo.setContent(content);
			bVo.setNo(no);
			
			//다오로 해당 게시글 번호에 있는 제목, 내용 수정
			BoardDao bDao = new BoardDao();
			bDao.modify(bVo);
			
			//다시 해당 게시글 조회 페이지로 이동
			WebUtil.redirect(request, response, "./board?action=read&no=" + no);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
