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

import co.wiss1.model.W0070Model;
import co.wiss1.common.Constants;

// WebServlet
@WebServlet(Constants.W0070_CONTROL)
public class W0070Control extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("W0070C doPost start");

        //sessionから取得したUserNameをNull対応
        HttpSession session = request.getSession(true);
        try {

            // アクションIDの取得
            String actionId = request.getParameter("actionId");
            String Id = session.getAttribute("userId").toString();

            // 単独削除
            if("soloupdate".equals(actionId)) {
                System.out.println("*W70C 単独論理削除します。");
                //削除対象のコメントID。Viewから受け取る
                String inqID = request.getParameter("inquiryId");
                String userID = session.getAttribute("userId").toString();

                //削除の項目を送る
                int delete = W0070Model.updateSoloInq(inqID,userID);
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
                }

                //削除の項目を送る
                int delete = W0070Model.updateInq(checkBox);
                if (delete >= 1) {
                    System.out.println("削除成功");
                    request.setAttribute("delete",delete);
                } else {
                    System.out.println("削除失敗");
                    request.setAttribute("delete",delete);
                }

            }

            // 初期表示 と削除後の再検索した問い合わせ一覧を取得
            List<HashMap<String, String>> inqList = W0070Model.getInqList();
            //System.out.println(inqList());
            // ユーザ一覧が空ではなく1件以上存在する場合、ユーザ一覧をセット
            if (inqList != null && 0 < inqList.size()) {
                request.setAttribute("inqList", inqList);
            }
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0070_VIEW);
            dispatch.forward(request, response);
            System.out.println("doPost end");
            return;
        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}
