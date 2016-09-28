package co.wiss1.control;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.realm.RealmBase;

import co.wiss1.model.W0010Model;
import co.wiss1.common.Constants;

// WebServlet
@WebServlet(Constants.W0010_CONTROL)
public class W0010Control extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
          // フォーム入力を受け取る
          HttpSession session = request.getSession(true);
          String userId = request.getParameter("userId");
          String inputpassword = request.getParameter("password");

          // パスワードをハッシュ化
          String hashedpassword = RealmBase.Digest(inputpassword, "SHA-1", Constants.CHARACTER_ENCODING);


          // ユーザー情報を受け取る
          HashMap<String, String> UserInfo = W0010Model.getUserInfo(userId);

          // ユーザー名とパスワードと管理者フラグを取り出す
          String userName = UserInfo.get("userName");
          String password = UserInfo.get("password");
          String adminFlg = UserInfo.get("adminFlg");
          String fontColor = UserInfo.get("fontColor");
          String deleteFlg = UserInfo.get("deleteFlg");
          String chk = UserInfo.get("残念");

          if(deleteFlg == null){
              request.setAttribute("EMSG0001", "ユーザーが存在しません。");
              RequestDispatcher dispatch = request.getRequestDispatcher(Constants.W0010_VIEW);
              dispatch.forward(request, response);
          } else if(deleteFlg.equals("t")){ //削除されたユーザーは存在しないものとして弾く
              request.setAttribute("EMSG0001", "ユーザーが存在しません。");
              RequestDispatcher dispatch = request.getRequestDispatcher(Constants.W0010_VIEW);
              dispatch.forward(request, response);

          }else if (hashedpassword.equals(password)){
              session.setAttribute("userId",userId);
              session.setAttribute("userName",userName);
              session.setAttribute("adminFlg",adminFlg);
              session.setAttribute("fontColor", fontColor);
              RequestDispatcher dispatch = request.getRequestDispatcher(Constants.W0011_CONTROL);
              dispatch.forward(request, response);

          }else if(chk == null){
              request.setAttribute("EMSG0004", "パスワードが間違っています。");
              RequestDispatcher dispatch = request.getRequestDispatcher(Constants.W0010_VIEW);
              dispatch.forward(request, response);
          }else if(chk.equals("残念")){
              request.setAttribute("EMSG0001", "ユーザーが存在しません。");
              RequestDispatcher dispatch = request.getRequestDispatcher(Constants.W0010_VIEW);
              dispatch.forward(request, response);
          }

    }

}
