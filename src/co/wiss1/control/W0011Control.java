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

import co.wiss1.model.W0011Model;
import co.wiss1.common.Constants;

// WebServlet
@WebServlet(Constants.W0011_CONTROL)
    public class W0011Control extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        //sessionから取得したUserNameをNull対応
        HttpSession session = request.getSession(true);

        try{
            // setCharacterEncodingはリクエスト本体にのみ摘要される(POST)
            request.setCharacterEncoding(Constants.CHARACTER_ENCODING);

            // アクションIDの取得
            String actionId = request.getParameter("actionId");
            String Id = session.getAttribute("userId").toString();

            System.out.println("W11C:通過");

            // 初期表示 と削除後の再検索したメッセージ一覧を取得
            List<HashMap<String, String>> messageList = W0011Model.getMessageList(Id);

            // 一覧が空ではなく1件以上存在する場合、メッセージ一覧をセット
            if (messageList != null && 0 < messageList.size()) {
            request.setAttribute("messageList", messageList);
        }
        RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0011_VIEW);
        dispatch.forward(request, response);
        System.out.println("doPost end");
        return;

        }catch(NullPointerException W0011nullException){
             RequestDispatcher dispatch =getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
             dispatch.forward(request, response);
        }
    }
}