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
     	       if ( MyMessage == true ){
     	    	 document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
     	         document.MyForm.submit();
     	       }else{
   				 return;
   			   }
        }

            function insert(command){
				var l = document.MyForm.text.value.length;
				alert(l);
				if(l <= 0){
					alert("未記入です！！！");
				}
			    if (l<=200 && 10 < l){
            	  myRet = confirm("投稿しますか？");
        		if(myRet == true ){
        		document.MyForm.actionId.value = command;
            	document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
            	document.MyForm.submit();
        		}else if(myRet == false){
                	alert("キャンセルされました");
            		}
        		}else if (l > 200){
					alert("200文字以内で入力してください");
        		}

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
    		<a style="margin-left:20px"class="button" name="logout"onClick="logOut();">
    		<img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (1).gif"></a>
            </div>
            <% String categoryName = request.getAttribute("Name").toString();%>
			<% String sessionflag = session.getAttribute("adminFlg").toString();%>
			<% String userId = session.getAttribute("userId").toString();%>
			<% String userName = session.getAttribute("userName").toString();%>
            <% String chk1 = null ;%>
            <% String chk2 = "t";%>
            <% String Id; %>
            <% Id = request.getAttribute("Id").toString(); %>



            <%  List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList"); %>

				<% try { %>


				<CENTER>
            	<h1>
   		    	<img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    			</h1>
            	<%-- コメントの表示--%>
				<table style="">
				<h2><%out.print(request.getAttribute("Name"));%>スレッド</h2>
            	</CENTER>
                        <% if (commentList != null) { %>
                            <%
                                for (HashMap<String,String> commentInfo : commentList) {
                                    String comment = commentInfo.get("comment");
                                    String commentId = commentInfo.get("commentId");
                                    String commentUserId = commentInfo.get("userId");
                                    String UserName = commentInfo.get("userName");
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
                           <th colspan=2><span style="margin-right: 13em;"></span>投稿内容<span style="margin-right: 8em;"></span>ユーザ名  <% out.print(UserName); %>
                            <div style="text-align : left">
                            <input type="checkbox"  <%=chk1 %> name="chkbox" style="width:17px;height:17px;" value="<%= commentId %>"onClick="chk();">
                            <FONT size="4"><% out.print(comment); %></FONT>
                            </div>


                            </th>
                            <br/>
                        </tr>
                            <% } %>
                        <% } %>
            	</table>
             	</div>
			<P class="margin">入力文字数は ５０文字以上 ２００文字まで。<br>
            コメント入力欄<textarea class="margin" input type="text"  id="text" name="text"  rows="4" cols="40"  wrap="hard"  placeholder="コメント記入欄"></textarea>

            <input class="margin" type="submit" name="btn1" id="toukou" value ="投稿する" onClick="insert('insert');">
            <input class="margin" type="button" name="btn1" value ="削除する" onClick="update('update');">
            <input class="margin" type="hidden" name="actionId" value="">
            <input class="margin" type="hidden" name="Id" value="<%= Id %>">
            <input class="margin" type="hidden" name="userId" value="<%= userId %>">
            <input class="margin" type="hidden" name="userName" value="<%= userName %>">
            <input class="margin" type="hidden" name="Name" value="<%= categoryName %>">
            </P>
			<%} catch(NullPointerException deleteException){ %>
			コメント入力欄<textarea class="margin" input type="text"  id="text" name="text"  rows="4" cols="40"  wrap="hard" placeholder ="コメント記入欄"></textarea>
			<input class="margin" type="submit" name="btn1" value ="投稿する" onClick="insert('insert');">
            <input class="margin" type="button" name="btn1" value ="削除する" onClick="update('update');">
            <input class="margin" type="hidden" name="actionId" value="">
            <input class="margin" type="hidden" name="Id" value="<%= Id %>">
            <input class="margin" type="hidden" name="userName" value="<%= userName %>">
            <input class="margin" type="hidden" name="Name" value="<%= categoryName %>">
			<% } %>

           	         </form>
        </body>
</html>