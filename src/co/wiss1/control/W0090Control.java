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

import co.wiss1.model.W0050Model;
import co.wiss1.model.W0090Model;

@WebServlet("/W0090Control")
public class W0090Control extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		//sessionから取得したuserNameをNull対応
		HttpSession session = request.getSession(true);
		try{
			String loginUser = session.getAttribute("userId").toString();
			System.out.println("W0090C SessionのuserIdが"+ loginUser +"に入力されました");
			String actionId = request.getParameter("actionId");

			request.setAttribute("updateFlg", "0");

			//送信ボタンを押したときの処理
			if ("send_message".equals(actionId)) {
				System.out.println("W0090C registarします。");

				String messageTitle = request.getParameter("messageTitle");
				String message = request.getParameter("message");
				String rUserId = request.getParameter("rUserId");
				System.out.println("W0090C 件名は" + messageTitle + "です");
				System.out.println("W0090C 本文は" + message + "です");
				System.out.println("W0090C 送信先は" + rUserId + "です");

				int registar = W0090Model.sendMessage(loginUser,messageTitle,message,rUserId);
				System.out.println("W0090C registar 終了" + registar);
				request.setAttribute("registar",registar);


			}

			// 初期表示 と削除後の再検索したユーザ一覧を取得
			List<HashMap<String, String>> userList = W0090Model.getUserList();
			//System.out.println(userList());
			// ユーザ一覧が空ではなく1件以上存在する場合、ユーザ一覧をセット
			if (userList != null && 0 < userList.size()) {
				request.setAttribute("userList", userList);
			}

			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0090View.jsp");
			dispatch.forward(request, response);


		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}