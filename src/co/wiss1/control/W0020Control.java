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
import co.wiss1.common.Constants;

// WebServlet
@WebServlet(Constants.W0020_CONTROL)
public class W0020Control extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("doPost start");

        try{
            // アクションIDの取得
            String actionId = request.getParameter("actionId");
            String checkBox[] = request.getParameterValues("chkbox"); //Viewのchkboxの値を取得
            String categoryId = request.getParameter("categoryId");

            // 削除
            if ("Update".equals(actionId)){
                //ViewからchkBoxの値を受け取る
                for (int i = 0; i < checkBox.length; i++ ) {
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

            // 初期表示 と削除後の再検索したカテゴリ一覧を取得
            String parent = request.getParameter("pldw");

            List<HashMap<String, String>> categoryList = null;
            if(parent != null){
                categoryList = W0020Model.getCategoryList(parent);
            }
            // カテゴリ一覧が空ではなく1件以上存在する場合、カテゴリ一覧をセット
            if (categoryList != null && 0 < categoryList.size()) {
                request.setAttribute("categoryList", categoryList);
            }
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0020_VIEW);
            dispatch.forward(request, response);
            System.out.println("doPost end");
        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}