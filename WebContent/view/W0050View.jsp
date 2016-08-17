<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link href="<%= request.getContextPath() %>/view/css/W0050.css" rel="stylesheet" type="text/css" />
		<title>WISS1ch</title>

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
		 function deletes(Command){
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
						document.MyForm.action = "<%= request.getContextPath() %>/W0050Control"
						document.MyForm.submit();
					  }
				   }
				 if(flg ==0){
					 alert("チェックボックスが未入力です。");
				  }
			     }

			  function Regist(){
				  document.MyForm.action = "<%= request.getContextPath() %>/view/W0050View.jsp"
				  document.MyForm.submit();
			  }
			  function move(Command){

				  var values = Command.split(','); // , 区切;
				  document.MyForm.Id.value = values[0];
				  document.MyForm.Name.value = values[1];
				  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
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

			<div>
				<CENTER>
				<h1>
	   		    <a href ="javascript:window.open('https://www.google.co.jp/maps/');"><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
	    		</h1>
	    		<font size="8">ユーザリスト</font><br><br>

				<%-- テーブルの表示--%>
				<table border = "1">

						<tr>
							<th></th>
							<th>ユーザID</th>
							<th>ユーザ名</th>
							<th>住所</th>
							<th>権限</th>
						</tr>


				</CENTER>

					    <%

							//ユーザ情報取得
							List<HashMap<String,String>> userList = (List<HashMap<String,String>>)request.getAttribute("userList");

						%>
						<% String sessionflag = session.getAttribute("adminFlg").toString();

						System.out.println("★権限は" + sessionflag + "です！！！★");

						   String chk1 = null;
						   String chk2 = null;
			  			  String str1 = "t";
			    		  if(sessionflag.equals(str1) ){
			    		      String str2 = "true";
			    		      chk1 = str2;
			    		  }else{
			    			  String str2 = "disabled";
			    			  chk1 = str2;

			    		     }	%>

						<% if (userList != null) { %>

							<% for (HashMap<String,String> userInfo : userList) { %>
							<%String Id = userInfo.get("userId"); %>
							<%String Name = userInfo.get("userName"); %>
							<tr>
								<td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= userInfo.get("userId") %>" onClick="chk();"></td>
								<td><a onClick="move('<%=Id %>,<%=Name %>');"   href="#"  value=""  ><% out.print(userInfo.get("userId")); %></a></td>
								<td><% out.print(userInfo.get("userName")); %></td>
								<td><% out.print(userInfo.get("userAddress")); %></td>
								<td><% out.print(userInfo.get("userAdmin")); %></td>

								<% } %>
							<% } %>
							</tr>
				</table>
				<P>
				<input type="button"  <%=chk1 %> value="ユーザ登録" onClick="Regist();" >
				<input type="button"  <%=chk1 %> value="ユーザ削除" onClick="deletes('Update');">
				<input type="hidden" name="actionId" value="">
				<input type="hidden" name="Id" value="">
				<input type="hidden" name="Name" value="">
				</div>
				<div id="map" style="width: 330px; height: 100px;"></div>
				</P>
			</form>
		</body>
</html>