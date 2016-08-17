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

import co.wiss1.model.W0020Model;

// WebServlet
@WebServlet("/W0020Control")
public class W0020Control extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		System.out.println("doPost start");

		  //sessionから取得したUserNameをNull対応
		  HttpSession session = request.getSession(true);
		  try{
			  String user = session.getAttribute("userName").toString();


			  // アクションIDの取得
			  String actionId = request.getParameter("actionId");
			  String checkBox[] = request.getParameterValues("chkbox"); //Viewのchkboxの値を取得
			  String Id = request.getParameter("categoryId");
			  System.out.print("引数は"+Id );


			  // 削除

			  if ("Update".equals(actionId)){
				  //ViewからchkBoxの値を受け取る
				  for (int i = 0; i < checkBox.length; i++ ) {
				      System.out.println(checkBox[i] + "<br>");
				  }

				  //削除の項目を送る
				  int delete = W0020Model.updateCategory(checkBox);
				  if(delete >= 1){
				      System.out.println("削除成功");
				      request.setAttribute("delete",delete);
				  }else{
				      System.out.println("削除失敗");
				      request.setAttribute("delete",delete);
				  }
			  }

			  String Id1 = request.getParameter("pullval");
			  System.out.print(Id1);

			// 初期表示 と削除後の再検索したカテゴリ一覧を取得
			List<HashMap<String, String>> categoryList = W0020Model.getCategoryList();
			// カテゴリ一覧が空ではなく1件以上存在する場合、カテゴリ一覧をセット
			if (categoryList != null && 0 < categoryList.size()) {
				request.setAttribute("categoryList", categoryList);
			}
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0020View.jsp");
			dispatch.forward(request, response);
			System.out.println("doPost end");
		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}
