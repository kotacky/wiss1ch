<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8" />
		<link href="<%= request.getContextPath() %>/view/css/W0020.css" rel="stylesheet" type="text/css" />
		<!-- 「rel="～" href="…"」「このHTMLのstylesheetはcss/W0010.cssです」  -->
		<div style="text-align: right;">
		<!-- -align: right;」「 右揃え」  -->
		<li ><% out.print(session.getAttribute("userName")); %></li>
		<!-- <li> …… リストの項目を記述する」「sessionとは？必要な情報をクライアントではなくサーバ側に保存」  -->
		<title>WISS1ch</title>
		<style>
			h1{color: blue; }
		</style>
		<script type="text/javascript">
		  function init(Comand){
			  document.MyForm.actionId.value = Comand;
			  document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
				/*-「getContextPath()とは？環境に依存しないURL」*/
			  document.MyForm.submit();
				//「submit()とは？HTMLのformタグの内容をサーバに送信する動作のこと」

		  }
		  function Regist(){
			  document.MyForm.action = "<%= request.getContextPath() %>/view/W0030View.jsp"
			  document.MyForm.submit();
		  }
		  function go(tekitou){
			  document.MyForm.Id.value = tekitou;
			  document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
			  document.MyForm.submit();
		  }					<!--
		</script>
	</head>

	<body>
	<form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">
	<!--  //「postとは？入力データをCGIに引き渡す方法（METHOD）として、「GET」と「POST」があります」
	//「最大有効文字数という制限がない POST ２５５文字まででデータをサーバに送る手段 GET METHOD」-->
	<input type="submit" value="ログアウト" ></div>

		<div>
			<CENTER>
			<h1>WISS1ch</h1>
			<%-- テーブルの表示--%>
			<table border="1">

					<tr>
						<th>選択</th>
						<th>カテゴリ名</th>
					</tr>
			</CENTER>

				    <%
						// カテゴリ一覧を取得
						List<HashMap<String,String>> categoryList = (List<HashMap<String,String>>)request.getAttribute("categoryList");
						//「HashMapとは？キーとなる文字列と要素がペアになっている連想配列」

					%>
					<% String sessionflag = session.getAttribute("adminFlg").toString();
						//「toString()とは？　toStringメソッドは対象のオブジェクトを文字列に変換した結果を返します。」

					System.out.println("★権限は" + sessionflag + "です！！！★");
						//("権限は" + sessionflag + "です！！！")とは？

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
						<tr>
							<td><input type="checkbox" <%= chk1  %> name="chkbox" value="<%= categoryInfo.get("categoryId") %>" onClick="chk();"></td>
							<td><a onClick="go('<%=Id %>');"   href="#"  value=""  ><% out.print(categoryInfo.get("categoryName")); %></a></td>

							<% } %>
						<% } %>
						</tr>
			</table>
			<P>
			<input type="button"  <%=chk1 %> value="カテゴリ登録" onClick="Regist();" >
			<input type="button"  <%= chk1  %> value="カテゴリ削除" onClick="init('Update');">
			<input type="hidden" name="actionId" value="">
			<input type="hidden" name="Id" value="">
			</div>
			</P>
		</form>
	</body>
</html>
