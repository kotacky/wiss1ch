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

    	//単独論理削除
		function soloupdate(cid){
			document.MyForm.inquiryId.value = cid;
			if(confirm("単独削除しますか？")){
				document.MyForm.actionId.value = 'soloupdate';
				document.MyForm.action = "<%= request.getContextPath() %>/W0070Control"
//				document.MyForm.submit();
			}else {
				alert("キャンセル");
			}
		}

		//一括論理削除
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
				  MyMessage = confirm("削除しますか？");
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
	    		<font size="6">問い合わせ一覧</font><br><br>
	    		</CENTER>

				<%-- 問い合わせの表示--%>




					    <%

							//問い合わせ情報取得
							List<HashMap<String,String>> inqList = (List<HashMap<String,String>>)request.getAttribute("inqList");

						%>
						<%
							String chk1 = null;
							String str1 = "t";
							String chk2 = "t";
						%>

						<% if (inqList != null) { %>
						<%
							 for (HashMap<String,String> inqInfo : inqList) {
							String inquiryId = inqInfo.get("inquiryId");
							String PostTime = inqInfo.get("PostTime");
							String userName = inqInfo.get("userName");
							String inquiry = inqInfo.get("inquiry");
							String OutputInquiry = inquiry.replaceAll("&","&amp;")
															.replaceAll("<","&lt;")
															.replaceAll(">","&gt;")
															.replaceAll("\"","&quot;")
															.replaceAll("\'","&#39;")
															.replaceAll("\n","<br>");
							String userId = inqInfo.get("userId");
							String userMail = inqInfo.get("userMail");
							//PostDateはミリ秒まで表示しているのでトリミング、ハイフンをスラッシュへ
							String OutputPostTime = PostTime.substring(0,16);
							OutputPostTime = OutputPostTime.replaceAll("-","/");
							if(userId.equals(userId)) {
								chk1 ="true";
							} else {
								chk1 ="disabled";
							}

							%>

								<div class="margin" align="center">
								<table>
									<tr bgcolor="#ffffff">
										<%-- 投稿時間を表示するように修正 --%>
										<th colspan=3>
											<div style="text-align : left">
												<input type="checkbox"  <%=chk1 %> name="chkbox" style="width:17px;height:17px;" value="<%= inquiryId %>"onClick="chk();">
												<span style="margin-right: 1em;"></span>
												問い合わせ時間: <% out.print(OutputPostTime); %>
												<span style="margin-right: 3em;"></span>
												ユーザ名 <a href="mailto:<% out.print(userMail); %>"><% out.print(userName); %></a> (<% out.print(userId); %>)
											</div>
											<div style = "text-align : left">
												<FONT size="4"><% out.print(OutputInquiry);%></FONT>
											</div>
											<div style = "text-align : right">　
												<input type="submit" <%=chk1 %> name="deleteBtn" value="削除" onClick="soloupdate(<%=inquiryId%>);">
											</div>
										</th>
										<br>
									</tr>
								</table>
             					</div>




	<% } %>
<% } %>


			<div id="footer">
<%-- 			<p id="copyright">Copyright (c) WISS1 Inc. All Rights Reserved.</p> --%>
	</div>

							</tr>
				</table>
				<P align="center">

				<input type="button"  <%=chk1 %> value="問い合わせ削除" onClick="deletes('Update');"><br>
				<input type="hidden" name="actionId" value="">
				<input type="hidden" name="inquiryId" value="">
				<input type="hidden" name="userName" value="">
				<input type="hidden" name="userAddress" value="">
				<input type="hidden" name="userMail" value="">

				</P>

			</form>
		</body>
</html>