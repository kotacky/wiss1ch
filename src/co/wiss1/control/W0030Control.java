package co.wiss1.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import co.wiss1.model.W0030Model;
import co.wiss1.model.W0040Model;

@WebServlet("/W0030Control")
public class W0030Control extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{

			//sessionから取得したuserNameをNull対応
			HttpSession session = request.getSession(true);
			try{
			    String user = session.getAttribute("userName").toString();
			    System.out.println("W0030C :SessionのuserNameが"+ user +"に入力されました");

			    //初期値
			    int ret = -1;
				request.setAttribute("update_flag", 0);

			    //Viewからフォーム入力の値を受け取る
				//UTF-8にエンコード
				request.setCharacterEncoding("UTF-8");

				// アクションIDの取得
				String actionId = request.getParameter("actionId");
				System.out.println("W0030C : actionIdは" + actionId + "です");

				//登録する処理INSERT
				if("insert".equals(actionId)) {
					System.out.println("W0030C: Welcome INSERT.");
					//ViewからカテゴリIDを受け取る
					String categoryName = request.getParameter("categoryName");
					System.out.println("W0030C cateName:" + categoryName + "が入力されました。");

					//ViewからuserIdを受け取る
					String userId = request.getParameter("userId");
					System.out.println("W0030C USERID:" + userId + "が入力されている。");

					//Viewから親カテゴリIDを受け取る
					String parentId = request.getParameter("pldw");
					System.out.println("W0030C ParentID:" + parentId + "が入力されている。");

					//Model重複チェックメソッドから重複カウントを受け取る
					int getOverlapCount = W0030Model.getOverlapCount(categoryName);
					System.out.println("W0030C :重複カウント" + getOverlapCount + "が入力されました。");

					if(getOverlapCount == 0){
					    //カテゴリ追加メソッドから挿入した桁数を受け取る
					    ret = W0030Model.insertCategory(categoryName, parentId, userId);
					}
					else{
					    //getAttributeメソッドでrequestからViewが取得する
					    request.setAttribute("getOverlapCount",getOverlapCount);
					}
				}

				request.setAttribute("categoryId", 200);
				// カテゴリ更新のための画面変更if文
				if("update1st".equals(actionId)) {
					System.out.println("W0030C update1stに来ました");
					request.setAttribute("update_flag", 1);
					String checkBox[] = request.getParameterValues("chkbox");
					System.out.println("W40C checkBox[]に" + checkBox + "が入力されました。");
					//ViewからchkBoxの値を受け取る
					request.setAttribute("categoryId", checkBox[0]);
				}

				// カテゴリ更新のための画面変更if文
				if("update".equals(actionId)) {
					System.out.println("W0030C updateに来ました");
					String cid = request.getParameter("categoryId").toString();
					System.out.println("W0030C cid:" + cid);
					//中身未実装
					//ViewからカテゴリIDを受け取る
					String categoryName = request.getParameter("categoryName");
					System.out.println("W0030C cateName:" + categoryName + "が入力されました。");

					//ViewからuserIdを受け取る
					String userId = request.getParameter("userId");
					System.out.println("W0030C USERID:" + userId + "が入力されている。");

					//Viewから親カテゴリIDを受け取る
					String parentId = request.getParameter("pldw");
					System.out.println("W0030C ParentID:" + parentId + "が入力されている。");

					ret = W0030Model.updateCategory(categoryName, parentId, cid, userId);
				}

				//Viewの画面に戻す
				if(ret == -1){
				    request.setAttribute("insertFlag",1);
				    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0030View.jsp");
				    dispatch.forward(request, response);
				}else if(ret == 1){
				    //登録成功
				    request.setAttribute("insertFlag",1);
				    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/W0020Control");
				    dispatch.forward(request, response);
				}else{
				    //登録失敗
				    request.setAttribute("insertFlag",0);
				    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0030View.jsp");
				    dispatch.forward(request, response);
				}
			}catch(NullPointerException W0030nullException){
			  RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			  dispatch.forward(request, response);
	   }
	}
}