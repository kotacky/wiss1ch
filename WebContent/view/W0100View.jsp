<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%@ page import = "java.net.URLEncoder" %>

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8" />
        <link href="<%= request.getContextPath() %>/view/css/W0100.css" rel="stylesheet" type="text/css" />
        <link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" rel="stylesheet" />
        <title>WISS1ch</title>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
        <script type="text/javascript">

            function logOut(){
                if (confirm("ログアウトします。よろしいですか？") ){
                    document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
                    document.MyForm.submit();
                }else{
                    return;
                }
             }

            function go_portal(){
                document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
                document.MyForm.submit();
            }

            function go_messageform(){
                document.MyForm.action = "<%= request.getContextPath() %>/W0090Control"
                document.MyForm.submit();
            }

            function deletes(Command){
                // チェックボックス要素をすべて取得する
                var boxes = document.getElementsByName("chkbox");
                // チェックボックスの個数を取得する
                var cnt = boxes.length;
                //flg:チェックの判定用
                var flg = 0;

                for(var i=0; i < cnt; i++) {
                    if(boxes.item(i).checked) {
                        flg = 1;
                    }
                }
                if(flg > 0){
                    if (confirm("削除しますか")){
                        document.MyForm.actionId.value = Command;
                        document.MyForm.action = "<%= request.getContextPath() %>/W0100Control"
                        document.MyForm.submit();
                    }
                }
                if(flg == 0){
                    alert("チェックボックスが未入力です。");
                }
            }

            //メッセージのポップアップ表示
            $(function(){
                var cellIndex;
                $('td').click(function(){
                    cellIndex = this.cellIndex;
                });

                $("#messageTable tr").click(function() {
                    if (cellIndex != 2) {
                        return;
                    }
                    var messageTitle = $(this).find("td").eq(2).text();
                    var messageId = $(this).find("td").eq(0).text();
                    var message = document.MyForm["message" + messageId].value;
                    var sendUserName = document.MyForm["sendUserName" + messageId].value;
                    var recUserName = document.MyForm["recUserName" + messageId].value;
                    var time = document.MyForm["time" + messageId].value;
                    var msg =
                            "To " + recUserName +
                            "<br/><br/>" + message +
                            "<br/><br/>" + time + " From " + sendUserName;
                    $('.content').html(msg);

                    $('.dialog').dialog({
                        width: 500,
                        hight: 500,
                        autoOpen: false,
                        title: messageTitle,
                        buttons: {
                            "OK": function() {
                                $(this).dialog("close");
                            }
                        }
                    });

                    $('.dialog').dialog('open');
                });
            });

        </script>
    </head>
    <body>
        <form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">

            <div align="right">
                <% out.print(session.getAttribute("userName")); %>
                <a style="margin-left:20px"class="button" name="logout" onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
            </div>
            <input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick="javascript:history.back();">
            <CENTER>
                <h1>
                    <a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
                </h1>
                <font size="5">メッセージ一覧</font><br><br>

                <%-- テーブルの表示--%>
                <table class="messagelist" id="messageTable">

            </CENTER>

                <%

                //メッセージ情報取得
                List<HashMap<String,String>> messageList = (List<HashMap<String,String>>)request.getAttribute("messageList");

                %>
                <% String sessionflag = session.getAttribute("adminFlg").toString();
                   String sessionuser = session.getAttribute("userId").toString();
                %>
                <%
                   String chk1 = null;
                   String str1 = "t";
                %>

                <% if (messageList != null) { %>

                    <% for (HashMap<String,String> messageInfo : messageList) { %>
                        <% String messageId = messageInfo.get("messageId"); %>
                        <% String messageTitle = messageInfo.get("messageTitle"); %>
                        <% String message = messageInfo.get("message"); %>
                        <% String sendUserName = messageInfo.get("sendUserName");  %>
                        <% String recUserName = messageInfo.get("recUserName"); %>
                        <% String sendUserId = messageInfo.get("sendUserId");  %>
                        <% String recUserId = messageInfo.get("recUserId"); %>
                        <% String postTime = messageInfo.get("postTime");  %>
                    <%
                           String OutputMessage = message.replaceAll("&","&amp;")
                                                         .replaceAll("<","&lt;")
                                                         .replaceAll(">","&gt;")
                                                         .replaceAll("\"","&quot;")
                                                         .replaceAll("\'","&#39;")
                                                         .replaceAll("\n","<br>");
                    //PostDateはミリ秒まで表示しているのでトリミング、ハイフンをスラッシュへ
                           String OutputPostTime = postTime.substring(0,16);
                           OutputPostTime = OutputPostTime.replaceAll("-","/");

                    %>

                    <tr>
                        <td style="display:none"><%=messageId %></td>
                        <td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= messageInfo.get("messageId") %>" onClick="chk();"></td>
                        <td class="messageTitle"><a href="javascript:value(0)" id="open<%=messageId %>" class="openDialog" ><b><%=messageTitle %></b></a></td>
                        <td><%=OutputPostTime %></td>
                        <td class="userName"><%=sendUserName %></td>
                        <td>⇒</td>
                        <td class="userName"><%=recUserName %></td>
                        <td>
                            <input type="hidden" name="messageTitle<%=messageId %>" value="<%=messageTitle%>">
                            <input type="hidden" name="message<%=messageId %>" value="<%=OutputMessage %>">
                            <input type="hidden" name="sendUserName<%=messageId %>" value="<%=sendUserName %>">
                            <input type="hidden" name="recUserName<%=messageId %>" value="<%=recUserName %>">
                            <input type="hidden" name="time<%=messageId %>" value="<%=OutputPostTime %>">
                        </td>
                    </tr>
                    <% } %>
                <% } %>

                </table>

            <div class="dialog">
                <p class="content"></p>
            </div>

            <P>
                <input type="button" value="メッセージ送信フォーム" onClick="go_messageform();"><br>
                <input type="button"  <%=chk1 %> value="削除" onClick="deletes('Update');">
                <input type="hidden" name="actionId" value="">
                <input type="hidden" name="userId" value="">
                <input type="hidden" name="userName" value="">
            </P>

        </form>
    </body>
</html>