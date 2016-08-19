package co.wiss1.control;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
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

// WebServlet
@WebServlet("/W0040Control")
@MultipartConfig(location="")
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
				String CommentID = request.getParameter("commentId");
				String userID = session.getAttribute("userId").toString();
				System.out.println("*W40C いいねするコメントIDは" + CommentID + "です。");
				System.out.println("*W40C いいねするユーザIDは" + userID + "です。");

				int goodCount = W0040Model.goodComment(CommentID, userID);
				System.out.println("*W40C goodに" + goodCount + "が入力されました。");
				request.setAttribute("goodCount",goodCount);
			}


			// コメント登録(旧版)
			if ("insert".equals(actionId)) {
				System.out.println("W40C insertします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				//文字色指定(sessionから取得)
				String ColorStr = session.getAttribute("font_color").toString();
				System.out.println("W40C ユーザーIDは"+userId +"です" );
				System.out.println("W40C ユーザー名は"+ userName +"です" );
				System.out.println("W40C 色番号は"+ ColorStr +"です" );

				int insertCount = W0040Model.insertComment(comment,categoryId,userId,userName,ColorStr);
				request.setAttribute("insertCount",insertCount);
			}

			// 新版コメント登録
			if ("newinsert".equals(actionId)) {
				System.out.println("W40C newinsertします。");

				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				String postName = request.getParameter("postName");
				System.out.println("W40C ユーザーIDは"+userId +"です" );
				System.out.println("W40C ユーザー名は"+ userName +"です" );
				System.out.println("W40C 投稿者名は"+ postName +"です" );

				//文字色指定(sessionから取得)
				String ColorStr = session.getAttribute("font_color").toString();
				System.out.println("W40C 色番号は"+ ColorStr +"です" );
				//投稿者名変更
				//Name:投稿される名前
				//blank:比較用空文字列
				//Name:Modelに渡す投稿者名
		        String blank = "";
				String Name;
				if(postName.equals(blank)){
					Name = userName;
				}else{
					Name = postName;
				}
				System.out.println("W40C 表示される投稿名は"+ Name +"です" );


		        // <INPUT type="file" name="imgfile"> で指定したものを取得
				final String files = "imgfile";
		        Part part = request.getPart(files);

		        byte[] data = new byte[(int) part.getSize()];   // byte配列を作成
		        InputStream in = null;
		        try {
		        	in = part.getInputStream(); // ストリームからbyte配列
		        	in.read(data, 0, data.length); 		// に、入力する
		        	System.out.println("W40C inputstream:data[" + data + "]");

		        	String result = new String(data, "UTF-8");
		        	System.out.println("W40C result: " + result);

		        	//16進数でデータを表示
		        	System.out.println("W40C HEXDATA:");
		        	int i;
		        	for(i=0; i<data.length; i++){
		        		System.out.print(Integer.toHexString(data[i]));
		        	}
		        	System.out.println("\nW40C HEXDATA:");

		        	//byte型の初期化。View側で得た画像ファイルを入れられれば
		        	//String byteInputStr = part.toString();		//なんか違う
		        	//String byteInputStr = postName;
		        	//byte[] ImgByte = byteInputStr.getBytes("UTF-8");

		        	//byte[] partstr = part.getContent();
		        	//SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMddHHmmss");
		        	//String upfilename = "upload" + datetime.toString();
		        	//System.out.println("W40C up-file-name[" + upfilename + "]");
		        	//part.write("upfilename");

		        	//String partstr = part.toString();
		        	//System.out.println("W40C strデータは[" + partstr + "]です");

		        } catch (IOException ex) {
		        	int insertCount = W0040Model.insertComment(comment, categoryId, userId, Name, ColorStr);
		        } finally{
		        	in.close();
		        }
		        int insertCount = W0040Model.insertCommentAddImg(comment,categoryId,userId,Name,data,ColorStr);
		        request.setAttribute("insertCount",insertCount);
			}

			// コメントの単独削除
			if("soloupdate".equals(actionId)) {
				System.out.println("*W40C 単独論理削除します。");
				//削除対象のコメントID。Viewから受け取る
				String CommentID = request.getParameter("commentId");
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