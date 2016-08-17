package co.wiss1.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

			System.out.println("W40C doPost start");
			// setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
			request.setCharacterEncoding("UTF-8");

			// 処理に必要となる情報を受け取る
			// カテゴリIDの取得
			String categoryId = request.getParameter("Id");
			request.setAttribute("Id",categoryId);
			System.out.println("W40C カテゴリIDは"+categoryId +"です" );

			String categoryName = request.getParameter("Name");
			request.setAttribute("Name",categoryName);
			System.out.println("W40C カテゴリ名は"+categoryName +"です" );


			String comment = request.getParameter("text");
			System.out.println("W40C コメントは"+ comment +"です");

			// アクションIDの取得
			String actionId = request.getParameter("actionId");


			// いいね！
			if("good".equals(actionId)) {
				System.out.println("*W40C GOODします。");
				//いいね対象のコメントID。Viewから受け取る
				String CommentID = "25";
				//String CommentID = request.getParameter("good");				//NULLする
				String userID = session.getAttribute("userId").toString();
				System.out.println("*W40C いいねするコメントIDは" + CommentID + "です。");
				System.out.println("*W40C いいねするユーザIDは" + userID + "です。");

				int goodCount = W0040Model.goodComment(CommentID, userID);
				System.out.println("*W40C goodに" + goodCount + "が入力されました。");
				request.setAttribute("goodCount",goodCount);
			}


			// 既存登録
			if ("insert".equals(actionId)) {
				System.out.println("W40C insertします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				System.out.println("W40C ユーザーIDは"+userId +"です" );
				System.out.println("W40C ユーザー名は"+ userName +"です" );

				int insertCount = W0040Model.insertComment(comment,categoryId,userId,userName);
				request.setAttribute("insertCount",insertCount);
			}


			// 新版コメント登録
			if ("newinsert".equals(actionId)) {
				System.out.println("W40C newinsertします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				//byte型の初期化。今後View側で得た画像ファイルをここでInputStreamとかする
				byte[] Img = new byte[1000];
				//今は仮に1000Byte分の0Fillでテストする
				Arrays.fill(Img, (byte)0);

				//文字色指定。今はsessionに保存していないので仮に「１」とする
				String ColorStr = "1";
				//String ColorStr = session.getAttribute("font_color").toString();
				int Color = Integer.parseInt(ColorStr);
				System.out.println("W40C ユーザーIDは"+userId +"です" );
				System.out.println("W40C ユーザー名は"+ userName +"です" );
				System.out.println("W40C 色番号は"+ Color +"です" );

				int insertCount = W0040Model.insertCommentAddImg(comment,categoryId,userId,userName,Img,Color);

				request.setAttribute("insertCount",insertCount);
			}

			// コメントの単独削除
			if("soloupdate".equals(actionId)) {
				System.out.println("*W40C 単独論理削除します。");
				//削除対象のコメントID。Viewから受け取る
				String CommentID = "24";
				//String CommentID = request.getParameter("good");				//NULLする
				String userID = session.getAttribute("userId").toString();
				System.out.println("*W40C 削除を行うコメントIDは" + CommentID + "です。");
				System.out.println("*W40C 削除を行うユーザIDは" + userID + "です。");

				int goodCount = W0040Model.updateSoloComment(CommentID, userID);
				System.out.println("*W40C goodに" + goodCount + "が入力されました。");
				request.setAttribute("goodCount",goodCount);
			}


			// コメントの削除
			if("update".equals(actionId)) {
				String checkBox[ ] = request.getParameterValues("chkbox");
				System.out.println("W40C checkBox[ ]に" + checkBox + "が入力されました。");
				//ViewからchkBoxの値を受け取る
				for (int i = 0; i < checkBox.length; i++ ) {
					System.out.println(checkBox[i] + "<br>");
		        }

				int updateCount = W0040Model.updateComment(checkBox);
				System.out.println("W40C updateに" + updateCount + "が入力されました。");
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