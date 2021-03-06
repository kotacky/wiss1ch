<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8" />
        <link href="<%= request.getContextPath() %>/view/css/W0011.css" rel="stylesheet" type="text/css" />
        <title>WISS1ch</title>

        <script type="text/javascript">
        function logOut(){
            if ( confirm("ログアウトします。よろしいですか？") ){
                document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
                document.MyForm.submit();
            }else{
                return;
            }
        }

        function go_category(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
            document.MyForm.submit();
        }

        function go_userinfo(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0050Control"
            document.MyForm.submit();
        }

        function go_inquiry(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0070Control"
            document.MyForm.submit();
        }

        function go_messagelist(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0100Control"
            document.MyForm.submit();
        }


        </script>
    </head>

    <body>

        <form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">
            <%
                //メッセージ情報取得
                List<HashMap<String,String>> messageList = (List<HashMap<String,String>>)request.getAttribute("messageList");

            %>
            <% 	String sessionflag = session.getAttribute("adminFlg").toString();
                String sessionuser = session.getAttribute("userId").toString();
            %>
            <%
                String str1 = "t";
            %>

            <div align="right">
            <%
                if (messageList != null) { %>
                <b><font color="#ff0000">NEW !</font>
            <% } %>
                <a href="javascript:go_messagelist()">メッセージ</a></b>
                <% out.print(session.getAttribute("userName")); %>
                <a style="margin-left:20px"class="button" name="logout" onClick="logOut();">
                <img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
            </div>

            <div>
                <CENTER>
                <h1>
                    <!-- Wiss1chロゴ画像表示 -->
                    <img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
                </h1>
                <font color=#0074bf size=5>WISS1ちゃんねる ポータルメニュー</font>
                </CENTER>
            </div>
            <br><br>
            <CENTER>
                <!-- テーブルで遷移ボタンの表示-->
                <table class="transition">
                    <tr>
                        <td class="userinfo"><input type="button" name=ui_btn value="ユーザ情報" onClick=go_userinfo();></td>
                        <td>        </td>
                        <td class="category"><input type="button" name=ct_btn value="カテゴリ一覧" onClick=go_category();></td>
                    </tr>
                </table>
                <%if(sessionflag.equals(str1)) { %>
                <input type="button" name=inq_btn value="問い合わせ一覧" onClick="go_inquiry();">
                <% } %>
            </CENTER>
        </form>
    </body>
</html>