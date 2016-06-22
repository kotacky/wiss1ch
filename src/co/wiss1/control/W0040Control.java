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

import co.wiss1.model.W0040Model;

// WebServlet
@WebServlet("/W0040Control")
	public class W0040Control extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			System.out.println("doPost start");
			// setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
			 request.setCharacterEncoding("UTF-8");

			// 処理に必要となる情報を受け取る
			// カテゴリIDの取得
				String Id = request.getParameter("Id");
				System.out.println("引数は"+Id +"です。" );
				String categoryid = request.getParameter("categoryId");
				String Coment = request.getParameter("example");
				System.out.println("引数は"+ Coment +"です～～～");
			// アクションIDの取得
				String actionId = request.getParameter("actionId");

			// 登録
			if ("insert".equals(actionId)) {


				W0040Model.insertComment("comment");
				RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0040View.jsp");
				dispatch.forward(request, response);
			}


			// コメントの削除
			if("update".equals(actionId)) {
				String checkBox[ ] = request.getParameterValues("chkbox");
				W0040Model.updatecomment(checkBox);
			}
			// 初期表示
			List<HashMap<String, String>> commentList = W0040Model.getCommentList(Id);
			if (commentList != null && 0 < commentList.size()) {
				request.setAttribute("commentList", commentList);
				RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0040View.jsp");
			    dispatch.forward(request, response);
			}else{
				RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0040View.jsp");
			    dispatch.forward(request, response);
			}


	}
}
