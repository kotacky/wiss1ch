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

import co.wiss1.model.W0050Model;

// WebServlet
@WebServlet("/W0050Control")
public class W0050Control extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("W0050C doPost start");

        //sessionから取得したUserNameをNull対応
        HttpSession session = request.getSession(true);
        try {
            String admin = session.getAttribute("adminFlg").toString();

            // アクションIDの取得
            String actionId = request.getParameter("actionId");
            String checkBox[] = request.getParameterValues("chkbox"); //Viewのchkboxの値を取得
            String Id = session.getAttribute("userId").toString();

            // 削除
            if ("Update".equals(actionId)) {
                //ViewからchkBoxの値を受け取る
                for (int i = 0; i < checkBox.length; i++ ) {
                }

                //削除の項目を送る
                int delete = W0050Model.updateUser(checkBox);
                if (delete >= 1) {
                    System.out.println("削除成功");
                    request.setAttribute("delete",delete);
                } else {
                    System.out.println("削除失敗");
                    request.setAttribute("delete",delete);
                }
            }

            // 初期表示 と削除後の再検索したユーザ一覧を取得
            List<HashMap<String, String>> userList = W0050Model.getUserList(admin,Id);
            //System.out.println(userList());
            // ユーザ一覧が空ではなく1件以上存在する場合、ユーザ一覧をセット
            if (userList != null && 0 < userList.size()) {
                request.setAttribute("userList", userList);
            }
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0050View.jsp");
            dispatch.forward(request, response);
            System.out.println("doPost end");
            return;
        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
            dispatch.forward(request, response);
        }
    }
}
