package com.javaex.dao;

import java.sql.*;
import java.util.*;
import com.javaex.vo.*;

public class GuestBookDao {
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
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	
	public int insert(GuestVo gVo) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " insert into guestbook";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gVo.getName());
			pstmt.setString(2, gVo.getPassword());
			pstmt.setString(3, gVo.getContent());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 추가되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	
	public int delete(int no, String password) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " delete from guestbook";
			query += " where no = ?";
			query += " and password = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 삭제되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	
	public List<GuestVo> getList() {
		List<GuestVo> gList = new ArrayList<>();
		
		try {
			getConnection();
			
			String query = "";
			query += " select	no,";
			query += " 			name,";
			query += " 			password,";
			query += " 			content,";
			query += " 			to_char(reg_date, ?)";
			query += " from guestbook";
			query += " order by no";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "YYYY-MM-DD HH:MM:SS");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestVo gVo = new GuestVo(no, name, password, content, regDate);
				
				gList.add(gVo);
			}
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return gList;
	}
}
