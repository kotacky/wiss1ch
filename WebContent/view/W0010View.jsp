<%@ page contentType="text/html; charset=Windows-31J" %>
<!DOCTYPE html>
<html lang="ja">
	<head>

	<link rel="stylesheet" type="text/css" href="wiss1ch/WebContent/view/css/W0010.css">
	<link rel="stylesheet" type="text/css" href="./css/W0010.css">
	<meta charset="Windows-31J">
	<title>WISS1</title>
	<div style="bottom:40;"><h1 style="text-align: center;"><span style="color:#6A5ACD;font-size:50px;">WISS1ch</span></h1></div>
	<script type="text/javascript">
	function login(){
		if(document.myForm.userId.value == ""){
			alert("ユーザーIDを入力してください。");

			// バグ修正中につきコメントアウト
/*			var str="user";
			document.write(str.length);

		}else if (document.myForm.Nam.value.length() <= 3) {
			alert("ユーザーIDが短すぎます。"); */

		}else if (document.myForm.password.value == ""){
			alert("パスワードを入力してください。");

		}else{
			document.myForm.action = "<%= request.getContextPath() %>/W0010Control"
			document.myForm.submit();
		}
	}
	</script>

</head>
<body style="background-color:#D3D3D3">
	<div class="form-wrapper">
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

