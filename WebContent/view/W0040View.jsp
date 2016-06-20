<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html lang=ja>
	<head>
		<meta charset="UTF-8">
		<title><h2><% out.print(request.getAttribute("categryName")); %></h2></title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">

		function func(insert){
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
				  document.MyForm.submit();
		 }

		function func(update){
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
				  document.MyForm.submit();
		 }
		</script>

	</head>
			<div align="right" >
			<form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">
			<input type="submit" name="logOut" value="ログアウト" onclick="logOut();"></form>
			</div>
	<body>
			<CENTER>
			<h1>WISS1ch</h1>
			<%-- コメントの表示--%>
			<table>
				<label>コメントID</label>
				<label>ユーザー名</label>
			</CENTER>

					<%
						// 投稿内容一覧を取得
							List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList");
					%>
					<% if (commentList != null) { %>
						<% for (HashMap<String,String> commentInfo : commentList) { %>
						<tr>
							<th><input type="checkbox" name="chkbox" value="<%= commentInfo.get("comment") %>"></th>
							<td><% out.print(commentInfo.get("commentId")); %></td>
							<td><% out.print(commentInfo.get("userName")); %></td>
						</tr>
						<% } %>
					<% } %>

			</table>
		</div>
			<CENTER>

			コメント入力欄<textarea name="example" rows="3"  maxlength='100' cols="30" value="<%= request.getParameter("comment") %>"></textarea>
			<input type="button" name="btn1" value ="投稿する"  onClick="func('insert');">
			<input type="submit" name="btn1" value ="削除する"  onClick="func('update');">
			<input type="hidden" name="actionId">
		</body>

</html>
