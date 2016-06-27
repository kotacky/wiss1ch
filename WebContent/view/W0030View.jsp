<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html >
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/view/css/W0030.css" rel="stylesheet" type="text/css" />
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
  	       //スペース、空文字、null入力を未記入と判断する。又、入力時の空白を無くす。
           function Registration(){
        	  if(!((document.MyForm.categoryName.value.trim()=="")||(document.MyForm.categoryName.value==null))){
        		  document.MyForm.categoryName.value = document.MyForm.categoryName.value.trim()
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
			<H2><% out.print("カテゴリ名が重複しています。"); %></H2>
				<META HTTP-EQUIV="refresh"
                content="5;http://localhost:8080/wiss1ch/view/W0030View.jsp">
			<% } %>
		<% }catch(NullPointerException insertException){ %>
	<% } %>
 	 <body>

 		<form name="MyForm" method="POST" action="#">
			<div align="right">
			<% out.print(session.getAttribute("userName")); %>
    		<input style="margin-left:20px" type="button" class="button" name="logout" value="ログアウト" onClick="logOut();">
    	    </div>

    		<h1>
   		    <img src="<%= request.getContextPath() %>/view/img/wiss1ch.png">
    		</h1>

      		<p><center><B><span style="font-size:16px">管理者画面</span></B></center></p>
			<p><center><B><span style="font-size:16px">カテゴリの追加</span></B><br>
			<input type="text" name="categoryName"
		 	maxlength="20" value="" placeholder="例：芸能">
			<input type="button"  value="カテゴリ登録" onClick="Registration();">
			<input type="hidden" name="userName" value=<%="\""+session.getAttribute("userName")+"\"" %>>
			</center></p>
		</form>

	</body>