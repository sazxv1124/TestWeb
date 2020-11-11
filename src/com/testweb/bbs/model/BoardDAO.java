package com.testweb.bbs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.testweb.user.model.UserDAO;
import com.testweb.user.model.UserVO;
import com.testweb.util.JdbcUtil;

public class BoardDAO {	//UserDAO는 불필요하게 여러개 만들어질 필요가 없기 때문에
	//한개의 객체만 만들어지도록 singleton형식으로 설계합니다
	
	//1. 나자신의 객체를 생성해서 1개로 제한합니다.
	private static BoardDAO instance = new BoardDAO();
	
	
	//2. 직접 객체를 생성할 수 없도록 생성자에도 private
	private BoardDAO() {
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
	public static BoardDAO getInstance() {
		
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
	
	//글 등록 메서드
	public void regist(String writer, String title, String content) {
		
		String sql = "insert into board(bno, title, writer, content) values (board_seq.nextval, ?, ?, ?)";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			
			pstmt.executeUpdate(); //sql문 실행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	
	//전체 게시글 수
	public int getTotal() {
		int total = 0;
		String sql = "select count(*) as total from board";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt("total");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return total;
	}
	
	public List<BoardVO> getuserList(String id, int pageNum, int amount) {
		List<BoardVO> userlist = new ArrayList<>();
		String sql = "select * from (select rownum rn, bno, title, writer, content, regdate  from (select * from board where writer = ? order by bno desc)) where rn >= ? and rn <= ?"; 
		
		System.out.println("id: " + id + " pageNum: " + pageNum + " amount" + amount);
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, (pageNum - 1) * amount+1); //(페이지번호 -1) * 데이터개수
			pstmt.setInt(3, pageNum * amount);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				userlist.add(new BoardVO(
						rs.getInt("bno"),
						rs.getString("title"),
						rs.getString("writer"),
						rs.getString("content"),
						rs.getTimestamp("regdate")));
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
				
		return userlist;
	}
	
	
	
	public List<BoardVO> getList(int pageNum, int amount) {
		List<BoardVO> boardlist = new ArrayList<>();
		String sql = "select * from(select rownum rn, bno, writer, title, content, regdate from (select * from board order by bno desc)) where rn >= ? and rn <= ?"; 
			
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * amount+1); //(페이지번호 -1) * 데이터개수
			pstmt.setInt(2, pageNum * amount);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				boardlist.add(new BoardVO(
						rs.getInt("bno"),
						rs.getString("title"),
						rs.getString("writer"),
						rs.getString("content"),
						rs.getTimestamp("regdate")));
			
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
				
		return boardlist;
	}

	public BoardVO getContent(String bno) {
		BoardVO vo = new BoardVO();
	
		String sql = "select * from board where bno = ?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setBno(rs.getInt("bno"));
				vo.setWriter(rs.getString("writer"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		/*
		 * 번호 기준으로 select 구문으로 조회해서 BoardVO에 저장하고,
		 * vo이름으로 화면에 데이터를 전달
		 * 
		 */
		
		return vo; 
	}
	
	public void update(String bno, String title, String content) {
		BoardVO vo = null;
		String sql = "update board set title = ?, content = ? where bno = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, bno);
			
			pstmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	
	}
	
	public void delete(String bno) {
		
		String sql = "delete from board where bno = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	
	

}
