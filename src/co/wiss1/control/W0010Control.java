package co.wiss1.control;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.realm.RealmBase;

import co.wiss1.model.W0010Model;

// WebServlet
@WebServlet("/W0010Control")
public class W0010Control extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		  // フォーム入力を受け取る
		  HttpSession session = request.getSession(true);
		  String userId = request.getParameter("userId");
		  String inputpassword = request.getParameter("password");

	      // パスワードをハッシュ化
	      String hashedpassword = RealmBase.Digest(inputpassword, "SHA-1", "Windows-31J");


		  // ユーザー情報を受け取る
		  HashMap<String, String> UserInfo = W0010Model.getUserInfo(userId);

		  // ユーザー名とパスワードと管理者フラグを取り出す
		  String userName = UserInfo.get("userName");
		  String password = UserInfo.get("password");
		  String adminFlg = UserInfo.get("adminFlg");
		  String font_color = UserInfo.get("font_color");
		  String chk = UserInfo.get("残念");
		  if (hashedpassword.equals(password)){
			  session.setAttribute("userId",userId);
			  session.setAttribute("userName",userName);
			  session.setAttribute("adminFlg",adminFlg);
			  session.setAttribute("font_color", font_color);
			  System.out.println("W0010C font_color=" + font_color + ";");
			  RequestDispatcher dispatch = request.getRequestDispatcher("/W0011Control");
			  dispatch.forward(request, response);

		  }else if(chk.equals("残念")){
			    request.setAttribute("EMSG0001", "ユーザーが存在しません。");
			    RequestDispatcher dispatch = request.getRequestDispatcher("/view/W0010View.jsp");
			    dispatch.forward(request, response);
		  }else{
			    request.setAttribute("EMSG0004", "パスワードが間違っています。");
			    RequestDispatcher dispatch = request.getRequestDispatcher("/view/W0010View.jsp");
			    dispatch.forward(request, response);
		  }


	   /* // ログアウト・セッションの破棄・ログイン画面への遷移


		  HttpSession Session = request.getSession(true);
		  session.setAttribute("userName","admin");
		  session.invalidate();

		  response.sendRedirect("/W0010View"); */

	}

}
