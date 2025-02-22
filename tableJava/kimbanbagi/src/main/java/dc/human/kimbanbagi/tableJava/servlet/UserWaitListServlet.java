package dc.human.kimbanbagi.tableJava.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

/*

PROJECT        : tablejava
PROGRAM ID    : UserWaitListServlet.java
PROGRAM NAME    : 사용자 웨이팅 내역 리스트
DESCRIPTION    : 사용자의 웨이팅 내역을 보여주고 관리 함
AUTHOR        : 반재홍
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   반재홍        init

*/

@WebServlet("/UserWaitList")
public class UserWaitListServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// jsp에서 보낸 request 읽어오기
		String userId = request.getParameter("userId");
		
		String action = request.getParameter("action");
		
		WaitDAO dao = new WaitDAO();
		
		// 사용자의 웨이팅 리스트를 보여주는 선택지
		if(action.equals("waitList")){
			List<WaitDTO> waitList = dao.getUserWaitingList(userId);
			
			request.setAttribute("userId", userId);
			request.setAttribute("waitList", waitList);
			request.getRequestDispatcher("/userWaitList.jsp").forward(request, response);
			
		// 웨이팅 상태 변경 처리 메소드
		}else if (action.equals("status")) {
			String restaurantId = request.getParameter("restaurantId");
			
			String status = request.getParameter("status");
			
			if(dao.changeStatus(status, userId, restaurantId) != 0 ) {
				NotificationDAO ndao = new NotificationDAO();
				
				String restaurantName = request.getParameter("restaurantName");
				
				if(ndao.waitCancelFromUser(userId, restaurantId, restaurantName)!=0) {
					
					request.setAttribute("userId", userId);
					request.getRequestDispatcher("/userWaitList.jsp").forward(request, response);
				}else {
					// notification 처리 오류
				}
			} else {
				String msg = "웨이팅 취소에 오류가 발생했습니다. 다시 시도해주세요.";
				request.setAttribute("userId", userId);
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/userWaitList.jsp").forward(request, response);
			}
		}
	}

}


