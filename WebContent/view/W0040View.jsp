<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html lang=ja>
    <head>

        <link href="<%= request.getContextPath() %>/view/css/W0040.css" rel="stylesheet" type="text/css" />
        <meta charset="UTF-8">

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
            function insert(command){
                document.MyForm.actionId.value = command;
                document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
                document.MyForm.submit();
            }
            function update(Command){
    			// チェックボックス要素をすべて取得する
    			var boxes = document.getElementsByName("chkbox");
    			// チェックボックスの個数を取得する
    		    var cnt = boxes.length;

    			var flg = 0;  //flg:チェックの判定用
    			for(var i=0; i < cnt; i++) {
    		    	if(boxes.item(i).checked) {
    		    	    flg = 1;
    		       }
    		    }
    			if(flg > 0){
    			  MyMessage = confirm("削除しますか");
    		  		if ( MyMessage == true ){
    		  			document.MyForm.actionId.value = Command;
    					document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
    					document.MyForm.submit();
    				  }
    			   }
    			 if(flg ==0){
    				 alert("チェックボックスが未入力です。");
    			  }
    		     }

        </script>
    </head>
    <body>
        <form name="MyForm" method="POST" action="#">
            <div align="right">
            <% out.print(session.getAttribute("userName")); %>
    		<input style="margin-left:20px" type="button" class="button" name="logout" value="ログアウト" onClick="logOut();">
            </div>

			<% String sessionflag = session.getAttribute("adminFlg").toString();%>
			<% String userId = session.getAttribute("userId").toString();%>
            <% String chk1 = null ;%>
            <% String chk2 = "t";%>
            <%String Id; %>
            <% Id = request.getAttribute("Id").toString(); %>



            <%  List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList"); %>


			<% try { %>

            <CENTER>
            <h1>
   		    	<img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    		</h1>
            <%-- コメントの表示--%>
			<table style="">
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
				<div class="margin">
				<table>
                        <tr>
                            <th colspan=2>投稿内容<br/><% out.print(comment); %></th>

                        </tr>
                        <tr><br/>
                            <th><input type="checkbox"  <%=chk1 %> name="chkbox" value="<%= commentId %>"onClick="chk();"></th>
                            <td>ユーザID  <% out.print(commentUserId); %></td>
                        </tr>
                            <% } %>
                        <% } %>
            	</table>
             	</div>
			<P class="margin">
            コメント入力欄<textarea class="margin" input type="text"  id="example" name="example"  rows="4" cols="40" maxlength="200"  placeholder="コメント記入欄"></textarea>
            <input class="margin" type="button" name="btn1" value ="投稿する" onClick="insert('insert');">
            <input class="margin" type="button" name="btn1" value ="削除する" onClick="update('update');">
            <input class="margin" type="hidden" name="actionId" value="">
            <input class="margin" type="hidden" name="Id" value="<%= Id %>">
            <input class="margin" type="hidden" name="userId" value="<%= userId %>">
            </P>
			<%} catch(NullPointerException deleteException){ %>
				コメント入力欄<textarea class="margin" input type="text"  id="example" name="example"  rows="4" cols="40"  maxlength="200"  placeholder="コメント記入欄"></textarea>
				<input class="margin" type="button" name="btn1" value ="投稿する" onClick="insert('insert');">
            	<input class="margin" type="button" name="btn1" value ="削除する" onClick="update('update');">
            	<input class="margin" type="hidden" name="actionId" value="">
            	<input class="margin" type="hidden" name="Id" value="<%= Id %>">
            	<input class="margin" type="hidden" name="userId" value="<%= userId %>">
			<% } %>

           	         </form>
        </body>
</html>