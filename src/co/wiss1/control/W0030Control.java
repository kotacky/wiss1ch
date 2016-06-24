package co.wiss1.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.wiss1.model.W0030Model;

@WebServlet("/W0030Control")
public class W0030Control extends HttpServlet{

    public static void main (String args[]) {

    }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{

		//初期値
		int ret = 0;

    	//登録する処理INSERT
			//Viewからフォーム入力の値を受け取る
			//UTF-8にエンコード
			request.setCharacterEncoding("UTF-8");
			String categoryName = request.getParameter("categoryName");
			System.out.print("値に" + categoryName + "が入力されました。");
			//ViewからuserNameを受け取る
			String userName = request.getParameter("userName");
			System.out.println("ControlのUserNameに" + userName + "が入力されている。");


				//Model重複チェックメソッドから重複カウントを受け取る
				int getOverlapCount = W0030Model.getOverlapCount(categoryName);
				System.out.print("重複カウント" + getOverlapCount + "が入力されました。");
				if(getOverlapCount == 0){
					//カテゴリ追加メソッドから挿入した桁数を受け取る
					ret = W0030Model.insertCategory(categoryName,userName);
				}else{
					//getAttributeメソッドでrequestからViewが取得する //重複しています
					request.setAttribute("getOverlapCount",getOverlapCount);
				}
		//Viewの画面に戻す
		if(ret == 1){
			//登録成功
			request.setAttribute("insertFlag",1);
			//
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/W0020Control");
			dispatch.forward(request, response);
		}
		else{
			//登録失敗
			request.setAttribute("insertFlag",0);
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0030View.jsp");
			dispatch.forward(request, response);
		}

	}
}