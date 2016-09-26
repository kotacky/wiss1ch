package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import co.wiss1.common.DBAccessUtils;

public class W0090Model {

    public static List<HashMap<String, String>> getUserList() {
        System.out.println("W0090 getUserList ()");

        // ユーザ一覧を格納する箱
        List<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
        // SQL実行結果格納用Set
        ResultSet resultSet = null;
        // DB接続コネクション
        Connection connection = null;
        // SQLステートメント
        Statement statement = null;

        try {
            // DB接続
            connection = DBAccessUtils.getConnection();//DB接続申請
            // SQL実行準備
            statement = connection.createStatement();//SQLを
            // SQL文作成
            StringBuffer sb = new StringBuffer();//一時的に文字を保管する
                sb.append("SELECT user_id, user_name FROM t_user_info WHERE delete_flg = 'FALSE' ORDER BY user_name");//sbの箱SELECT * FROM t_category WHERE delete_flg = 'FALSE' ORDER BY category_idにいれる

            // SQL文実行
            System.out.println("W0090M ," + sb.toString());
            resultSet = statement.executeQuery(sb.toString());//resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
            // 実行結果の取得・次の行を呼ぶ
            while(resultSet.next()) {
                HashMap<String, String> userInfo = new HashMap<String, String>();
                userInfo.put("userId", resultSet.getString("user_id"));
                userInfo.put("userName", resultSet.getString("user_name"));
                userList.add(userInfo);
                System.out.println(userInfo.get("userId"));
                System.out.println(userInfo.get("userName"));
            }
        } catch (SQLException e) {
            System.out.println("カテゴリ一覧SQL実行処理失敗!!");
            e.printStackTrace();//どういうバグがあって落ちたのかログを保存する
        } finally {
            try {
                // もろもろクローズ処理
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                System.err.println("クローズ処理失敗!!");
                e.printStackTrace ();
            }
        }
        return userList;
    }


    public static int sendMessage(String loginUser, String messageTitle, String message, String rUserId) {
        Connection connection = null;
        Statement statement = null;
        int insertCount = 0;
        System.out.println("W0090M 引数は"+ loginUser +"です" );

        try{
            // コメント一覧照会実行
            connection = DBAccessUtils.getConnection();				//DBへ接続
            statement = connection.createStatement();				//Statementを取得するためのコード

            //自動コミットを有効にする
            connection.setAutoCommit(true);
            //コメントの追加
            String insertSql = "INSERT INTO t_message (message_title, message, send_user_id, receive_user_id, create_date, create_user, update_date, update_user)"
                             + " VALUES ('" + messageTitle + "', '" + message + "', '" + loginUser + "', '" + rUserId + "', current_timestamp, '" + loginUser + "', current_timestamp, '" + loginUser + "')";
            System.out.println("W0090M :" + insertSql);
            insertCount = statement.executeUpdate (insertSql);
        }catch (SQLException e){
            System.err.println("問い合わせ送信SQL failed.");
            e.printStackTrace ();
        }finally{
            try {
             // もろもろクローズ処理
                statement.close();
                connection.close();
            } catch (Exception e) {

                System.err.println("クローズ処理失敗!!");
                e.printStackTrace ();
            }
        }
        return insertCount;
    }
}

