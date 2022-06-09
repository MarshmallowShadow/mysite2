package com.javaex.dao;

import java.util.List;
import com.javaex.vo.BoardVo;

public class TestApp {
	public static void main(String[] args) {
		BoardDao boardDao = new BoardDao();
		
		List<BoardVo> boardVo = boardDao.getList();
		System.out.println(boardVo);
	}
}
