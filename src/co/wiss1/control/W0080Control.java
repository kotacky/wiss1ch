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

import co.wiss1.model.W0080Model;
import co.wiss1.common.Constants;

@WebServlet(Constants.W0080_CONTROL)
public class W0080Control extends HttpServlet{

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
            if ("insert_inq".equals(actionId)) {
                System.out.println("W0080C registarします。");

                String inquiry = request.getParameter("inquiry");

                int registar = W0080Model.sendInquiry(loginUser,inquiry);
                System.out.println("W0080C registar 終了" + registar);
                request.setAttribute("registar",registar);


            }


            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0080_VIEW);
            dispatch.forward(request, response);


        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}