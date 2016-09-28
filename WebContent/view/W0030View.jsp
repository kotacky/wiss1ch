<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html >
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <link href="<%= request.getContextPath() %>/view/css/W0030.css" rel="stylesheet" type="text/css" />
        <title>WISS1ch</title>
        <script type="text/javascript">

            //ログアウト
            function logOut(){
                if ( confirm("ログアウトします。よろしいですか？") ){
                    document.MyForm.action = "<%= request.getContextPath() %>/W0000Control"
                    document.MyForm.submit();
                }else{
                    return;
                }
            }

            //ポータル帰還
            function go_portal(){
                document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
                document.MyForm.submit();
            }

            //カテゴリ一覧へ遷移
            function go_categorylist(){
                document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
                document.MyForm.submit();
            }

            //カテゴリ登録・更新
            function Registration(updateflag){
                //空白のみと無記入をはじく
                if(!((document.MyForm.categoryName.value.trim()=="")||(document.MyForm.categoryName.value==null))){
                    //空白を除去
                    document.MyForm.categoryName.value = document.MyForm.categoryName.value.trim()
                    if(updateflag == "1"){
                        //flagが1の場合更新
                        document.MyForm.actionId.value = 'update';
                    }else{
                        //flagが0の場合新規登録
                        document.MyForm.actionId.value = 'insert';
                    }
                    document.MyForm.action = "<%= request.getContextPath() %>/W0030Control"
                    document.MyForm.submit();
                }else{
                    alert("未記入です。");//エラー表示
                    return;
                }
            }

            //プルダウンの変更を確認
            function changepulldown(){
                //selectedIndexは「今」選択されているoptionを指す。返り値は数字。
                var pullop = document.MyForm.pldw.selectedIndex;
                // optionのvalueを取得
                var pullval = document.MyForm.pldw.options[pullop].value;
                //alert(pullval)//オンチェンジ動作確認用、値拾う
            }

        </script>
    </head>

    <body>
         <form name="MyForm" method="POST" action="#">

        <% try{ %>
            <% String sessionflag = session.getAttribute("adminFlg").toString();
                if(sessionflag.equals("f") ){
                    //管理者権限が無い場合、ログイン画面に飛ばす
                    RequestDispatcher dispatch =getServletContext().getRequestDispatcher("/view/W0010View.jsp");
                    dispatch.forward(request, response);
                }
            %>

        <% }catch(NullPointerException insertException){ %>
        <% } %>

          <div align="right">
            <% String insertFlag = (String) request.getAttribute("insertFlag"); %>
            <% String upflag = (String) request.getAttribute("update_flag"); %>
            <% String cid = (String) request.getAttribute("categoryId"); %>
            <% out.print(session.getAttribute("userName")); %>
            <a style="margin-left:20px"class="button" name="logout"onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
        </div>

        <input type="button" value="戻る" style="position: absolute; left: 20px; top: 0px;" onClick=go_categorylist();>

        <h1>
               <a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
        </h1>

        <p><center><B><span style="font-size:32px">カテゴリ<% if(upflag.equals("1")){ out.print("変更"); }else{ out.print("登録");}%>画面</span></B>
        <br><br>
        <% if("0".equals(insertFlag)){ %>
            <H2><% out.print("<CENTER><font size=2 color=red>カテゴリ名が重複しているか、大カテゴリが選択されていません。</font></CENTER>"); %></H2>
        <% } %>
                <SELECT <% if(upflag.equals("1")){ out.print("disabled");} %> onchange= "changepulldown()"  name="pldw">
                    <OPTION value="0" selected disabled>大カテゴリを選択</OPTION>
                    <OPTION value="1">16'新人</OPTION>
                    <OPTION value="2">Java</OPTION>
                    <OPTION value="3">野球</OPTION>
                    <OPTION value="4">麻雀</option>
                </SELECT>
            <input type="text" required name="categoryName" maxlength="20" value="" placeholder="例：芸能">
            <input type="button" value="カテゴリ<% if(upflag.equals("1")){ out.print("変更"); }else{ out.print("追加");}  %>" onClick="Registration(<%= upflag%>);">
            <input type="hidden" name="actionId" value="">
            <input type="hidden" name="userId" value=<%="\""+session.getAttribute("userId")+"\"" %>>
            <input type="hidden" name="categoryId" value="<% out.print(cid); %>">
        </center></p>
        </form>
    </body>
</html>