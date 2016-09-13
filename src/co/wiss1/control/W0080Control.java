package co.wiss1.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.realm.RealmBase;
import co.wiss1.model.W0080Model;

@WebServlet("/W0080Control")
public class W0080Control extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		//sessionから取得したuserNameをNull対応
		HttpSession session = request.getSession(true);
		try{
			String loginUser = session.getAttribute("userId").toString();
			System.out.println("W0080C SessionのuserIdが"+ loginUser +"に入力されました");
			String actionId = request.getParameter("actionId");

			request.setAttribute("updateFlg", "0");

			//送信ボタンを押したときの処理
			if ("Registration".equals(actionId)) {
				System.out.println("W0080C registarします。");

				String inquiry = request.getParameter("inquiry");

				int registar = W0080Model.sendInquiry(loginUser,inquiry);
				System.out.println("W0080C registar 終了" + registar);
				request.setAttribute("registar",registar);

				if(registar == 0){
					System.out.println("W0080C registar0に入りました" + registar);
					RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0080View.jsp");
					dispatch.forward(request, response);
					return;
				}else{
					RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/W0050Control");
					dispatch.forward(request, response);
					return;
				}
			}


			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0080View.jsp");
			dispatch.forward(request, response);


		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}