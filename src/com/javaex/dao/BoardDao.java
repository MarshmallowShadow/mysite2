package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch(ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	private void close() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	//글 작성 후에 추가
	public int insert(BoardVo bVo) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " insert into board";
			query += " values(seq_board_no.nextval, ?, ?, 0, sysdate, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 등록되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//글 삭제
	public int delete(int no) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " delete from board";
			query += " where no=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 삭제되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//게시글 누를때마다 조회수 1개 오르기
	public int plusView(int no) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " update	board";
			query += " set		hit = hit+1";
			query += " where	no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//게시글 제목, 내용 수정
	public int modify(BoardVo bVo) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " update	board";
			query += " set		title = ?,";
			query += " 			content = ?";
			query += " where	no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 수정되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//게시판에 나열할 게시글 목록 가져오기
	public List<BoardVo> getList(String keyword){
		List<BoardVo> bList = new ArrayList<>();
		if(keyword==null) {
			keyword = "";
		}
		
		try {
			getConnection();
			
			String query = "";
			query += " select b.no,";
			query += " 		title,";
			query += " 		hit,";
			query += " 		to_char(reg_date,'YY-MM-DD HH24:MM'),";
			query += " 		name,";
			query += " 		user_no";
			query += " from users u, board b";
			query += " where user_no = u.no";
			query += " and (title like ?";
			query += " or name like ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				String name = rs.getString(5);
				int userNo = rs.getInt(6);
				
				BoardVo bVo = new BoardVo();
				bVo.setNo(no);
				bVo.setTitle(title);
				bVo.setHit(hit);
				bVo.setRegDate(regDate);
				bVo.setName(name);
				bVo.setUserNo(userNo);
				
				bList.add(bVo);
			}
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return bList;
	}
	
	
	//read에 필요한 해당 개시글에 정보 가져오기
	public BoardVo getBoard(int no) {
		BoardVo bVo = new BoardVo();
		
		try {
			getConnection();
			
			String query = "";
			query += " select title,";
			query += " 		content,";
			query += " 		hit,";
			query += " 		reg_date,";
			query += " 		name,";
			query += "		user_no";
			query += " from users u, board b";
			query += " where user_no = u.no";
			query += " and	b.no = ?";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				String name = rs.getString(5);
				int userNo = rs.getInt(6);
				
				bVo.setTitle(title);
				bVo.setContent(content);
				bVo.setHit(hit);
				bVo.setRegDate(regDate);
				bVo.setUserNo(userNo);
				bVo.setName(name);
			}
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return bVo;
	}
}
