package dc.human.kimbanbagi.tableJava.servlet;

import java.io.IOException;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*

PROJECT        : tablejava
PROGRAM ID    : LoginServlet.java
PROGRAM NAME    : 로그인
DESCRIPTION    : 로그인 관련 servlet
AUTHOR        : 김문정
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   김문정        init

*/

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		// jsp에서 보낸 request 읽기
		String id=request.getParameter("user_id");
		String pwd=request.getParameter("user_pwd");
		
		LoginDAO dao= new LoginDAO();
		String role = dao.match(id, pwd);
		
		if(role == null || role == "") {
			String msg = "일치하는 회원 정보가 없습니다.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		// user_role이 "1"이면 사용자 / "2"이면 사장님
		else if(role.equals("1")) {
			
			// request에 userId 넣어 UserMain 서블릿으로 포워딩
			request.setAttribute("userId", id);
			request.getRequestDispatcher("userMain").forward(request, response);
			
		} else if(role.equals("2")) {
			
			// 사장님의 가게 등록 여부 확인 후 결과에 맞는 페이지로 이동
			if(dao.getRegister(id)) {
				String restaurantId = dao.getRid(id);
				
				request.setAttribute("userId", id);
				// 가게가 등록되어 있다면 restaurant_id까지 request에 넣어 OwnerMain 서블릿으로 포워딩
				request.setAttribute("restaurantId", restaurantId);
				request.getRequestDispatcher("ownerMain").forward(request, response);
				
			}else {
				request.setAttribute("userId", id);
				// 가게 미등록 시 등록 페이지로 이동
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
			
		// role이 null일 시 처리 코드 작성	
		} 
	}

}
