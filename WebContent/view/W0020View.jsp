<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link href="<%= request.getContextPath() %>/view/css/W0020.css" rel="stylesheet" type="text/css" />
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
					document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
					document.MyForm.submit();
				  }
			   }
			 if(flg ==0){
				 alert("チェックボックスが未入力です。");
			  }
		     }

		  function Regist(){
			  document.MyForm.action = "<%= request.getContextPath() %>/view/W0030View.jsp"
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
   		   		<img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    			</h1>
    			<FORM name="pull">
				<SELECT name="pldw">
				<OPTION value="0" selected>大カテゴリを選択</OPTION>
				<OPTION value="1">16'新人</OPTION>
				<OPTION value="2">Java</OPTION>
				<OPTION value="3">野球</OPTION>
				<option value="4">麻雀</option>
				</SELECT>
				</FORM>

				<%-- テーブルの表示--%>
				<table border="1">

					<tr>
						<th>選択</th>
						<th>カテゴリ名</th>
					</tr>
			</CENTER>
					<!-- 変更点 -->



				   <!--  // カテゴリ一覧を取得-->

				    <script type="text/JavaScript">
					var event = document.getElementById("pldw");
					event.onchange = function(){ // セレクトボックスに変更が加えられたら
					var indx = document.pull.pldw.selectedIndex; // selectedImdexは「今」選択されているoptionを指す。返り値は数字。
					var categoryid = document.pull.pldw.options[indx].value; // optionのvalueを取得
					<%String Id1 = request.getParameter("categoryid");%>

					if(indx != 0 ){ // インデックスが0番でなかったら
					<%List<HashMap<String,String>> categoryList = (List<HashMap<String,String>>)request.getAttribute("categoryList");%>
					  }else{
						alert("選択して下さい");
							}
					}

					</script>

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

					<% if (categoryList != null) { %>

						<% for (HashMap<String,String> categoryInfo : categoryList) { %>
						<%String Id = categoryInfo.get("categoryId"); %>
						<%String Name = categoryInfo.get("categoryName"); %>
						<tr>
							<td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= categoryInfo.get("categoryId") %>" onClick="chk();"></td>
							<td><a onClick="move('<%=Id %>,<%=Name %>');"   href="#"  value=""  ><% out.print(categoryInfo.get("categoryName")); %></a></td>

							<% } %>
						<% } %>
						</tr>
			</table>
			<P>
			<input type="button"  <%=chk1 %> value="カテゴリ登録" onClick="Regist();" >
			<input type="button"  <%=chk1 %> value="カテゴリ削除" onClick="deletes('Update');">
			<input type="hidden" name="actionId" value="">
			<input type="hidden" name="Id" value="">
			<input type="hidden" name="Name" value="">
			</div>
			</P>
		</form>
	</body>
</html>
