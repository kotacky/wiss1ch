<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html lang=ja>
	<head>
		<meta charset="UTF-8">
		<title><h1><% out.print(request.getAttribute("categryName")); %></h1></title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">
		function logOut(){
     	   MyMessage = confirm("ログアウトします。よろしいですか？");
     	     if ( MyMessage == true ){
				 document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
     	         document.MyForm.submit();
     	     }else{
     	    	return;
     	     }

		function func(Command){
			  document.MyForm.actionId.value = Command;
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
				  document.MyForm.submit();
		 }

		function init(Command){
			  document.MyForm.actionId.value = Command;
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
				  document.MyForm.submit();
		 }
		 }
		</script>

	</head>
		<div align="right" >
		<input type="button" name="btn1" value ="ログアウトする"  onClick="logOut();">
		</div>
	<body>

		<div>
			<CENTER>
			<h1>WISS1ch</h1>


			<%-- コメントの表示--%>
			<table>

					<tr>
						<th>選択</th>
						<th>コメントID</th>
						<th>ユーザー名</th>
					</tr>
			</CENTER>

					<%
						// 投稿内容一覧を取得
							List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList");
					%>
					<% if (commentList != null) { %>
						<% for (HashMap<String,String> commentInfo : commentList) { %>
						<tr>
							<th><input type="checkbox" name="chkbox" value="<%= commentInfo.get("commentId") %>"></th>
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
			</form>
		</body>

</html>