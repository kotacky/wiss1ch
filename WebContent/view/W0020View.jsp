<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8" />
        <link href="<%= request.getContextPath() %>/view/css/W0020.css" rel="stylesheet" type="text/css" />
        <title>WISS1ch</title>

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


        function deletes(Command){
            // チェックボックス要素をすべて取得する
            var boxes = document.getElementsByName("chkbox");
            // チェックボックスの個数を取得する
            var cnt = boxes.length;

            var flg = 0;  //flg:チェックの判定用
            for(var i=0; i < cnt; i++) {
                if(boxes.item(i).checked) {
                    flg = 1;
                }
            }
            if(flg > 0){
                MyMessage = confirm("削除しますか");
                if ( MyMessage == true ){
                    document.MyForm.actionId.value = Command;
                    document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
                    document.MyForm.submit();
                }
            }
            if(flg ==0){
                alert("チェックボックスが未入力です。");
            }
        }

        function Regist(){
            // チェックボックス要素をすべて取得する
            var boxes = document.getElementsByName("chkbox");
            // チェックボックスの個数を取得する
            var cnt = boxes.length;

            var flg = 0;
            for(var i=0; i < cnt; i++) {
                if(boxes.item(i).checked) {
                    flg = flg + 1;
                }
            }
            if(flg==0){
                //登録モードで移行
                document.MyForm.actionId.value = 'start';
                document.MyForm.action = "<%= request.getContextPath() %>/W0030Control"
                document.MyForm.submit();
            }else if(flg == 1){
                //変更モードで移行
                document.MyForm.actionId.value = 'update1st';
                document.MyForm.action = "<%= request.getContextPath() %>/W0030Control"
                document.MyForm.submit();
            }else{
                alert("2個以上のカテゴリ情報変更は出来ません。");
            }
        }

        function move(Command){
            var values = Command.split(','); // , 区切;
            document.MyForm.Id.value = values[0];
            document.MyForm.Name.value = values[1];
            document.MyForm.action = "<%= request.getContextPath() %>/W0040Control"
            document.MyForm.submit();
        }

        //ポータル帰還
        function go_portal(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0011Control"
            document.MyForm.submit();
        }

        function go_inqform(){
            document.MyForm.action = "<%= request.getContextPath() %>/W0080Control"
                document.MyForm.submit();
        }

        //追加した関数
        function changepulldown(){
            var pullop = document.MyForm.pldw.selectedIndex;//selectedIndexは「今」選択されているoptionを指す。返り値は数字。
            var pullval = document.MyForm.pldw.options[pullop].value; // optionのvalueを取得
            //alert(pullval)//オンチェンジ動作確認用、値拾う
            document.MyForm.action = "<%= request.getContextPath() %>/W0020Control"
            document.MyForm.submit();
        }
        </script>
    </head>

    <body>
    <form name="MyForm" method="POST" action="<%= request.getContextPath() %>/W0000Control">
        <input type="hidden" name="categoryId">
        <div align="right">
            <% out.print(session.getAttribute("userName")); %>
            <a style="margin-left:20px"class="button" name="logout"onClick="logOut();"><img src="<%= request.getContextPath() %>/view/img/153.142.124.217 (2).gif"></a>
        </div>
            <input type="button"  value="戻る"  style="position: absolute; left: 20px; top: 0px;" onClick="javascript:history.back();">
        <div>
            <CENTER>
                <h1>
                   <a href="#"  onclick=go_portal();><img src="<%= request.getContextPath() %>/view/img/wiss1ch.png"></a>
                </h1>
                <SELECT onchange= "changepulldown()"  name="pldw">
                    <OPTION value="0" selected disabled>大カテゴリを選択</OPTION>
                    <OPTION value="1">16'新人</OPTION>
                    <OPTION value="2">Java</OPTION>
                    <OPTION value="3">野球</OPTION>
                    <OPTION value="4">麻雀</option>
                </SELECT>

                <%-- テーブルの表示--%>
                <table border="1">

                    <tr>
                        <th>選択</th>
                        <th>カテゴリ名</th>
                    </tr>
            </CENTER>
                    <!-- 変更点 -->



                    <!--  // カテゴリ一覧を取得-->

                    <%List<HashMap<String,String>> categoryList = (List<HashMap<String,String>>)request.getAttribute("categoryList");%>
                    <%
                        String sessionflag = session.getAttribute("adminFlg").toString();
                        System.out.println("★権限は" + sessionflag + "です！！！★");

                        String chk1 = null;
                        String chk2 = null;
                          String str1 = "t";
                        if(sessionflag.equals(str1) ){
                            String str2 = "true";
                            chk1 = str2;
                        }else{
                            String str2 = "disabled";
                            chk1 = str2;
                        }
                    %>
                    <% if (categoryList != null) { %>

                        <% for (HashMap<String,String> categoryInfo : categoryList) { %>
                                <%String Id = categoryInfo.get("categoryId"); %>
                                <%String Name = categoryInfo.get("categoryName");
                                  String OutputName = Name.replaceAll("&","&amp;")
                                                                .replaceAll("<","&lt;")
                                                                .replaceAll(">","&gt;")
                                                                .replaceAll("\"","&quot;")
                                                                .replaceAll("\'","&#39;");
                        %>
                                <tr>
                                    <td><input type="checkbox" <%= chk1  %> name="chkbox" style="width:17px;height:17px;"value="<%= categoryInfo.get("categoryId") %>" onClick="chk();"></td>
                                    <td><a onClick="move('<%=Id %>,<%=OutputName %>');"   href="#"  value=""  ><% out.print(categoryInfo.get("categoryName")); %></a></td>
                        <% } %>
                    <% } %>
                    </tr>
                </table>
            <P>
                <input type="button"  <%=chk1 %> value="カテゴリ登録" onClick="Regist();" >
                <input type="button"  <%=chk1 %> value="カテゴリ削除" onClick="deletes('Update');"><br>
                <input type="button" name=inq_btn value="問い合わせフォーム" onClick=go_inqform();>
                <input type="hidden" name="actionId" value="">
                <input type="hidden" name="Id" value="">
                <input type="hidden" name="Name" value="">
            </div>
            </P>
        </form>
    </body>
</html>
