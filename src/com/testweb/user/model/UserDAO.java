package com.testweb.user.model;

import java.sql.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.testweb.util.JdbcUtil;

public class UserDAO {
	
	//UserDAO는 불필요하게 여러개 만들어질 필요가 없기 때문에
	//한개의 객체만 만들어지도록 singleton형식으로 설계합니다
	
	//1. 나자신의 객체를 생성해서 1개로 제한합니다.
	private static UserDAO instance = new UserDAO();
	
	
	//2. 직접 객체를 생성할 수 없도록 생성자에도 private
	private UserDAO() {
		//드라이버 로드
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//커넥션 풀을 얻는 작업
			InitialContext ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
			
			
		} catch (Exception e) {
			System.out.println("드라이브 로드 에러");
		}
	}

	
	//3. 외부에서 객체 생성을 요구할때 getter메서드를 통해 1번의 객체를 반환
	public static UserDAO getInstance() {
		
		return instance;
	}
	
	//--------------------------------------------------
	//DB연결 변수들을 상수로 선언
//	private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
//	private String uid = "JSP";
//	private String upw = "JSP";
	private DataSource ds;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//회원가입 메서드
	public int join(UserVO vo) {
		
		int result = 0; //결과를 반환할 변수
		String sql = "insert into users(id, pw, name, phone, email, address) values(?,?,?,?,?,?)";
		
		try {
			

			//1. conn 객체 생성 
			conn = ds.getConnection();
			//2. pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getAddress());
			//3. sql문 실행
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	//중복검사 메서드
	public int checkId(String id) {
		int result = 0;
		
		String sql = "select * from users where id = ?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			//select 구문의 경우의 실행 executeQuery()
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //rs.next()가 존재한다는것은 중복
				result = 1;
			} else { //존재하지 않는다면 중복이 아님
				result = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public UserVO login(String id, String pw) {
		
		UserVO vo = null;
		String sql = "select * from users where id = ? and pw = ?";
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //성공
				//rs에 담긴 결과를 뽑을 때는 rs.getString(컬럼명), rs.getInt(컬럼명)
//				vo = new UserVO(rs.getString("id"),
//								rs.getString("pw"),
//								rs.getString("name"), 
//								rs.getString("email"),
//								rs.getString("address"),
//								rs.getTimestamp("regdate"));
				vo = new UserVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setAddress(rs.getString("address"));
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
	
	public int update(UserVO user) {
		
		int result = 0;
		String sql = "update users set pw = ?, name = ?, phone = ?, email = ?, address = ? where id = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPw());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPhone());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getId());
			
			result = pstmt.executeUpdate();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public int delete(UserVO user) {
		int result = 0;
		String sql = "delete from users where id = ?";
		try { 
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return result;
	}
}	
	