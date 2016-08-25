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
import co.wiss1.model.W0060Model;

@WebServlet("/W0060Control")
public class W0060Control extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
		//sessionから取得したuserNameをNull対応
		HttpSession session = request.getSession(true);
		try{
			String loginUser = session.getAttribute("userId").toString();
			System.out.println("W0060C SessionのuserIdが"+ loginUser +"に入力されました");
			String actionId = request.getParameter("actionId");

			request.setAttribute("updateFlg", 0);

			//ユーザ一覧から更新で遷移してきた場合
			if("move".equals(actionId)) {
				request.setAttribute("updateFlg", 1);

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				System.out.println("moveからやってきた！！");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );

				request.setAttribute("userId", userId);
				request.setAttribute("userName", userName);
				request.setAttribute("userAddress", userAddress);

				// パスワードをハッシュ化
				//	  String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
			}

			//ユーザ情報更新画面で更新ボタンを押したとき
			if ("update".equals(actionId)) {
				System.out.println("W0060C updateします。");

				String userId = request.getParameter("hiddenUserid");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String passWord = request.getParameter("passWord");
				String conPassword = request.getParameter("conPassword");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );
				System.out.println("W0060C パスワードは"+ passWord +"です" );
				System.out.println("W0060C パスワードは"+ conPassword +"です" );

				// パスワードをハッシュ化
				String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
				int registar = W0060Model.updateUser(loginUser,userId,userName,userAddress,hashedpassword,conPassword);
				request.setAttribute("registar",registar);
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/W0050Control");
				dispatch.forward(request, response);

			}

			//ユーザ一覧からユーザ登録ボタンを押したとき

			if ("userRegist".equals(actionId)){
				System.out.println("W0060C 新規登録画面にいきます。");


				request.setAttribute("userId", "");
				request.setAttribute("userName", "");
				request.setAttribute("userAddress", "");



			}

			//ユーザ新規登録画面で登録ボタンを押したとき
			if ("Registration".equals(actionId)) {

				System.out.println("W0060CS insertします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String passWord = request.getParameter("passWord");
				String conPassword = request.getParameter("conPassword");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );
				System.out.println("W0060C パスワードは"+ passWord +"です" );
				System.out.println("W0060C パスワードは"+ conPassword +"です" );

				// パスワードをハッシュ化
				String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
				int registar = W0060Model.registarUser(loginUser,userId,userName,userAddress,hashedpassword,conPassword);
				request.setAttribute("registar",registar);
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/W0050Control");
				dispatch.forward(request, response);

			}


			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0060View.jsp");
			dispatch.forward(request, response);


		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}