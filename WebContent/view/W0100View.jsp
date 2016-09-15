<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%@ page import = "java.net.URLEncoder" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link href="<%= request.getContextPath() %>/view/css/W0050.css" rel="stylesheet" type="text/css" />
		<title>WISS1ch</title>

		<script type="text/javascript" >
		function logOut(){
			MyMessage = confirm("ログアウトします。よろしいですか？");
			if ( MyMessage == true ){
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
				  MyMessage = confirm("削除しますか");
			  		if ( MyMessage == true ){
			  			document.MyForm.actionId.value = Command;
						document.MyForm.action = "<%= request.getContextPath() %>/W0100Control"
						document.MyForm.submit();
					  }
				}
				if(flg ==0){
					 alert("チェックボックスが未入力です。");
				  }
			    }
		 function move(Command){
			var values = Command.split(','); // , 区切;
			document.MyForm.userId.value = values[0];
			document.MyForm.userName.value = values[1];
			document.MyForm.userAddress.value = values[2];
			document.MyForm.userMail.value = values[3];
			document.MyForm.actionId.value = 'move';
			document.MyForm.action = "<%= request.getContextPath() %>/W0100Control"
			document.MyForm.submit();
		}



		</script>
	</head>


	<body>
		<form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">

				<div align="right">
				<% out.print(session.getAttribute("userName")); %>
	    		<a style="margin-left:20px"class="button" name="logout"onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
	    		</div>
	    		<input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick="javascript:history.back();">
				<CENTER>
				<h1>
	   		    <a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
	   		    </h1>
	    		<font size="5">メッセージ一覧</font><br><br>

				<%-- テーブルの表示--%>
				<table border = "1">

				</CENTER>

					    <%

							//ユーザ情報取得
							List<HashMap<String,String>> messageList = (List<HashMap<String,String>>)request.getAttribute("messageList");

						%>
						<% String sessionflag = session.getAttribute("adminFlg").toString();
						   String sessionuser = session.getAttribute("userId").toString();

						System.out.println("userIdは" + sessionuser + "です！！！★"); %>
						<%
							String chk1 = null;
							String str1 = "t";
						%>

						<% if (messageList != null) { %>

							<% for (HashMap<String,String> messageInfo : messageList) { %>
							<%String messageTitle = messageInfo.get("messageTitle"); %>
							<%String message = messageInfo.get("message"); %>
							<%String sendUserName = messageInfo.get("sendUserName");  %>
							<%String recUserName = messageInfo.get("recUserName"); %>
							<%String postTime = messageInfo.get("postTime");  %>
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
								<td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= messageInfo.get("userId") %>" onClick="chk();"></td>
		<!--  						<td><a onClick="move('<%=messageTitle %>,<%=message %>,<%=postTime %>,<%=sendUserName %><%=recUserName %>');"   href="#"  value=""  ><% out.print(messageInfo.get("userId")); %></a></td>
								<td><% out.print(messageInfo.get("userName")); %></td>
		-->						<td><%=messageTitle %></td>
								<td><%=OutputPostTime %></td>
								<td><%=sendUserName %></td>
								<td><%=recUserName %></td>

					</Tr>
	<% } %>
<% } %>
				</tbody>
			</table>


			<div id="footer">
			<p id="copyright">Copyright (c) WISS1 Inc. All Rights Reserved.</p>
	</div>

							</tr>
				</table>
				<P>
				<input type="button"  <%=chk1 %> value="削除" onClick="deletes('Update');"><br>
				<input type="button" value="メッセージ送信フォーム" onClick="go_messageform();">
				<input type="hidden" name="actionId" value="">
				<input type="hidden" name="userId" value="">
				<input type="hidden" name="userName" value="">
				<input type="hidden" name="userAddress" value="">
				<input type="hidden" name="userMail" value="">

				</P>

			</form>
		</body>
</html>