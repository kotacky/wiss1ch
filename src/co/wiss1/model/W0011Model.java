package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0011Model {
    public static void main (String args[]) {
    }
    public static List<HashMap<String, String>> getMessageList(String Id) {
        System.out.println("W0011 getMessageList (" + Id + ")");

        // メッセージ一覧を格納する箱
        List<HashMap<String, String>> messageList = new ArrayList<HashMap<String, String>>();
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
                sb.append("SELECT message_id, create_date, send_user_id, receive_user_id "
                            + "FROM t_message "
                            + "WHERE ((send_user_id, receive_user_id, message_id) IN ( "
                            + "SELECT send_user_id, receive_user_id, MAX(message_id) "
                            + "FROM t_message "
                            + "GROUP BY send_user_id, receive_user_id)) "
                            + "AND (delete_flg = FALSE "
                            + "AND (send_user_id = '" + Id + "' OR receive_user_id = '" + Id + "')) "
                            + "AND create_date BETWEEN current_timestamp - interval '1 days' AND current_timestamp");
            //sbの箱SELECT * FROM t_message のうち送受信者のいずれかが自分で WHERE m.delete_flg = 'FALSE'
            //その中で24時間以内に登録されたものをSELECT
            // SQL文実行
            System.out.println("W0011M " + sb.toString());
            resultSet = statement.executeQuery(sb.toString());
            //resultSet実行した結果executeQuery＝要求をＳＱＬとしてＤＢに投げる
            // 実行結果の取得・次の行を呼ぶ
            while(resultSet.next()) {
                HashMap<String, String> messageInfo = new HashMap<String, String>();
                messageInfo.put("messageId", resultSet.getString("message_id"));
                messageInfo.put("sendUserId", resultSet.getString("send_user_id"));
                messageInfo.put("recUserId", resultSet.getString("receive_user_id"));
                messageInfo.put("postTime", resultSet.getString("create_date"));
                messageList.add(messageInfo);
                System.out.println(messageInfo.get("postTime"));
            }
        } catch (SQLException e) {
            System.out.println("メッセージ一覧SQL実行処理失敗!!");
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
        return messageList;
    }
}

