package co.wiss1.control;

import java.io.InputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import co.wiss1.model.W0040Model;
import co.wiss1.model.W0050Model;
import co.wiss1.model.W0070Model;

// WebServlet
@WebServlet("/W0070Control")
@MultipartConfig(location="")
public class W0070Control extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			//sessionから取得したUserNameをNull対応
			HttpSession session = request.getSession(true);
		try{
			String user = session.getAttribute("userName").toString();

			System.out.println("W0070C doPost start");
			// setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
			request.setCharacterEncoding("UTF-8");

			// 処理に必要となる情報を受け取る
			// アクションIDの取得
			String actionId = request.getParameter("actionId");
			String checkBox[] = request.getParameterValues("chkbox"); //Viewのchkboxの値を取得
			String Id = session.getAttribute("userId").toString();
			System.out.println("W0070C 引数は"+Id );


		// 単独削除
		if("soloupdate".equals(actionId)) {
			System.out.println("*W0070C 単独論理削除します。");
			//削除対象のコメントID。Viewから受け取る
			String InquiryID = request.getParameter("inquiryId");
			String userID = session.getAttribute("userId").toString();
			System.out.println("*W0070C 削除を行うコメントIDは" + InquiryID + "です。");
			System.out.println("*W0070C 削除を行うユーザIDは" + userID + "です。");

		}


		// 一括削除
		if ("Update".equals(actionId)) {
			//ViewからchkBoxの値を受け取る
			for (int i = 0; i < checkBox.length; i++ ) {
				System.out.println(checkBox[i] + "<br>");
			}

			//削除の項目を送る
			int delete = W0070Model.updateInquiry(checkBox);
			if (delete >= 1) {
				System.out.println("削除成功");
				request.setAttribute("delete",delete);
			} else {
				System.out.println("削除失敗");
				request.setAttribute("delete",delete);
			}
		}

		// 初期表示
		List<HashMap<String, String>> inquiryList = W0070Model.getInquiryList(Id);
		if (inquiryList != null && 0 < inquiryList.size()) {
			request.setAttribute("inquiryList", inquiryList);
		}
		RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0070View.jsp");
	    dispatch.forward(request, response);
		System.out.println("doPost end");
		return;
	}catch(NullPointerException W0030nullException){
		 RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
		 dispatch.forward(request, response);
	}
}


}//コミットプッシュ用
