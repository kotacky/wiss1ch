package co.wiss1.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.realm.RealmBase;

import co.wiss1.common.Constants;
import co.wiss1.model.W0060Model;

@WebServlet(Constants.W0060_CONTROL)
public class W0060Control extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        request.setCharacterEncoding(Constants.CHARACTER_ENCODING);
        //sessionから取得したuserNameをNull対応
        HttpSession session = request.getSession(true);
        try{
            String loginUser = session.getAttribute("userId").toString();
            String actionId = request.getParameter("actionId");


            //住所自動入力
            if("autoinput".equals(actionId)) {
            	String updateFlg =request.getParameter("updateFlg");

            	System.out.println("W0060C autoinputします。");
            	System.out.println(updateFlg);

            	String userId  = request.getParameter("userId");
                String userName = request.getParameter("userName");
            	String postalCode = request.getParameter("postalCode").toString();
            	String addressInfo = W0060Model.getAddressInfo(postalCode);
            	String userMail = request.getParameter("userMail");
            	System.out.println(addressInfo);


            	request.setAttribute("userId", userId);
            	request.setAttribute("userName", userName);
                request.setAttribute("postalCode", postalCode);
            	request.setAttribute("userAddress",addressInfo);
            	request.setAttribute("userMail", userMail);
            	request.setAttribute("updateFlg", updateFlg);
            }

            //ユーザ一覧から更新で遷移してきた場合
            if("move".equals(actionId)) {
            	request.setAttribute("updateFlg", "1");

                String userId = request.getParameter("userId");
                String userName = request.getParameter("userName");
                String postalCode = request.getParameter("postalCode");
                String userAddress = request.getParameter("userAddress");
                String userMail = request.getParameter("userMail");
                System.out.println("moveからやってきた！！");

                request.setAttribute("userId", userId);
                request.setAttribute("userName", userName);
                request.setAttribute("postalCode",postalCode);
                request.setAttribute("userAddress", userAddress);
                request.setAttribute("userMail", userMail);
            }

            //ユーザ情報更新画面で更新ボタンを押したとき
            if ("update".equals(actionId)) {
                System.out.println("W0060C updateします。");

                String userId = request.getParameter("hiddenUserid");
                String userName = request.getParameter("userName");
                String postalCode = request.getParameter("postalCode");
                String userAddress = request.getParameter("userAddress");
                String userMail = request.getParameter("userMail");
                String passWord = request.getParameter("passWord");
                String conPassword = request.getParameter("conPassword");
                String fontColor = request.getParameter("fontColor");

                // パスワードをハッシュ化
                String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
                int registar = W0060Model.updateUser(loginUser,userId,userName,postalCode,userAddress,userMail,hashedpassword,conPassword, fontColor);
                //自分の情報を更新していた場合
                if(loginUser.equals(userId)){
                    session.setAttribute("font_color", fontColor);
                }
                request.setAttribute("registar",registar);
                RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0050_CONTROL);
                dispatch.forward(request, response);
                return;
            }

            //ユーザ一覧からユーザ登録ボタンを押したとき
            if ("userRegist".equals(actionId)){
                System.out.println("W0060C 新規登録画面にいきます。");
                request.setAttribute("updateFlg", "0");

                request.setAttribute("userId", "");
                request.setAttribute("userName", "");
                request.setAttribute("postalCode", "");
                request.setAttribute("userAddress", "");
                request.setAttribute("userMail", "");
            }

            //ユーザ新規登録画面で登録ボタンを押したとき
            if ("Registration".equals(actionId)) {
                System.out.println("W0060C registarします。");

                String userId = request.getParameter("userId");
                String userName = request.getParameter("userName");
                String postalCode = request.getParameter("postalCode");
                String userAddress = request.getParameter("userAddress");
                String userMail = request.getParameter("userMail");
                String passWord = request.getParameter("passWord");
                String conPassword = request.getParameter("conPassword");
                String fontColor = request.getParameter("fontColor");

                // パスワードをハッシュ化
                String hashedpassword = RealmBase.Digest(passWord, "SHA-1", "Windows-31J");
                int registar = W0060Model.registarUser(loginUser,userId,userName,postalCode,userAddress,userMail,hashedpassword,conPassword,fontColor);
                System.out.println("W0060C registar 終了" + registar);
                request.setAttribute("registar",registar);

                if(registar == 0){
                    System.out.println("W0060C registar0に入りました" + registar);
                    RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0060_VIEW);
                    dispatch.forward(request, response);
                    return;
                }else{
                    RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0050_CONTROL);
                    dispatch.forward(request, response);
                    return;
                }
            }


            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0060_VIEW);
            System.out.println(dispatch);
            dispatch.forward(request, response);


        } catch (NullPointerException W0030nullException) {
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher(Constants.W0010_VIEW);
            dispatch.forward(request, response);
        }
    }
}