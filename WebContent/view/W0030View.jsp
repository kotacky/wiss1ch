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
	 <body>

 	<form name="MyForm" method="POST" action="#">

	<% try{ %>
		<% String sessionflag = session.getAttribute("adminFlg").toString();
			System.out.println("W0030View管理者権限は" + sessionflag + "です。");
			if(sessionflag.equals("f") ){
			    //管理者権限が無い場合、ログイン画面に飛ばす
			    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
			    dispatch.forward(request, response);
		    }%>
		<% String insertFlag = request.getAttribute("insertFlag").toString(); %>
		<% if("0".equals(insertFlag)){ %>
			<H2><% out.print("カテゴリ名が重複しています。"); %></H2>
			<% } %>

	<% }catch(NullPointerException insertException){ %>
	<% } %>


 	 			<div align="right">
			<% out.print(session.getAttribute("userId")); %>
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
			<input type="hidden" name="userId" value=<%="\""+session.getAttribute("userId")+"\"" %>>
			</center></p>
		</form>

	</body>
</html>