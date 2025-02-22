package dc.human.kimbanbagi.tableJava.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dc.human.kimbanbagi.tableJava.dto.*;
import dc.human.kimbanbagi.tableJava.common.DBConnectionManager;

/*

PROJECT        : tablejava
PROGRAM ID    : JoinDAO.java
PROGRAM NAME    : 회원가입
DESCRIPTION    : 회원가입 관련 sql문 처리
AUTHOR        : 이경민
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   이경민        init

*/

public class JoinDAO {
	private Connection conn;
	int row=0; // sql문 처리가 잘 됐는지 확인
	
	// update 또는 insert 시 updated_date / created_date 칼럼에 사용
	java.util.Date now = new java.util.Date();
	Date sqlDate = new Date(now.getTime()); 

    public int join(UserDTO dto) {
        try {
        	conn = DBConnectionManager.getConnection();
        	
            String sql = "INSERT INTO users ("
            		+ "user_id,"
            		+ "user_pwd,"
            		+ "user_email,"
            		+ "user_name,"
            		+ "phone_number,"
            		+ "user_role,"
            		+ "store_register,"
            		+ "withdrawal_status,"
            		+ "created_date,"
            		+ "created_id) "
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dto.getuId());
            pstmt.setString(2, dto.getPwd());
            pstmt.setString(3, dto.getEmail());
            pstmt.setString(4, dto.getName());
            pstmt.setString(5, dto.getNumber());
            pstmt.setString(6, dto.getRole());
            pstmt.setString(7, dto.getRegister());
            pstmt.setString(8, dto.getWithdrawal());
            pstmt.setDate(9, sqlDate);
            pstmt.setString(10, dto.getuId());

            row = pstmt.executeUpdate();
            
            conn.close();
            pstmt.close();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return row;
    }
    
    //아이디 중복 확인 메소드
    public boolean idCheck(String id) {
        boolean result = false;
        
        try {
        	conn = DBConnectionManager.getConnection();
        	
            String sql = "SELECT "
            		+ "user_id "
            		+ "FROM users "
            		+ "WHERE user_id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	result = true;
            }
            
            conn.close();
            pstmt.close();
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}