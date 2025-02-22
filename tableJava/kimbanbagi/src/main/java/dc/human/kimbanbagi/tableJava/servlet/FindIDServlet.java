package dc.human.kimbanbagi.tableJava.servlet;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*

PROJECT        : tablejava
PROGRAM ID    : FindIDServlet.java
PROGRAM NAME    : 아이디 찾기
DESCRIPTION    : 아이디 찾기 관련 servlet
AUTHOR        : 반재홍
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   반재홍        init

*/

@WebServlet("/findID")
public class FindIDServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// jsp에서 보낸 request 읽기
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		
		UserDTO dto = new UserDTO();
		dto.setEmail(email);
		dto.setName(name);
		
		FindIDDAO dao = new FindIDDAO();
		String userId = dao.findID(dto);
		
		// ID를 찾았을 시 findID_OK로 이동, 찾지 못 했을 시 findID_NO로 이동
		if(userId != null) {
			request.setAttribute("userId", userId);
			request.getRequestDispatcher("findID_OK.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("findID_NO.jsp").forward(request, response);
		}
		
	}
	
	
}
