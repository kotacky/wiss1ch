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
		function logOut(){
			MyMessage = confirm("ログアウトします。よろしいですか？");
			if ( MyMessage == true ){
				document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
				document.MyForm.submit();
			}else{
				return;
			}
         }

		function Registration(Update){
				//alert(Update);

				//パスワードが一致していなかった場合
				if(document.MyForm.passWord.value != document.MyForm.conPassword.value){
					alert("パスワードが一致していません。");
					return;
				}else if(Update == "1"){
					//パスワードが一致していた時
        			//flagが1の場合更新
	        		//alert("update:");
	        		document.MyForm.actionId.value = 'update';
	        		document.MyForm.hiddenUserid.value = document.MyForm.userId.value;
        		}else{
        			//flagが0の場合新規登録
	        		//alert("insert:");
	        		document.MyForm.actionId.value = 'Registration';
        		}
        		//alert("actionID : " + document.MyForm.actionId.value);
        		document.MyForm.action = "<%= request.getContextPath() %>/W0060Control"
        	   // document.MyForm.submit();
  		}

		function changepuldown(){
			var select = document.MyForm.fontColor.selectedIndex;
			//alert("index:"+select);
			var selectValue = document.MyForm.fontColor.options[select].value;
			//alert("value:"+selectValue);
		}


       </script>
 	 </head>
	 <body>
 	<form name="MyForm" method="POST" action="#">
 	<fieldset>
 	 		<div align="right">
			<% out.print(session.getAttribute("userName")); %>
			<% String updateFlg = (String)request.getAttribute("updateFlg"); %>
			<% String userId = (String)request.getAttribute("userId"); %>
			<% String userName = (String)request.getAttribute("userName"); %>
			<% String userAddress = (String)request.getAttribute("userAddress"); %>
    		<a style="margin-left:20px"class="button" name="logout" onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
    	    </div>

    		<h1>
   		    <img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    		</h1>

    		<% String registar = request.getAttribute("registar").toString();%>
    		<CENTER><font size=6>ユーザー<% if(updateFlg.equals("1")){ out.print("変更"); }else{ out.print("登録");}  %>画面</font></CENTER><br></br>
			<% if(registar.equals("0")){out.print("<CENTER><font size='2' color = 'red'>ユーザIDが重複しています。</font></CENTER><br></br>");}%>

			<CENTER>
				<table border="1">

					<tr>
						<td>ユーザーID(半角英数字)：</td>
						<td><input pattern=^[0-9A-Za-z]+$ <% if (updateFlg.equals("1")) { out.print("disabled");}%> value="<% if (updateFlg.equals("1")) { out.print(userId);}%>"  type="text" name="userId" size="20" maxlength="10" required></td>
					</tr>
					<tr>
						<td>ユーザー名(全角文字)：</td>
						<td><input pattern=[^\x20-\x7E]* type="text" name="userName" value="<% if (updateFlg.equals("1")) { out.print(userName);}%>" size="20" maxlength="10"required></td>
					</tr>
					<tr>
						<td>住所：</td>
						<td><textarea name="userAddress" cols="30" rows="3" required><% if (updateFlg.equals("1")) { out.print(userAddress);}%></textarea></td>
					</tr>
					<tr>
						<td>パスワード(半角文字　8字以上)：</td>
						<td><input pattern=^([a-zA-Z0-9]{8,})$ type="password" name="passWord" size="20" maxlength="40" required></td>
					</tr>
					<tr>
						<td>確認用パスワード(半角文字　8字以上)：</td>
						<td><input pattern=^([a-zA-Z0-9]{8,})$ type="password" name="conPassword" size="20" maxlength="40" required></td>
					</tr>
					<tr>
						<td>文字色：</td>
						<td>
						<select name="fontColor" onchange = "changepuldown()" >
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
						<input type="submit" value=<% if(updateFlg.equals("1")){ out.print("変更"); }else{ out.print("登録");}  %> onClick="Registration(<%= updateFlg%>)">

						<input type ="hidden" name ="actionId" value ="">
						<input type ="hidden" name ="hiddenUserid" value ="">

			</CENTER>
	</fieldset>
	</form>
	</body>
</html>