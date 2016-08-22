<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.awt.image.BufferedImage" %>
<%@ page import = "java.awt.Graphics2D" %>
<%@ page import = "java.io.ByteArrayInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "javax.imageio.ImageIO" %>
<%@ page import = "javax.swing.ImageIcon" %>
<%@ page import = "javax.xml.bind.DatatypeConverter" %>
<!DOCTYPE html>
<html lang=ja>
    <head>

        <link href="<%= request.getContextPath() %>/view/css/W0040.css" rel="stylesheet" type="text/css" />
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

	    	//投稿(挿入) 新版
			function newinsert(command){
				var l = document.MyForm.text.value.length;
				//alert(l);
				if(l <= 3){
					alert("文字数が不足しています");
				}else if (l<=200){
					myRet = confirm("投稿しますか？");
					if(myRet == true ){
						document.MyForm.actionId.value = command;
						document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
						document.MyForm.submit();
					}else if(myRet == false){
						alert("キャンセルされました");
					}
				}else if (l > 200){
					alert("200文字以内で入力してください");
				}
			}

	    	//投稿(挿入) 旧版
			function insert(command){
				var l = document.MyForm.text.value.length;
				//alert(l);
				if(l <= 3){
					alert("文字数が不足しています");
				}else if (l<=200){
					myRet = confirm("投稿しますか？");
					if(myRet == true ){
						document.MyForm.actionId.value = command;
						document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
						document.MyForm.submit();
					}else if(myRet == false){
						alert("キャンセルされました");
					}
				}else if (l > 200){
					alert("200文字以内で入力してください");
				}
			}

	    	//いいね
			function good(cid){
				document.MyForm.actionId.value = 'good';
				document.MyForm.commentId.value = cid;
	    		alert("いいねします ID:" + cid);
				document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
				document.MyForm.submit();
			}

	    	//単独論理削除
			function soloupdate(cid){
				document.MyForm.actionId.value = 'soloupdate';
				document.MyForm.commentId.value = cid;
	    		MyRet = confirm("単独削除します ID:" + cid);
				if(myRet == true ){
					document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
					document.MyForm.submit();
				}else{
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
						document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
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
			<input type="hidden" name="commentId">
            <div align="right">
            <% out.print(session.getAttribute("userName")); %>
    		<a style="margin-left:20px"class="button" name="logout"onClick="logOut();">
    		<img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
            </div>
            <% String categoryName = request.getAttribute("Name").toString();%>
			<% String sessionflag = session.getAttribute("adminFlg").toString();%>
			<% String userId = session.getAttribute("userId").toString();%>
			<% String userName = session.getAttribute("userName").toString();%>
			<% String user_font_color = session.getAttribute("font_color").toString();%>
            <% String chk1 = null ;%>
            <% String chk2 = "t";%>
            <% String Id; %>
            <% Id = request.getAttribute("Id").toString(); %>
    		<% out.print(user_font_color); %>


            <%  List<HashMap<String,String>> commentList = (List<HashMap<String,String>>)request.getAttribute("commentList"); %>
            <%  List<HashMap<String,BufferedImage>> ImgList = (List<HashMap<String,BufferedImage>>)request.getAttribute("ImgList"); %>

				<% try { %>
				<CENTER>
            		<h1>
   		    			<a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
    				</h1>
            		<%-- コメントの表示--%>
					<table style="">
					<h2><%out.print(request.getAttribute("Name"));%>スレッド</h2>
            	</CENTER>
                    <%	if (commentList != null) { %>
					<%
							int j, k = commentList.size();
							for (j=0; j<k; j++) {
								HashMap<String,String> commentInfo = commentList.get(j);
								String comment = commentInfo.get("comment");
								String OutputComment = comment.replaceAll("\n","<br>");
								String commentId = commentInfo.get("commentId");
								String commentUserId = commentInfo.get("userId");
								String UserName = commentInfo.get("userName");
								String good_count = commentInfo.get("good_count");
								String post_font_color = commentInfo.get("font_color");
							//Create_Dateを取得してPostDateへ
								String PostTime = commentInfo.get("PostTime");
							//PostDateはミリ秒まで表示しているのでトリミング、ハイフンをスラッシュへ
								String OutputPostTime = PostTime.substring(0,16);
								OutputPostTime = OutputPostTime.replaceAll("-","/");
								if(chk2.equals(sessionflag)){
									chk1 = "true";
								} else if(commentUserId.equals(userId)) {
									chk1 ="true";
								} else {
									chk1 ="disabled";
								}

								HashMap<String,BufferedImage> ImgInfo = ImgList.get(j);
								BufferedImage Imgbyte = ImgInfo.get("img_bin");
								boolean result = false;
								//Graphics2D g = Imgbyte.createGraphics();
								//g.drawImage(Imgbyte, null, 10, 10);
					%>

								<div class="margin">
								<table>
									<tr>
										<%-- 投稿時間を表示するように修正 --%>
										<th colspan=3>
											<div style="text-align : left">
												<input type="checkbox"  <%=chk1 %> name="chkbox" style="width:17px;height:17px;" value="<%= commentId %>"onClick="chk();">
												<span style="margin-right: 1em;"></span>
												投稿時間: <% out.print(OutputPostTime); %>
												<span style="margin-right: 3em;"></span>
												ユーザ名  <% out.print(UserName); %>
											</div>
											<div style = "text-align : left">
												<FONT size="4" color="<%out.print(post_font_color);%>"><% out.print(OutputComment);%></FONT>
											</div>
											<div style = "text-align : right">
												<input type="submit" name="commentBtn" value="ｲｲﾈ!" onClick="good(<%=commentId%>);">
												<% out.print(good_count); %>　　
												<input type="submit" name="deleteBtn" value="削除" onClick="soloupdate(<%=commentId%>);">
											</div>
										</th>
										<br>
									</tr>
								</table>
             					</div>
                            <% } %>
                        <% } %>
			<P class="margin">投稿可能文字数は 4文字以上、200文字までです。
			<div>
				投稿者名<input type="text" id="postName" name="postName" size="20"></input>
			</div>
			<div>
	            コメント入力欄<textarea class="margin" input type="text" onkeyup="ShowLength( 'nummoji' , value );"  id="text" name="text"  rows="4" cols="40"  placeholder="コメント記入欄"></textarea>
				<span id="nummoji">0文字</span>
			</div>
			<input class="margin" type="file" name="imgfile" accept='image/png' size="50" id=imgfile" value="画像選択">　　　　　　
            <input class="margin" type="submit" name="btn1" id="toukou" value ="投稿する" onClick="newinsert('newinsert');">
            <input class="margin" type="button" name="btn1" value ="削除する" <% if (commentList == null) { out.print("disabled"); }%> onClick="update('update');">
            <input class="margin" type="hidden" name="actionId" value="">
            <input class="margin" type="hidden" name="Id" value="<%= Id %>">
            <input class="margin" type="hidden" name="userId" value="<%= userId %>">
            <input class="margin" type="hidden" name="userName" value="<%= userName %>">
            <input class="margin" type="hidden" name="Name" value="<%= categoryName %>">
            </P>
			<%} catch(NullPointerException deleteException){ %>>
			<div>
	            コメント入力欄<textarea class="margin" input type="text" onkeyup="ShowLength( 'nummoji' , value );"  id="text" name="text"  rows="4" cols="40"  placeholder="コメント記入欄"></textarea>
				<span id="nummoji">0文字</span>
			</div>
			<input class="margin" type="file" name="imgfile" accept='image/png' size="50" id=imgfile" value="画像選択">
			<input class="margin" type="submit" name="btn1" value ="投稿する" onClick="newinsert('newinsert');">
            <input class="margin" type="button" name="btn1" value ="削除する" <% if (commentList == null) { out.print("disabled"); }%> onClick="update('update');">
            <input class="margin" type="hidden" name="actionId" value="">
            <input class="margin" type="hidden" name="Id" value="<%= Id %>">
            <input class="margin" type="hidden" name="userId" value="<%= userId %>">
            <input class="margin" type="hidden" name="userName" value="<%= userName %>">
            <input class="margin" type="hidden" name="Name" value="<%= categoryName %>">
			<% } %>

           	         </form>
        </body>
</html>