<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html lang=ja>
	<head>
		<% try{ %>
		<%
		// 投稿内容一覧を取得
			List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList");
		%>


		<%String Id; %>

		<% Id = commentList.get(0).get("Id"); %>
		<%System.out.println("★"+ Id); %>

		<div align="right" >
		<input type="submit" name="logOut" value="ログアウト" onclick="logOut();">
		</div>
		<CENTER><h1>WISS1ch</h1></CENTER>


		<meta charset="UTF-8">
		<title><h2><% out.print(request.getAttribute("categryName")); %></h2></title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">

		function func(command){
			  document.MyForm.actionId.value = command;

			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
			  document.MyForm.submit();
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
		<form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">


			<CENTER>
			<%-- コメントの表示--%>
			<table>
			</CENTER>


					<h2>
						<%out.print(commentList.get(0).get("CategoryName")); %>スレッド</h2>
						<% if (commentList != null) { %>

						<% for (HashMap<String,String> commentInfo : commentList) { %>
						<tr>
							<td colspan=2>投稿内容<br/><% out.print(commentInfo.get("comment")); %></td>
						</tr>
						<tr>
							<th><input type="checkbox" name="chkbox" value="<%= commentInfo.get("commentId") %>"></th>
							<td>投稿者:<% out.print(commentInfo.get("userName")); %></td>
						</tr>
						<% } %>
					<% } %>

			</table>




			コメント入力欄<input type="text"  id="example" name="example" >
			<input type="button" name="btn1" value ="投稿する"  onClick="os('insert');">
			<input type="button" name="btn1" value ="削除する"  onClick="ok('update');">
			<input type="hidden" name="actionId" value="">
			<input type="hidden" name="Id" value="<%= Id %>">
			<% }catch(NullPointerException deleteException){ %>
					<input type="button" value="一見おきてる!?">
				<% } %>
		 </form>
		</body>
</html>