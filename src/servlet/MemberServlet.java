package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Member;
import service.MemberService;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	MemberService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
        service = new MemberService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//��û ������ �ּҰ� �ƴ϶� parameter �� ����
		String command = request.getParameter("action");
		System.out.println("command : " + command);
		
		if(command.equals("join")) {
			// action=join
			System.out.println("join ��û �޾ҽ��ϴ�.");
			Member member = new Member();
			member.setId(request.getParameter("userid"));
			member.setPw(request.getParameter("password"));
			member.setName(request.getParameter("name"));
			member.setEmail(request.getParameter("email"));
			boolean result = service.join(member);
			String data = "";
			// ��� data JSON �������� ������ֱ�
			if(result) {
				data = "{\"result\" : true}";
			} else {
				data = "{\"result\" : false}";
			}
			response.getWriter().print(data);
		} else if (command.equals("checkId")) {
			String userid = request.getParameter("userid");
			Member member = service.getMember(userid);
			String result = "";
			if(member != null) {
				// ���̵� �ߺ� (���Ұ�)
				result = "{\"result\" : false}";
			} else {
				// ���̵� ��� ����
				result = "{\"result\" : true}";
			}
			response.getWriter().print(result);
		} else if (command.equals("checkEmail")) {
			String userEmail = request.getParameter("email");
			String email = service.getEmail(userEmail);
			String result = "";
			//System.out.println(email);
			if(email != null) {
				result = "{\"result\" : false}";
			} else {
				result = "{\"result\" : true}";
			}
			response.getWriter().print(result);
		} else if (command.equals("memberList")) {
			List<Member> memberList = service.getAllMembers();
			String result = new Gson().toJson(memberList);
			// JSON ǥ�������� �ٲ���.
			response.getWriter().print(result);
		}
		
	}
	
}
