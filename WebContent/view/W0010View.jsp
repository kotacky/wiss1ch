<%@ page contentType="text/html; charset=Windows-31J" %>
<!DOCTYPE html>
<html lang="ja">
	<head>


	<link href="<%= request.getContextPath() %>/view/css/W0010.css" rel="stylesheet" type="text/css" />
	<meta charset="Windows-31J">
	<title>WISS1ch</title>
	<div style="bottom:40;">
	<script type="text/javascript">

	function login(){
		var str =  document.myForm.userId.value;
		var str2 = document.myForm.password.value;
		if(str == ""  && str2 ==""){
			alert("パスワード又は、ユーザーIDを入力してください。");
		}else if  ( str.match(/[^0-9a-zA-Z]/)){
				alert('半角英数字以外の文字が含まれています。');
		}else if (str2.length < 0){
					alert("パスワードを入力して下さい。");
			  if ( str.length < 4){
							alert("ユーザーIDが短すぎます。");
				}
		}else if(str2.length <= 0){
			alert("パスワードを入力して下さい。");

		}else if(str.length < 4) {
			alert("パスワードが短すぎます。");
		 }
		else if ( str.length < 4){
			if ( str.length <= 0){
				alert("ユーザーIDを入力して下さい。");}
			if ( st2r.length <= 0){
				alert("パスワードを入力して下さい。");
			}
			else if(str2 <= 0 && 1<str){
				alert("パスワードを入力して下さい。");
			}
		}else{
			document.myForm.action = "<%= request.getContextPath() %>/W0010Control"
			document.myForm.submit();
		}
	}
	</script>
</head>
<body style>

            <h2><CENTER>
   		    <img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    		</CENTER></h2>

	<div class="form-wrapper">
	<% try{ %>

		<% String EMSG  = request.getAttribute("EMSG0001").toString(); %>

			<H3 style="margin-left:80px;color:red"><% out.print(EMSG); %></H3>
	<% }catch(NullPointerException deleteException){ %>
	<% } %>
  <h1>Login</h1>
  <form name="myForm" method="POST" action="#">
    <div class="form-item">
      <label for="email"></label>
      <input type="text" maxlength="8" name="userId" placeholder="User ID"></input>
    </div>
    <div class="form-item">
      <label for="password"></label>
      <input type="password" maxlength="8" name="password" placeholder="Password"></input>
    </div>
    <div class="button-panel">
    <input type="submit" class="button" title="Login" value="Login" onclick="login();"></input>
    </div>
  </form>
  <div class="form-footer">
    <p><a href="http://www.wiss1.co.jp/">WISS1ch</a></p>
  </div>
</div>
</body>
</html>

