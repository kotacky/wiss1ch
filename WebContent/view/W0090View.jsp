<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
	<meta charset="UTF-8">
	<link href="<%= request.getContextPath() %>/view/css/W0090.css" rel="stylesheet" type="text/css" />
	<title>WISS1ch</title>
	<script type="text/javascript">

	//文字数カウント
	function ShowLength( idn, str ) {
			document.getElementById(idn).innerHTML = str.length;
	}

	//リクエストパラメータの文字コードを指定
	function logOut(){
		MyMessage = confirm("ログアウトします。よろしいですか？");
		if ( MyMessage == true ){
			document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
			document.MyForm.submit();
		}else{
			return;
		}
    }

	//メッセージ送信
	function send_message(command){
		var k = document.MyForm.messageTitle.value.length;
		var l = document.MyForm.message.value.length;
		var id = document.MyForm.recUserId.value;
		if (id == "-") {
			alert("送信先を選択してください。");
		} else {
			if (l > 200) {
				alert("本文は200文字以内で入力してください。");
			} if (document.MyForm.message.value == '') {
				alert("本文を入力してください。")
			} else {
				if (k > 30) {
					alert("件名は30文字以内で入力してください。")
				} else {
					if (document.MyForm.messageTitle.value == '') {
						document.MyForm.messageTitle.value = '(無題)';
					}

					if (confirm("この内容で送信しますか？")) {
						document.MyForm.actionId.value = command;

			       		document.MyForm.action = "<%= request.getContextPath() %>/W0090Control"

					} else {
						alert("キャンセルしました。");
					}
				}
			}
       	}
	}

	function go_portal(){
		document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
		document.MyForm.submit();
	}

	function go_message(){
		document.MyForm.action = "<%= request.getContextPath() %>/W0100Control"
		document.MyForm.submit();
	}

	//プルダウンの変更を確認
	function changepulldown(){
		//selectedIndexは「今」選択されているoptionを指す。返り値は数字。
		var pullop = document.MyForm.pldw.selectedIndex;
		// optionのvalueを取得
		var pullval = document.MyForm.pldw.options[pullop].value;
		//alert(pullval)//オンチェンジ動作確認用、値拾う
	}

	</script>
 	</head>
	<body>
 	<form name="MyForm" method="POST" action="#">
 		<div align="right">
		<% out.print(session.getAttribute("userName")); %>
		<% String message = (String)request.getAttribute("message"); %>
    	<a style="margin-left:20px"class="button" name="logout" onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
        </div>



     	<h1>
   		<a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
    	</h1>
		<input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick="go_message();">

    	<CENTER><font size=5>メッセージ送信フォーム<br><br></font></CENTER>

    	<%

		//ユーザ情報取得
		List<HashMap<String,String>> userList = (List<HashMap<String,String>>)request.getAttribute("userList");

		%>

		<div class="form">
			<div class="txt">
			送信先<br>
				<select id="recUserId" name="recUserId" onchange="changepulldown()">
					<option value="-">送信先を選んでください。</option>
						<%	if (userList != null) { %>
							<% 	for (HashMap<String,String> userInfo : userList) { %>
							<% 	String recUserId = userInfo.get("userId"); %>
							<%	String UserName = userInfo.get("userName"); %>
							<option value="<%= recUserId %>"><% out.print(UserName); %></option>
								<% } %>
							<% } %>
				</select><br>
			件名<br>
				<input type="text" id="messageTitle" name="messageTitle" size="40" placeholder="(無題)"><br>
			本文<br>
				<textarea class="margin"  onkeyup="ShowLength( 'nummoji' , value );"  id="message" name="message"  rows="20" cols="60" ></textarea>
			<span id="nummoji">0</span><br>
			<font size=2>件名は30文字以内、本文は200文字以内でご入力ください。</font>
			<br>
			<input type="submit" value="送信" onClick="send_message('send_message')">

			<input type ="hidden" name ="actionId" value ="">
			<input type ="hidden" name ="hiddenUserid" value ="">
			</div>
		</div>

	</form>
	</body>
</html>