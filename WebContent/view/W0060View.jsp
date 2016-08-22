<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html >
<html lang="ja">
	<head>
	<meta charset="UTF-8">
	<link href="<%= request.getContextPath() %>/view/css/W0060.css" rel="stylesheet" type="text/css" />
	<title>WISS1ch</title>
	<script type="text/javascript">

	//リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");
		function logOut(){
			MyMessage = confirm("ログアウトします。よろしいですか？");
			if ( MyMessage == true ){
				document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
				document.MyForm.submit();
			}else{
				return;
			}
         }

		function Registration(){
			alert('追加します。');
			document.MyForm.actionId.value = 'Registration';
			document.MyForm.action = "<%= request.getContextPath() %>/W0060Control"
			document.MyForm.submit();
		}

       </script>
 	 </head>
	 <body>

 	<form name="MyForm" method="POST" action="#">

 	 		<div align="right">
			<% out.print(session.getAttribute("userName")); %>
    		<a style="margin-left:20px"class="button" name="logout"onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
    	    </div>

    		<h1>
   		    <img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    		</h1>
			<CENTER><font size=6>ユーザー登録入力画面</font></CENTER><br></br>
			<CENTER>
				<table border="1">

					<tr>
						<td>ユーザーID(半角英数字)：</td>
						<td><input required pattern="^[0-9A-Za-z]+$" type="text" name="userId" size="20"   maxlength="10"></td>
					</tr>
					<tr>
						<td>ユーザー名(全角文字)：</td>
						<td><input required pattern="[^\x20-\x7E]*" type="text" name="userName" size="20" maxlength="10"></td>
					</tr>
					<tr>
						<td>住所：</td>
						<td><textarea required name="userAddress" cols="30" rows="3"></textarea></td>
					</tr>
					<tr>
						<td>パスワード(半角文字　8字以上)：</td>
						<td><input required pattern="^([a-zA-Z0-9]{8,})$" type="password" name="passWord" size="20" maxlength="40"></td>
					</tr>
					<tr>
						<td>確認用パスワード(半角文字　8字以上)：</td>
						<td><input required pattern="^([a-zA-Z0-9]{8,})$" type="password" name="conPassword" size="20" maxlength="40"></td>
					</tr>
					<tr>
						<td>文字色：</td>
						<td>
						<select name=Font_color>
						<option style = "color : #000000 ;" value ="1">黒</option>
						<option style = "color : #ff0000 ;" value ="2">赤</option>
						<option style = "color : #0000ff ;" value ="3">青</option>
						<option style = "color : #008000 ;" value ="4">緑</option>
						<option style = "color : #ffcc00 ;" value ="5">黄</option>
						<option style = "color : #ffa500 ;" value ="6">橙</option>
						<option style = "color : #800080 ;" value ="7">紫</option>
						<option style = "color : #adff2f ;" value ="8">黄緑</option>
						<option style = "color : #87ceeb ;" value ="9">空色</option>
						<option style = "color : #000080 ;" value ="10">紺</option>
						<option style = "color : #a52a2a ;" value ="11">茶</option>
						<option style = "color : #d2691e ;" value ="12">チョコ</option>
						</select>
						</td>
					</tr>
				</table> <br></br>　
						<input type="submit" value="登録" onClick="Registration();">
						<input type="reset" value="キャンセル">
						<input type = "hidden" name = "actionId" value = "">
			</CENTER>
	</form>
	</body>
</html>