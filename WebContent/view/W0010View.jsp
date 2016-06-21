<%@ page contentType="text/html; charset=Windows-31J" %>
<!DOCTYPE html>
<html lang="ja">
	<head>


	<link href="<%= request.getContextPath() %>/view/css/W0010.css" rel="stylesheet" type="text/css" />
	<meta charset="Windows-31J">
	<title>WISS1</title>
	<div style="bottom:40;"><h1 style="text-align: center;"><span style="color:#6A5ACD;font-size:50px;">WISS1ch</span></h1></div>
	<script type="text/javascript">
	function login(){
		var str = document.myForm.password.value;
		if (document.myForm.userId.value == ""){
			//no1
			alert("ユーザーIDを入力してください。");

		}else if (str == ""){//no2

			alert("パスワードを入力してください。");
		}else if( str.length < 4){
			alert("パスワードが短い");
		}
		else{
			document.myForm.action = "<%= request.getContextPath() %>/W0010Control"
			document.myForm.submit();
		}
	}
	</script>

</head>
<body style="background-color:#D3D3D3">
	<div class="form-wrapper">
	<% try{ %>
		<% String EMSG  = request.getAttribute("EMSG0004").toString(); %>

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
      <input type="button" class="button" title="Login" value="Login" onclick="login();"></input>
    </div>
  </form>
  <div class="form-footer">
    <p><a href="http://www.wiss1.co.jp/">WISS1ch</a></p>
  </div>
</div>
</body>
</html>

