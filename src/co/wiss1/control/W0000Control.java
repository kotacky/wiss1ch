package co.wiss1.control;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/W0000Control")
public class W0000Control extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

		/*httpsessionで権限を持たせる*/
		HttpSession session = request.getSession(true);

		if (session == null) {
			System.out.println("セッションは破棄されました");
		} else {
			System.out.println("セッションが残っています");
		}

		session.invalidate();
		session = request.getSession(false);
		RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");

		if (session == null) {
			System.out.println("セッションは破棄されました");
			dispatch.forward(request, response); // 画面遷移 セッション不具合の原因
		} else {
			System.out.println("セッションが残っています");
		/*	RequestDispatcher dispatc =getServletContext().getRequestDispatcher("/view/W0030View.jsp");
			dispatc.forward(request, response); // 検証用 セッションが残っていると0030へ遷移 */
		}

		session = request.getSession(false);

	}
}