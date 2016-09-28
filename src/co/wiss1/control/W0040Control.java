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

// WebServlet
@WebServlet("/W0040Control")
@MultipartConfig(location="")
    public class W0040Control extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
            //sessionから取得したUserNameをNull対応
            HttpSession session = request.getSession(true);
        try{
            System.out.println("W40C doPost start");
            // setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
            request.setCharacterEncoding("UTF-8");

            // 処理に必要となる情報を受け取る
            // カテゴリIDの取得
            String categoryId = request.getParameter("categoryId");
            request.setAttribute("categoryId",categoryId);

            String categoryName = request.getParameter("categoryName");
            request.setAttribute("categoryName",categoryName);

            String comment = request.getParameter("text");

            // アクションIDの取得
            String actionId = request.getParameter("actionId");


            // いいね！
            if("good".equals(actionId)) {
                System.out.println("*W40C GOODします。");
                //いいね対象のコメントID。Viewから受け取る
                String commentId = request.getParameter("commentId");
                String userId = session.getAttribute("userId").toString();

                //いいね関数
                int goodCount = W0040Model.goodComment(commentId, userId);
                request.setAttribute("goodCount",goodCount);
            }

            // コメント登録(旧版)
            if ("insert".equals(actionId)) {
                System.out.println("W40C insertします。");

                String userId = request.getParameter("userId");
                String userName = request.getParameter("userName");
                //文字色指定(sessionから取得)
                String colorStr = session.getAttribute("fontColor").toString();

                int insertCount = W0040Model.insertComment(comment,categoryId,userId,userName,colorStr);
                request.setAttribute("insertCount",insertCount);
            }

            // 新版コメント登録
            if ("newinsert".equals(actionId)) {
                System.out.println("W40C newinsertします。");

                String userId = request.getParameter("userId");
                String userName = request.getParameter("userName");
                String postName = request.getParameter("postName");

                //文字色指定(sessionから取得)
                String colorStr = (String) session.getAttribute("fontColor");

                //投稿者名変更
                //writtenName:投稿される名前
                //blank:比較用空文字列
                //Name:Modelに渡す投稿者名
                String blank = "";
                String writtenName;
                if(postName.equals(blank)){
                    writtenName = userName;
                }else{
                    writtenName = postName;
                }

                // <INPUT type="file" name="imgfile"> で指定したものを取得
                final String files = "imgfile";
                Part part = request.getPart(files);

                byte[] data = new byte[(int) part.getSize()];   // byte配列を作成
                InputStream in = null;
                int insertCount = 0;
                try {
                    in = part.getInputStream(); // ストリームからbyte配列
                    in.read(data, 0, data.length); 		// に、入力する
                    System.out.println("W40C inputstream:data[" + data + "]");

                    //16進数でデータを表示
                    System.out.println("W40C HEXDATA:");
                    int i;
                    for(i=0; i<data.length; i++){
                        System.out.print(Integer.toHexString(data[i]));
                    }
                    System.out.println("\nW40C HEXDATA:");

                } catch (IOException ex) {
                } finally{
                }
                insertCount = W0040Model.insertCommentAddImg(comment, categoryId, userId, writtenName, data, colorStr);
                request.setAttribute("insertCount",insertCount);
                in.close();
            }

            // コメントの単独削除
            if("soloupdate".equals(actionId)) {
                System.out.println("*W40C 単独論理削除します。");
                //削除対象のコメントID。Viewから受け取る
                String commentId = request.getParameter("commentId");
                String userId = session.getAttribute("userId").toString();

                int goodCount = W0040Model.updateSoloComment(commentId, userId);
                request.setAttribute("goodCount",goodCount);
            }


            // コメントの削除
            if("update".equals(actionId)) {
                String checkBox[ ] = request.getParameterValues("chkbox");
                //ViewからchkBoxの値を受け取る
                for (int i = 0; i < checkBox.length; i++ ) {
                }

                int updateCount = W0040Model.updateComment(checkBox);
                request.setAttribute("updateCount",updateCount);
            }

            // 初期表示
            List<HashMap<String, String>> commentList = W0040Model.getCommentList(categoryId);
            HashMap<String, String> imgInfo = W0040Model.getImgList(categoryId);
            if (commentList != null && 0 < commentList.size()) {
                request.setAttribute("commentList", commentList);
                request.setAttribute("imgInfo", imgInfo);
            }
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0040View.jsp");
            dispatch.forward(request, response);
        } catch(NullPointerException W0030nullException) {
             RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
             dispatch.forward(request, response);
        }
    }
}