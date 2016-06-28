package co.wiss1.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.wiss1.model.W0040Model;

// WebServlet
@WebServlet("/W0040Control")
	public class W0040Control extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			//sessionから取得したUserNameをNull対応
			HttpSession session = request.getSession(true);
		try{
			String user = session.getAttribute("userName").toString();

			System.out.println("doPost start");
			// setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
			 request.setCharacterEncoding("UTF-8");

			// 処理に必要となる情報を受け取る
			// カテゴリIDの取得
				String categoryId = request.getParameter("Id");
				request.setAttribute("Id",categoryId);
				System.out.println("ControlカテゴリIDは"+categoryId +"です" );

				String categoryName = request.getParameter("Name");
				request.setAttribute("Name",categoryName);
				System.out.println("Controlカテゴリ名は"+categoryName +"です" );


				String comment = request.getParameter("text");
				System.out.println("Controlコメントは"+ comment +"です");

			// アクションIDの取得
				String actionId = request.getParameter("actionId");

			// 登録
			if ("insert".equals(actionId)) {
				System.out.println("insertに" + actionId + "が入力されました");

				String userId = request.getParameter("userId");
				System.out.println("ユーザーIDは"+userId +"ですbaba" );

				String userName = request.getParameter("userName");
				System.out.println("ユーザー名は"+ userName +"ですbaba" );


				int insertCount = W0040Model.insertComment(comment,categoryId,userId,userName);
				request.setAttribute("insertCount",insertCount);
			}
			// コメントの削除

			if("update".equals(actionId)) {
				String checkBox[ ] = request.getParameterValues("chkbox");
				System.out.println("checkBox[ ]に" + checkBox + "が入力されました。");
				//ViewからchkBoxの値を受け取る
				for (int i = 0; i < checkBox.length; i++ ) {
		            System.out.println(checkBox[i] + "<br>");
		        }

				int updateCount = W0040Model.updateComment(checkBox);
				System.out.println("updateに" + updateCount + "が入力されました。");
				request.setAttribute("updateCount",updateCount);
			}
			// 初期表示
			List<HashMap<String, String>> commentList = W0040Model.getCommentList(categoryId);
			if (commentList != null && 0 < commentList.size()) {
				request.setAttribute("commentList", commentList);
			}
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0040View.jsp");
		    dispatch.forward(request, response);
		}catch(NullPointerException W0030nullException){
			 RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			 dispatch.forward(request, response);
		}
	}
}