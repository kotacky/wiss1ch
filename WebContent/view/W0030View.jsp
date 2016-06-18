<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html >
<html lang="ja">
 	<head>
 	<link rel="stylesheet" type="text/css" href="./css/W0030.css">
	<link rel="stylesheet" type="text/css" href="W0030.css">
       <meta charset="UTF-8">
       <title>管理者画面</title>
       <script type="text/javascript">
           function logOut(){
        	   MyMeasage = confirm("ログアウトします。よろしいですか？");
        	       if ( MyMessage == true ){
        	    	 document.myForm.action = "<%= request.getContextPath() %>/W0000Control"
        	         document.MyForm.submit();
        	       }else{
      				 return;
      			   }
           }
           function Registration(){
        	  if(document.myForm.categoryInputText.value != ""){
        		  document.myForm.action = "<%= request.getContextPath() %>/W0030Control"
        	      document.MyForm.submit();
        	  }else{
        		  alert("未記入です。");
        		  return;
        	  }
           }

       </script>
 	 </head>
 	 <body>
 		<form name="myForm" method="GET" action="#">
			<div align="right">
    		<input type="button" name="logout" value="ログアウト" onClick="logOut();">
    		</div>
    		<center> <h1>WISS1ch</h1></center>
      		<p><center>管理者メニュー</center></p>
			<p><center>カテゴリの追加<br>
			<input type="text" name="categoryInputText"
		 	maxlength="20" value="" placeholder="例：芸能">
			<input type="button" value="カテゴリ追加" onClick="Registration();">
			</center></p>
		</form>
	</body>
</html>


