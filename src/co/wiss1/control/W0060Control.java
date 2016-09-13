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

			request.setAttribute("updateFlg", "0");

			//ユーザ一覧から更新で遷移してきた場合
			if("move".equals(actionId)) {
				request.setAttribute("updateFlg", "1");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String userMail = request.getParameter("userMail");
				System.out.println("moveからやってきた！！");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );
				System.out.println("W0060C メールアドレスは"+ userMail +"です" );

				request.setAttribute("userId", userId);
				request.setAttribute("userName", userName);
				request.setAttribute("userAddress", userAddress);
				request.setAttribute("userName", userMail);
				request.setAttribute("registar", "1");
			}

			//ユーザ情報更新画面で更新ボタンを押したとき
			if ("update".equals(actionId)) {
				System.out.println("W0060C updateします。");

				String userId = request.getParameter("hiddenUserid");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String userMail = request.getParameter("userMail");
				String passWord = request.getParameter("passWord");
				String conPassword = request.getParameter("conPassword");
				String fontColor = request.getParameter("fontColor");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );
				System.out.println("W0060C ユーザー名は"+ userMail +"です" );
				System.out.println("W0060C パスワードは"+ passWord +"です" );
				System.out.println("W0060C 再確認パスワードは"+ conPassword +"です" );


				// パスワードをハッシュ化
				String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
				int registar = W0060Model.updateUser(loginUser,userId,userName,userAddress,userMail,hashedpassword,conPassword, fontColor);
				//自分の情報を更新していた場合
				if(loginUser.equals(userId)){
					session.setAttribute("font_color", fontColor);
				}
				request.setAttribute("registar",registar);
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/W0050Control");
				dispatch.forward(request, response);
				return;
			}

			//ユーザ一覧からユーザ登録ボタンを押したとき
			if ("userRegist".equals(actionId)){
				System.out.println("W0060C 新規登録画面にいきます。");

				request.setAttribute("userId", "");
				request.setAttribute("userName", "");
				request.setAttribute("userAddress", "");
				request.setAttribute("userMail", "");
				request.setAttribute("registar",4);
			}

			//ユーザ新規登録画面で登録ボタンを押したとき
			if ("Registration".equals(actionId)) {
				System.out.println("W0060C registarします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String userMail = request.getParameter("userMail");
				String passWord = request.getParameter("passWord");
				String conPassword = request.getParameter("conPassword");
				String fontColor = request.getParameter("fontColor");
				System.out.println("W0060C ユーザーIDは"+userId +"です" );
				System.out.println("W0060C ユーザー名は"+ userName +"です" );
				System.out.println("W0060C 住所は"+ userAddress +"です" );
				System.out.println("W0060C メールアドレスは"+ userMail +"です" );
				System.out.println("W0060C パスワードは"+ passWord +"です" );
				System.out.println("W0060C 再確認パスワードは"+ conPassword +"です" );
				System.out.println("W0060C 文字色は"+ fontColor +"です" );

				// パスワードをハッシュ化
				String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
				int registar = W0060Model.registarUser(loginUser,userId,userName,userAddress,userMail,hashedpassword,conPassword,fontColor);
				System.out.println("W0060C registar 終了" + registar);
				request.setAttribute("registar",registar);

				if(registar == 0){
					System.out.println("W0060C registar0に入りました" + registar);
					RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0060View.jsp");
					dispatch.forward(request, response);
					return;
				}else{
					RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/W0050Control");
					dispatch.forward(request, response);
					return;
				}
			}


			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0060View.jsp");
			dispatch.forward(request, response);


		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}