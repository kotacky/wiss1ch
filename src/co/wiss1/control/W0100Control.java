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

import co.wiss1.model.W0040Model;
import co.wiss1.model.W0100Model;
import co.wiss1.model.W0100Model;

// WebServlet
@WebServlet("/W0100Control")
public class W0100Control extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("W0100C doPost start");

		//sessionから取得したUserNameをNull対応
		HttpSession session = request.getSession(true);
		try {

			// アクションIDの取得
			String actionId = request.getParameter("actionId");
			String Id = session.getAttribute("userId").toString();
			System.out.println("W0100C 引数は"+Id );
			System.out.println("actionId は "+actionId);

			// 単独削除
			if("soloupdate".equals(actionId)) {
				System.out.println("*W0100C 単独論理削除します。");
				//削除対象のコメントID。Viewから受け取る
				String inqID = request.getParameter("messageId");
				String userID = session.getAttribute("userId").toString();
				System.out.println("*W0100C 削除を行うコメントIDは" + inqID + "です。");
				System.out.println("*W0100C 削除を行うユーザIDは" + userID + "です。");

				//削除の項目を送る
				int delete = W0100Model.updateSoloMessage(inqID,userID);
				if (delete >= 1) {
					System.out.println("削除成功");
					request.setAttribute("delete",delete);
				} else {
					System.out.println("削除失敗");
					request.setAttribute("delete",delete);
				}

			}


			String checkBox[] = request.getParameterValues("chkbox"); //Viewのchkboxの値を取得
			// 一括削除
			if ("Update".equals(actionId)) {
				//ViewからchkBoxの値を受け取る
				for (int i = 0; i < checkBox.length; i++ ) {
					System.out.println(checkBox[i] + "<br>");
				}

				//削除の項目を送る
				int delete = W0100Model.updateMessage(checkBox);
				if (delete >= 1) {
					System.out.println("削除成功");
					request.setAttribute("delete",delete);
				} else {
					System.out.println("削除失敗");
					request.setAttribute("delete",delete);
				}

			}

			// 初期表示 と削除後の再検索したメッセージ一覧を取得
			List<HashMap<String, String>> messageList = W0100Model.getMessageList(Id);
			//System.out.println(messageList());
			// メッセージ一覧が空ではなく1件以上存在する場合、メッセージ一覧をセット
			if (messageList != null && 0 < messageList.size()) {
				request.setAttribute("messageList", messageList);
			}
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0100View.jsp");
			dispatch.forward(request, response);
			System.out.println("doPost end");
			return;
		}catch(NullPointerException W0030nullException){
			RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			dispatch.forward(request, response);
		}
	}
}
