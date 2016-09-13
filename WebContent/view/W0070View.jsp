<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%@ page import = "java.net.URLEncoder" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link href="<%= request.getContextPath() %>/view/css/W0070.css" rel="stylesheet" type="text/css" />
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
						document.MyForm.action = "<%= request.getContextPath() %>/W0070Control"
						document.MyForm.submit();
					  }
				}
				if(flg ==0){
					 alert("チェックボックスが未入力です。");
				  }
			    }
		 function move(Command){
			var values = Command.split(','); // , 区切;
			document.MyForm.inquiryId.value = values[0];
			document.MyForm.userName.value = values[1];
			document.MyForm.userAddress.value = values[2];
			document.MyForm.userMail.value = values[3];
			document.MyForm.actionId.value = 'move';
			document.MyForm.action = "<%= request.getContextPath() %>/W0060Control"
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
	    		<font size="8">問い合わせ一覧</font><br><br>

				<%-- 問い合わせの表示--%>


				</CENTER>

					    <%

							//問い合わせ情報取得
							List<HashMap<String,String>> inqList = (List<HashMap<String,String>>)request.getAttribute("inqList");

						%>
						<%
							String chk1 = null;
							String str1 = "t";
						%>

						<% if (inqList != null) { %>

							<% for (HashMap<String,String> inqInfo : inqList) { %>
							<%String inquiryId = inqInfo.get("inquiryId"); %>
							<%String PostTime = inqInfo.get("PostTime"); %>
							<%String userName = inqInfo.get("userName");  %>
							<%String inquiry = inqInfo.get("inquiry");  %>
							<%String userId = inqInfo.get("userId"); %>
							<%String userMail = inqInfo.get("userMail"); %>

							<tr>
								<td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= inqInfo.get("inquiryId") %>" onClick="chk();"></td>
								<td><a onClick="move('<%=inquiryId %>,<%=PostTime %>,<%= %>,<%=userName %>');"   href="#"  value=""  ><% out.print(inqInfo.get("inquiryId")); %></a></td>
								<td><% out.print(inqInfo.get("userName")); %></td>
								<td><% out.print(inqInfo.get("userMail")); %></td>

					</Tr>
	<% } %>
<% } %>
				</tbody>
			</table>
			<%if(sessionflag.equals(str1)){chk1 = "";}else{chk1 = "disabled";} %>
			<input type="hidden" name="process">
			<input type="hidden" name="employeeAuthority" value="<%= session.getAttribute("employeeAuthority") %>">

	</form>
			<div id="footer">
			<p id="copyright">Copyright (c) WISS1 Inc. All Rights Reserved.</p>
	</div>
</body>
</html>
							</tr>
				</table>
				<P>

				<input type="button"  <%=chk1 %> value="ユーザ削除" onClick="deletes('Update');"><br>
				<input type="hidden" name="actionId" value="">
				<input type="hidden" name="inquiryId" value="">
				<input type="hidden" name="userName" value="">
				<input type="hidden" name="userAddress" value="">
				<input type="hidden" name="userMail" value="">

				</P>

			</form>
		</body>
</html>