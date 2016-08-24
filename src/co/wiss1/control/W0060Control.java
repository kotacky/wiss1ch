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

import co.wiss1.model.W0040Model;
import co.wiss1.model.W0060Model;

@WebServlet("/W0060Control")
public class W0060Control extends HttpServlet{

    public static void main (String args[]) {

    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
			request.setCharacterEncoding("UTF-8");
			//sessionから取得したuserNameをNull対応
			HttpSession session = request.getSession(true);
			try{
			    String user = session.getAttribute("userName").toString();
			    System.out.println("W0060C SessionのuserNameが"+ user +"に入力されました");
			    String actionId = request.getParameter("actionId");
			  //初期値
			  //int ret = 0;
			  if ("Registration".equals(actionId)) {
					System.out.println("W0060C insertします。");

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
					int registar = W0060Model.registarUser(userId,userName,userAddress,hashedpassword,conPassword);
					request.setAttribute("registar",registar);
				}else{
					
				}
			  /*
			  //Viewからフォーム入力の値を受け取る
				//UTF-8にエンコード
				request.setCharacterEncoding("UTF-8");
				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String userAddress = request.getParameter("userAddress");
				String passWord = request.getParameter("passWord");
				String conPassword = request.getParameter("conPassword");

				System.out.println("ユーザIDに" + userId + "が入力されました。");
				System.out.println("ユーザ名に" + userName + "が入力されました。");
				System.out.println("住所に" + userAddress + "が入力されました。");
				System.out.println("パスワードに" + passWord + "が入力されました。");
				System.out.println("確認用パスワードに" + conPassword + "が入力されました。");



				//if() ユーザ登録ボタンを押した時
				//List<HashMap<String, String>> userList = W0060Model.getBlank(userId);
				// ユーザ情報変更の初期表示
				List<HashMap<String, String>> userList = W0060Model.getUserList(userId);
				if (userList != null && 0 < userList.size()) {
					request.setAttribute("userList", userList);
				}
				*/

				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0060View.jsp");
			    dispatch.forward(request, response);
			}catch(NullPointerException W0030nullException){
				 RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/view/W0010View.jsp");
				 dispatch.forward(request, response);
			}
	}
}