package dc.human.kimbanbagi.tableJava.servlet;

import dc.human.kimbanbagi.tableJava.dao.*;
import dc.human.kimbanbagi.tableJava.dto.*;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*

PROJECT        : tablejava
PROGRAM ID    : JoinServlet.java
PROGRAM NAME    : 회원가입
DESCRIPTION    : 회원가입 관련 servlet
AUTHOR        : 이경민
CREATED DATE    : 2024.06.05.
HISTORY
======================================================
DATE     NAME           DESCRIPTION
2024.06.05   이경민        init

*/

@WebServlet("/join")
public class JoinServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 어떤 버튼을 누르는 지 확인
		String action = request.getParameter("action");
		
		// 중복 확인 버튼을 눌렀을 경우
        if ("idCheck".equals(action)) {
            String userId = request.getParameter("userID");
            
            JoinDAO jdao = new JoinDAO();
            
            boolean idCheck = jdao.idCheck(userId);

            if (idCheck) {
                response.getWriter().write("중복된 아이디입니다.");
            } else {
                response.getWriter().write("사용 가능한 아이디입니다.");
            }
            
         // 회원 가입 버튼을 눌렀을 경우
        } else if ("join".equals(action)) {
            String userId = request.getParameter("userID");
            String pwd = request.getParameter("userPW");
            String email = request.getParameter("userEmail");
            String name = request.getParameter("userName");
            String number = request.getParameter("userNumber");
            String role = request.getParameter("role");
            
            // jsp의 radio input을 판별하기 위함 / 사용자는 "1", 사장님은 "2"
			if (role.equals("customer")) {
				role = "1";
			} else if(role.equals("owner")) {
				role = "2";
			}
			
			UserDTO dto = new UserDTO();
			
            dto.setuId(userId);
            dto.setPwd(pwd);
            dto.setEmail(email);
            dto.setName(name);
            dto.setNumber(number);
            dto.setRole(role);
            dto.setRegister("0"); // store_register는 사용자일 경우 항시 "0", 사장님일 경우 가게 등록을 하기 전이기에 "0"으로 초기화
            dto.setWithdrawal("1"); // 탈퇴 여부 = 회원일 시 "1", 탈퇴 회원일 시 "0"
			
			JoinDAO dao = new JoinDAO();
			
			if(dao.join(dto) != 0) {
				request.getRequestDispatcher("joinSuccess.jsp").forward(request, response);
			}else {
				String msg = "오류가 생겼습니다. 회원 가입 조건을 다시 확인해주세요.";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("join.jsp").forward(request, response);
			}
        }
    }
}
