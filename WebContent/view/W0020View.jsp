<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />

		<li><% out.print(session.getAttribute("User")); %></li>
		<title>WISS1ch</title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">
		  function init(Command){
			  document.MyForm.actionId.value = Command;
			  document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
				  document.MyForm.submit();  //
		  }
		  function Regest(){
			  document.MyForm.action = "<%= request.getContextPath() %>/W0030View"
			  document.MyForm.submit();  //
		  }
		</script>
	</head>

	<body>
	<form name="MyForm" method="GET" action="<%= request.getContextPath() %>/W0030View">
    <a style="right:" href="<%= request.getContextPath() %></W0000Control/" >ログアウト</a>

		<div>
			<CENTER>
			<h1>WISS1ch</h1>
			<%-- テーブルの表示--%>
			<table>
				<thead>
					<tr>
						<th>選択</th>
						<th>カテゴリID</th>
						<th>カテゴリ名</th>
					</tr>
			</CENTER>
				</thead>
				    <%
						// カテゴリ一覧を取得
						List<HashMap<String,String>> categoryList = (List<HashMap<String,String>>)request.getAttribute("categoryList");
					%>
					<% String sessionflag = "あ";
					   String chk1 = null;
					   String chk2 = null;
		  			  String str1 = "i";
		    		  if(sessionflag.equals(str1) ){
		    		      String str2 = "button";
		    		      String str3 = "checkbox";
		    		      chk1 = str2;
		    		      chk2 = str3;
		    		  }else{
		    			  String str2 = "disabled";
		    			  String str3 = "true";
		    			  chk1 = str2;
		    		      chk2 = str3;
		    		     }	%>

					<% if (categoryList != null) { %>

						<% for (HashMap<String,String> categoryInfo : categoryList) { %>
						<tr>
							<th><input type="<%= chk2  %>" value="checkbox" name="chkbox" value="<%= categoryInfo.get("categoryId") %>"></th>
							<td><% out.print(categoryInfo.get("categoryId")); %></td>
							<td><a href="<%= request.getContextPath() %>/control/W0040Control.java" ><% out.print(categoryInfo.get("categoryId")); %></a><td>
						</tr>
						<% } %>
					<% } %>
			</table>

			<input type="button"  <%=chk1 %> value="カテゴリ登録" onClick="Regist();" >
			<input type="button"  <%= chk2  %> value="カテゴリ削除" onClick="init(update);">
			<input type="hidden" name="actionId"></div>
		</form>
	</body>
</html>
