package dc.human.kimbanbagi.tableJava.servlet;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*

PROJECT        : tablejava
PROGRAM ID    : FindPWServlet.java
PROGRAM NAME    : 비밀번호 찾기
DESCRIPTION    : 비밀번호 찾기 관련 servlet
AUTHOR        : 박지민
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   박지민        init

*/

@WebServlet("/findPW")
public class FindPWServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		// jsp에서 보낸 request 읽기
		String userId = request.getParameter("userID");
		String name = request.getParameter("userName");
		
		UserDTO dto = new UserDTO();
		dto.setuId(userId);
		dto.setName(name);
		
		TempoPWDAO dao = new TempoPWDAO();
		boolean check = dao.check(dto);
		
		// 사용자가 입력한 id와 name과 일치하는 정보를 찾고, 임시 비밀번호까지 성공적으로 지급 했을 시
		if(check) {
			 response.sendRedirect("findPW_done.jsp");
		} else {
			 String msg = "일치하는 정보가 없습니다.";
			 request.setAttribute("msg", msg);
			 request.getRequestDispatcher("findPW.jsp").forward(request, response);
		}
	}

}
