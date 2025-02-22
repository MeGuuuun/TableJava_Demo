package dc.human.kimbanbagi.tableJava.servlet;

import java.io.IOException;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

import java.util.*;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*

PROJECT        : tablejava
PROGRAM ID    : WaitServlet.java
PROGRAM NAME    : 웨이팅
DESCRIPTION    : 웨이팅 관련 servlet
AUTHOR        : 반재홍
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   반재홍        init

*/

@WebServlet("/wait")
public class WaitServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		String userId = request.getParameter("userId");
		String restaurantId = request.getParameter("restaurantId");
		String restaurantName = request.getParameter("restaurantName");
		
		WaitDAO dao = new WaitDAO();
		
		// 앞에 대기 팀이 몇 번 있는지 알려주는 메소드
		if(action.equals("waitBtn")) {
			String waitingNumber = dao.getNextWaitingNumber(restaurantId);
			
			request.setAttribute("waitNumber", waitingNumber);
			request.setAttribute("restaurantId", restaurantId);
			request.setAttribute("restaurantName", restaurantName);
			request.setAttribute("userId", userId);
			request.getRequestDispatcher("wait.jsp").forward(request, response);
			
		} else if(action.equals("addBook")) {
			
			String phoneNumber = request.getParameter("phoneNumber");
			String headCount = request.getParameter("headCount");
			String waitNumber = request.getParameter("waitNumber");
			
			WaitDTO dto = new WaitDTO();
			
			dto.setUserId(userId);
			dto.setPhoneNumber(phoneNumber);
			dto.setRestaurantId(restaurantId);
			dto.setRestaurantName(restaurantName);
			dto.setHeadCount(headCount);
			dto.setWaitingNumber(waitNumber);
			
			// 웨이팅 상태 > "0" 웨이팅 중 / "1" 고객 호출 / "2" (사용자) 웨이팅 취소 / "3" (사장님) 웨이팅 취소 / "4" 착석 완료
	        dto.setWaitingStatus("0");
	        
	     // 웨이팅 신청 성공 시 성공 화면으로 이동
	        if(dao.addWaiting(dto)!=0) {
	        	NotificationDAO ndao = new NotificationDAO();
	        	
	        	if(ndao.addWaitNotification(dto)!=0) {
	        		request.setAttribute("userId", userId);
		            request.getRequestDispatcher("/waitSuccess.jsp").forward(request, response);
	        	}else {
	        		// notification 처리 오류
	        	}
	        	
	        } else {
	        	String msg = "웨이팅 신청에 오류가 생겼습니다. 다시 시도해주세요.";
	        	request.setAttribute("msg", msg);
	        	request.setAttribute("userId", userId);
	        	request.setAttribute("restaurantId", restaurantId);
	        	request.getRequestDispatcher("restaurantDetail").forward(request, response);
	        }
		}
       
	}

}
