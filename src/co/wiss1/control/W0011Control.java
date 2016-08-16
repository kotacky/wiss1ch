package co.wiss1.control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.wiss1.model.W0011Model;

// WebServlet
@WebServlet("/W0011Control")
	public class W0011Control extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try{
			// setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
			request.setCharacterEncoding("UTF-8");

			System.out.println("W11C:通過");
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0011View.jsp");
		    dispatch.forward(request, response);
		}catch(NullPointerException W0011nullException){
			 RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			 dispatch.forward(request, response);
		}
	}
}