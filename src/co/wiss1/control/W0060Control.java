package co.wiss1.control;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import co.wiss1.model.W0060Model;

@WebServlet("/W0060Control")
public class W0060Control
extends HttpServlet{

	//リクエストパラメータの取得
		String regflg = getInitParameter("regflg");

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{

		String regflg = request.getParameter("regflg");

			//sessionから取得したuserNameをNull対応
			HttpSession session = request.getSession(true);
			String reg = "";
			{
			try{
			    String user = session.getAttribute("userName").toString();
			    System.out.println("SessionのuserNameが"+ user +"に入力されました");
			    if (regflg == reg ){  //ここでユーザーの新規登録か変更かを判定。

			   //登録時の処理
			   //初期値
			  int ret = 0;

			  //Viewからフォーム入力の値を受け取る
				//UTF-8にエンコード
				request.setCharacterEncoding("UTF-8");
				String user_Id = request.getParameter("User_ID");
				System.out.print("値に" + user_Id + "が入力されました。");

				//Viewから新規登録情報を受け取る
				String userId = request.getParameter("User_ID");
				System.out.println("ControlのuserIdに" + userId + "が入力されている。");

				String Username = request.getParameter("user_Name");
				System.out.print("値に" + Username + "が入力されました。");

				String Useraddress = request.getParameter("User_address");
				System.out.print("値に" + Useraddress + "が入力されました。");

				String Password = request.getParameter("pass_word");
				System.out.print("値に" + Password + "が入力されました。");

				String ConfirmtionPassword = request.getParameter("confirmtion_password");
				System.out.print("値に" + ConfirmtionPassword + "が入力されました。");

				String Fontcolor = request.getParameter("font_color");
				 int FontColor = Integer.valueOf(Fontcolor).intValue();      //FontColorのデータを数値データに変換
				System.out.print("値に" + FontColor + "が入力されました。");

				if(Password == ConfirmtionPassword)
				{ret = W0060Model.insertCount(userId, Username, Password, Fontcolor);}
				else{
					return;
				}
				    //Viewの画面に戻す
				if(ret == 1){
				    //登録成功
				    request.setAttribute("insertFlag",1);
				    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/W0050View.jsp");
				    dispatch.forward(request, response);
							 }
				else{
				    //登録失敗
				    request.setAttribute("insertFlag",0);
				    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0060View.jsp");
				    dispatch.forward(request, response);
					 }




											}


//更新時の処理
	else{
		  //初期値
		  int ret1 = 0;

		  //ModelからユーザーIDを元にDB上の情報を受け取る
			//UTF-8にエンコード
			request.setCharacterEncoding("UTF-8");
			//String user_Id = W0060Model.selectsql("User_ID");
			//System.out.print("値に" + user_Id + "が入力されました。");

			//Viewから新規登録情報を受け取る
			String userId = request.getParameter("User_ID");
			System.out.println("ControlのuserIdに" + userId + "が入力されている。");

			String Username = request.getParameter("user_Name");
			System.out.print("値に" + Username + "が入力されました。");

			String Useraddress = request.getParameter("User_address");
			System.out.print("値に" + Useraddress + "が入力されました。");

			String Fontcolor = request.getParameter("font_color");
			 int FontColor = Integer.valueOf(Fontcolor).intValue();      //FontColorのデータを数値データに変換
			System.out.print("値に" + FontColor + "が入力されました。");

	}
			}
			    catch(NullPointerException W0060nullException){
					  RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
					  dispatch.forward(request, response);}
					return;
			}
			}
	}