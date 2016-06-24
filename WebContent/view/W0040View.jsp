<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html lang=ja>
    <head>

        <link href="<%= request.getContextPath() %>/view/css/W0040.css" rel="stylesheet" type="text/css" />
        <meta charset="UTF-8">
        <title>WISS1ch</title>

        <script type="text/javascript">
            function logOut(){
                MyMessage = confirm("ログアウトします。よろしいですか？");
                if ( MyMessage == true ) {
                    document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
                    document.MyForm.submit();
                } else {
                    return;
                }
            }
            function os(command){
                document.MyForm.actionId.value = command;
                document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
                document.MyForm.submit();
            }
            function ok(command){
                document.MyForm.actionId.value = command;
                document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
                document.MyForm.submit();
            }
        </script>
    </head>
    <body>
        <form name="MyForm" method="POST" action="#">
            <div align="right">
            <% out.print(session.getAttribute("userName")); %>
    		<input style="margin-left:20px" type="button" class="button" name="logout" value="ログアウト" onClick="logOut();">
            </div>
            <CENTER><h1>WISS1ch</h1></CENTER>
			<% String sessionflag = session.getAttribute("adminFlg").toString();%>
			<% String userId = session.getAttribute("userId").toString();%>
            <% String chk1 = null ;%>
            <% String chk2 = "t";%>
            <%String Id; %>
            <% Id = request.getAttribute("Id").toString(); %>



            <%  List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList"); %>


			<% try { %>

            <CENTER>
            <%-- コメントの表示--%>
            <table style="background-color:#F0F8FF;"border="1">
			<h2><%out.print(commentList.get(0).get("CategoryName")); %>スレッド</h2>
            </CENTER>

                        <% if (commentList != null) { %>
                            <%
                                for (HashMap<String,String> commentInfo : commentList) {
                                    String comment = commentInfo.get("comment");
                                    String commentId = commentInfo.get("commentId");
                                    String commentUserId = commentInfo.get("userId");
                                    if(chk2.equals(sessionflag)){
                                        chk1 = "true";
                                    } else if(commentUserId.equals(userId)) {
                                       chk1 ="true";
                                    } else {
                                       chk1 ="disabled";
                                    }
                            %>

                        <tr>
                            <th colspan=2>投稿内容<br/><% out.print(comment); %></th>

                        </tr>
                        <tr>
                            <th><input type="checkbox"  <%=chk1 %> name="chkbox" value="<%= commentId %>"></th>
                            <td>ユーザID<% out.print(commentUserId); %></td>
                        </tr>
                            <% } %>
                        <% } %>
            </table>


            コメント入力欄<textarea input type="text  id="example" name="example"  rows="4" cols="40" maxlength="200" placeholder="コメント記入欄"></textarea>
            <input type="button" name="btn1" value ="投稿する" onClick="os('insert');">
            <input type="button" name="btn1" value ="削除する" onClick="ok('update');">
            <input type="hidden" name="actionId" value="">
            <input type="hidden" name="Id" value="<%= Id %>">
            <input type="hidden" name="userId" value="<%= userId %>">
			<%} catch(NullPointerException deleteException){ %>

				コメント入力欄<textarea input type="text  id="example" name="example"  rows="4" cols="40" maxlength="200" placeholder="コメント記入欄"></textarea>
				<input type="button" name="btn1" value ="投稿する" onClick="os('insert');">
            	<input type="button" name="btn1" value ="削除する" onClick="ok('update');">
            	<input type="hidden" name="actionId" value="">
            	<input type="hidden" name="Id" value="<%= Id %>">
            	<input type="hidden" name="userId" value="<%= userId %>">
			<% } %>

           	         </form>
        </body>
</html>