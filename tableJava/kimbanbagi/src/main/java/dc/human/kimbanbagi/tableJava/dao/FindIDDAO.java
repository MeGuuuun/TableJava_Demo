package dc.human.kimbanbagi.tableJava.dao;

import java.sql.*;

import dc.human.kimbanbagi.tableJava.dto.*;
import dc.human.kimbanbagi.tableJava.common.DBConnectionManager;

/*

PROJECT        : tablejava
PROGRAM ID    : FindIDDAO.java
PROGRAM NAME    : 아이디 찾기
DESCRIPTION    : 아이디 찾기 관련 sql문 처리
AUTHOR        : 반재홍
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   반재홍        init

*/

public class FindIDDAO {
	private Connection conn;
	
	// 사용자가 입력한 email과 name을 데이터 베이스와 대조 해 맞는 회원 정보가 있을 시 user_id 반환
	public String findID(UserDTO dto) {
		String email = dto.getEmail();
		String name = dto.getName();
		String userId = null;
		
		try {
			conn = DBConnectionManager.getConnection();
			
			String sql = "SELECT "
					+ "user_id "
					+ "FROM users "
					+ "WHERE user_email=? "
					+ "AND user_name=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				userId = rs.getString("user_id");
			}
			
			conn.close();
			pstmt.close();
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return userId;
	}
	
}
