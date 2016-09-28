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
import co.wiss1.common.Constants;

@WebServlet(Constants.W0030_CONTROL)
public class W0030Control extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{

        //sessionから取得したuserNameをNull対応
        HttpSession session = request.getSession(true);
        try{
            //初期値
            int ret = -1;
            request.setAttribute("update_flag", "0");
            //新規登録で20から来た場合はcategoryIdは空
            //更新で来た場合は後ほど上書き
            request.setAttribute("categoryId", "");

            //Viewからフォーム入力の値を受け取る
            //UTF-8にエンコード
            request.setCharacterEncoding(Constants.CHARACTER_ENCODING);

            // アクションIDの取得
            String actionId = request.getParameter("actionId");

            //新規登録時に通る
            if("insert".equals(actionId)) {
                System.out.println("W0030C: Welcome INSERT.");
                //ViewからカテゴリIDを受け取る
                String categoryName = request.getParameter("categoryName");
                //ViewからuserIdを受け取る
                String userId = request.getParameter("userId");
                //Viewから親カテゴリIDを受け取る
                String parentId = request.getParameter("pldw");
                //Model重複チェックメソッドから重複カウントを受け取る
                int getOverlapCount = W0030Model.getOverlapCount(categoryName);
                //重複がなく、かつ親IDがnullってなければ
                if(getOverlapCount==0 && parentId!=null){
                    //Modelの関数で挿入する
                    ret = W0030Model.insertCategory(categoryName, parentId, userId);
                }else{
                    //getAttributeメソッドでrequestからViewが取得する
                    ret = 0;
                    request.setAttribute("getOverlapCount",getOverlapCount);
                }
            }

            // 更新で20から30に来た場合に通る
            if("update1st".equals(actionId)) {
                System.out.println("W0030C update1st");
                request.setAttribute("update_flag", "1");
                String checkBox[] = request.getParameterValues("chkbox");
                //ViewからchkBoxの値をintで受け取る
                request.setAttribute("categoryId", checkBox[0]);
            }

            // カテゴリ更新を実行したとき通る
            if("update".equals(actionId)) {
                System.out.println("W0030C update");
                String cid = request.getParameter("categoryId").toString();
                //中身未実装
                //ViewからカテゴリIDを受け取る
                String categoryName = request.getParameter("categoryName");
                //ViewからuserIdを受け取る
                String userId = request.getParameter("userId");
                //Viewから親カテゴリIDを受け取る
                String parentId = request.getParameter("pldw");

                ret = W0030Model.updateCategory(categoryName, parentId, cid, userId);
            }

            //Viewの画面に戻す
            if(ret == -1){
                //SQL操作なし(初回表示)
                request.setAttribute("insertFlag","1");
                RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0030_VIEW);
                dispatch.forward(request, response);
            }else if(ret == 1){
                //登録成功
                request.setAttribute("insertFlag","1");
                RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0020_CONTROL);
                dispatch.forward(request, response);
            }else{
                //登録失敗
                request.setAttribute("insertFlag","0");
                RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0030_VIEW);
                dispatch.forward(request, response);
            }
        } catch (NullPointerException W0030nullException) {
            //Null落ちしたら0010に戻る
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}