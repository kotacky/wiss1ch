<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link rel="stylesheet" type="text/css" href="./css/W0010.css">
		<div style="text-align: right;">
		<li ><% out.print(session.getAttribute("userName")); %></li>

		<title>WISS1ch</title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">
		  function ob(Comand){
		   alert("ためし");
		  }
		  function Regist(){
			  document.MyForm.action = "<%= request.getContextPath() %>/view/W0030View.jsp"
			  document.MyForm.submit();
		  }
		  function go(){
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
			  document.MyForm.submit();
		  }
		</script>
	</head>

	<body>
	<form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">
	<input type="submit" value="ログアウト" ></div>

		<div>
			<CENTER>
			<h1>WISS1ch</h1>
			<%-- テーブルの表示--%>
			<table border="1">

					<tr>
						<th>選択</th>
						<th>カテゴリID</th>
						<th>カテゴリ名</th>
					</tr>
			</CENTER>

				    <%
						// カテゴリ一覧を取得
						List<HashMap<String,String>> categoryList = (List<HashMap<String,String>>)request.getAttribute("categoryList");
					%>
					<% String sessionflag = session.getAttribute("adminFlg").toString();
					System.out.print("権限は" + sessionflag + "です！！！");
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
						<tr>
							<td><input type="checkbox" <%= chk1  %> value="" name="chkbox" value="<%= categoryInfo.get("categoryId") %>"></td>
							<td><% out.print(categoryInfo.get("categoryId")); %></td>
							<td><a onClick="go();" name="<%categoryInfo.get("categoryName"); %>>"  href="#" ><% out.print(categoryInfo.get("categoryName")); %></a></td>

							<% } %>
						<% } %>
						</tr>
			</table>
			<P>
			<input type="button"  <%=chk1 %> value="カテゴリ登録" onClick="Regist();" >
			<input type="button"  <%= chk1  %> value="カテゴリ削除" onClick="ob();">
			<input type="hidden" name="actionId" value=""></div>
			</P>
		</form>
	</body>
</html>
