<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html >
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <link href="<%= request.getContextPath() %>/view/css/W0080.css" rel="stylesheet" type="text/css" />
        <title>WISS1ch</title>
        <script type="text/javascript">

            //文字数カウント
            function ShowLength( idn, str ) {
                document.getElementById(idn).innerHTML = str.length;
            }

            //リクエストパラメータの文字コードを指定
            function logOut(){
                if ( confirm("ログアウトします。よろしいですか？") ){
                    document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
                    document.MyForm.submit();
                }else{
                    return;
                }
            }

            function insert_inq(command){
                var l = document.MyForm.inquiry.value.length;
                if (l > 1000) {
                    alert("1000文字以内で入力してください。");
                } else {
                    if (confirm("この内容で送信しますか？")) {
                        document.MyForm.actionId.value = command;
                        document.MyForm.action = "<%= request.getContextPath() %>/W0080Control"
                    } else {
                        alert("キャンセルしました。");
                    }
                }
            }

            function go_portal(){
                document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
                document.MyForm.submit();
            }

        </script>
    </head>
    <body>
        <form name="MyForm" method="POST" action="#">
            <div align="right">
                <% out.print(session.getAttribute("userName")); %>
                <% String inquiry = (String)request.getAttribute("inquiry"); %>
                <a style="margin-left:20px"class="button" name="logout" onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
            </div>

            <h1>
                <a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
            </h1>
            <input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick="javascript:history.back();">

            <CENTER><font size=6>お問い合わせ</font></CENTER><br>

            <CENTER>

                お問い合わせ内容<br>
                <textarea class="margin"  onkeyup="ShowLength( 'numOfLetter' , value );"  id="inquiry" name="inquiry"  rows="20" cols="60" ></textarea>
                <span id="numOfLetter">0</span>
                <br>
                <font size=2>1000文字以内でご入力ください。</font><br>
                <input type="submit" value="送信" onClick="insert_inq('insert_inq')">

                <input type ="hidden" name ="actionId" value ="">
                <input type ="hidden" name ="hiddenUserid" value ="">

            </CENTER>
        </form>
    </body>
</html>