<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html >
<html lang="ja">
 	   <head>
 	   <link href="<%= request.getContextPath() %>/view/css/W0030.css" rel="stylesheet" type="text/css" />
 	   <meta charset="UTF-8">
       <title>管理者画面</title>
       <script type="text/javascript">
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
        	  if(document.MyForm.categoryName.value != ""){
        		  document.MyForm.action = "<%= request.getContextPath() %>/W0030Control"
        	      document.MyForm.submit();
        	  }else{
        		  alert("未記入です。");//値
        		  return;
        	  }
           }

       </script>
 	 </head>

 	 <% try{ %>
		<% String insertFlag = request.getAttribute("insertFlag").toString(); %>
		<% if("0".equals(insertFlag)){ %>
			<H2><% out.print("登録が失敗しました。"); %></H2>
			<% } %>
		<% }catch(NullPointerException insertException){ %>
	<% } %>
 	 <body>

 		<form name="MyForm" method="POST" action="#">
			<div align="right">
    		<input type="button" class="button" name="logout" value="ログアウト" onClick="logOut();">
    	    </div>

    		<h1><center> WISS1ch</center></h1>

      		<p><center><B><span style="font-size:16px">管理者メニュー</span></B></center></p>
			<p><center><B><span style="font-size:16px">カテゴリの追加</span></B><br>
			<input type="text" name="categoryName"
		 	maxlength="20" value="" placeholder="例：芸能">
			<input type="button"  value="カテゴリ登録" onClick="Registration();">
			</center></p>
		</form>

	</body>
