package com.javaex.dao;

import java.sql.*;
import com.javaex.vo.UserVo;

public class UserDao {
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
			} if(pstmt != null) {
				pstmt.close();
			} if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	//회원가입 (아이디, 비밀번호, 이름, 성별 저장된 UserVo필요)
	public int userInsert(UserVo uVo) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " insert into users";
			query += " values (seq_users_no.nextval, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPassword());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 등록되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//회원정보 수정 (아이디, 비밀번호, 이름, 성별 저장된 UserVo필요)
	public int update(UserVo uVo) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " update	users";
			query += " set		password = ?,";
			query += " 			name = ?,";
			query += " 			gender = ?";
			query += " where id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getPassword());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setString(4, uVo.getId());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 수정되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//회원 탈퇴 (지금은 필요 없음)
	public int delete(int no) {
		int count = -1;
		
		try {
			getConnection();
			
			String query = "";
			query += " delete from users";
			query += " no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 식제되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return count;
	}
	
	//로그인 (아이디, 비밀번호 들어간 UserVo필수, 회원번호(no), 이름 들어간 authUser 리턴)
	public UserVo getUser(UserVo uVo) {
		UserVo authUser = null;
		
		try {
			getConnection();
			
			String query = "";
			query += " select	no,";
			query += " 			name";
			query += " from users";
			query += " where id = ?";
			query += " and password = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				
				authUser = new UserVo();
				authUser.setNo(no);
				authUser.setName(name);
			}
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return authUser;
	}
	
	//수정 폼 들어가기 전에 필요한 전보 가져오기 (authUser에 있던 no 가져오고 아이디, 비번, 이름, 성별 저장된 UserVo리턴)
	public UserVo getUser(int no) {
		UserVo uVo = null;
		
		try {
			getConnection();
			
			String query = "";
			query += " select	id,";
			query += "			password,";
			query += " 			name,";
			query += "			gender";
			query += " from users";
			query += " where no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString(1);
				String password = rs.getString(2);
				String name = rs.getString(3);
				String gender = rs.getString(4);
				
				uVo = new UserVo();
				uVo.setNo(no);
				uVo.setPassword(password);
				uVo.setName(name);
				uVo.setGender(gender);
				uVo.setId(id);
			}
		} catch(SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return uVo;
	}
}
