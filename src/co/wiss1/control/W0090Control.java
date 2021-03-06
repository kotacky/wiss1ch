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

import co.wiss1.model.W0090Model;
import co.wiss1.common.Constants;

@WebServlet(Constants.W0090_CONTROL)
public class W0090Control extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        request.setCharacterEncoding(Constants.CHARACTER_ENCODING);
        //sessionから取得したuserNameをNull対応
        HttpSession session = request.getSession(true);
        try{
            String loginUser = session.getAttribute("userId").toString();
            String actionId = request.getParameter("actionId");

            request.setAttribute("updateFlg", "0");

            //送信ボタンを押したときの処理
            if ("send_message".equals(actionId)) {
                System.out.println("W0090C registar start");

                String messageTitle = request.getParameter("messageTitle");
                String message = request.getParameter("message");
                String recUserId = request.getParameter("recUserId");

                int registar = W0090Model.sendMessage(loginUser,messageTitle,message,recUserId);
                System.out.println("W0090C registar end" + registar);
                request.setAttribute("registar",registar);

            }

            // 初期表示
            List<HashMap<String, String>> userList = W0090Model.getUserList();
            //System.out.println(userList());
            // ユーザ一覧が空ではなく1件以上存在する場合、ユーザ一覧をセット
            if (userList != null && 0 < userList.size()) {
                request.setAttribute("userList", userList);
            }

            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0090_VIEW);
            dispatch.forward(request, response);


        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}