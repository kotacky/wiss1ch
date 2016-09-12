<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.xml.bind.DatatypeConverter" %>
<!DOCTYPE html>
<html lang=ja>
    <head>

        <link href="<%= request.getContextPath() %>/view/css/W0070.css" rel="stylesheet" type="text/css" />
        <meta charset="UTF-8">
        <title>WISS1ch</title>

        <script type="text/javascript">
	    	function ShowLength( idn, str ) {
       			document.getElementById(idn).innerHTML = str.length + "文字";
    		}

	    	//ログアウト
	    	function logOut(){
				MyMessage = confirm("ログアウトします。よろしいですか？");
				if ( MyMessage == true ){
					document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
					document.MyForm.submit();
				}else{
					return;
				}
			}

	    	//ポータル帰還
			function go_portal(){
				document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
				document.MyForm.submit();
			}

	    	//単独論理削除
			function soloupdate(cid){
				document.MyForm.inquiryId.value = cid;
				var myRet = confirm("単独削除しますか？");
				if(myRet == true){
					document.MyForm.actionId.value = 'soloupdate';
					document.MyForm.action = "<%= request.getContextPath() %>/W0070Control"
					document.MyForm.submit();
				}else if(myRet == false){
					alert("キャンセル");
				}
			}

	    	//論理削除
			function update(Command){
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
        <form name="MyForm" enctype="multipart/form-data" method="POST" action="#">
			<input type="hidden" name="inquiryId">
            <div align="right">
            <% out.print(session.getAttribute("userName")); %>
    		<a style="margin-left:20px"class="button" name="logout"onClick="logOut();">
    		<img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
            </div>
            <input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick=go_portal();>
			<% String sessionflag = session.getAttribute("adminFlg").toString();%>
			<% String userId = session.getAttribute("userId").toString();%>
			<% String userName = session.getAttribute("userName").toString();%>
            <% String chk1 = null ;%>
            <% String chk2 = "t";%>
            <% String Id; %>
            <% Id = request.getAttribute("Id").toString(); %>


            <%  List<HashMap<String,String>> inquiryList = (List<HashMap<String,String>>)request.getAttribute("inquiryList"); %>


				<CENTER>
            		<h1>
   		    			<a href="#"  onClick="go_portal();"><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
    				</h1>
            		<%-- 問い合わせの表示--%>
					<table style="">
					<h2>問い合わせ一覧</h2>
            	</CENTER>
                    <%	if (inquiryList != null) { %>
					<%
							int j, k = inquiryList.size();
							for (j=0; j<k; j++) {
								HashMap<String,String> inquiryInfo = inquiryList.get(j);
								String inquiry = inquiryInfo.get("inquiry");
								String OutputInquiry = inquiry.replaceAll("\n","<br>")
																.replaceAll("&","&amp;")
																.replaceAll("<","&lt;")
																.replaceAll(">","&gt;")
																.replaceAll("\"","&quot;")
																.replaceAll("\'","&#39;");

								String inquiryId = inquiryInfo.get("inquiryId");
								String inquiryUserId = inquiryInfo.get("userId");
								String UserName = inquiryInfo.get("userName");
								String userMail = inquiryInfo.get("userMail");
							//Create_Dateを取得してPostDateへ
								String PostTime = inquiryInfo.get("PostTime");
							//PostDateはミリ秒まで表示しているのでトリミング、ハイフンをスラッシュへ
								String OutputPostTime = PostTime.substring(0,16);
								OutputPostTime = OutputPostTime.replaceAll("-","/");

					%>

								<div class="margin">
								<table>
									<tr>
										<%-- 投稿時間を表示するように修正 --%>
										<th colspan=3>
											<div style="text-align : left">
												<input type="checkbox"  <%=chk1 %> name="chkbox" style="width:17px;height:17px;" value="<%= inquiryId %>"onClick="chk();">
												<span style="margin-right: 1em;"></span>
												問い合わせ日時: <% out.print(OutputPostTime); %>
												<span style="margin-right: 3em;"></span>
												ユーザ名  <% out.print(UserName); %>
											</div>
											<div style = "text-align : left">
												<FONT size="4" ><% out.print(OutputInquiry);%></FONT>
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

           	         </form>
           	       			<div id="footer">
							<p id="copyright">Copyright (c) WISS1 Inc. All Rights Reserved.</p>
							</div>
        </body>
</html>
<%-- コミットプッシュ用 --%>