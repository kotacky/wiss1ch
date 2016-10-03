package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0100Model {

    public static List<HashMap<String, String>> getMessageList(String Id) {
        System.out.println("W0100M getMessageList (" + Id + ")");

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
                sb.append("SELECT DISTINCT m.message_id, m.message_title, m.message, m.create_date, m.send_user_id, m.receive_user_id, "
                            + "u.user_name AS receive_user_name, s.send_user_name "
                            + "FROM t_message m "
                            + "INNER JOIN (SELECT m.send_user_id, u.user_name AS send_user_name FROM t_message m INNER JOIN t_user_info u ON m.send_user_id = u.user_id ) s "
                            + "ON m.send_user_id = s.send_user_id "
                            + "INNER JOIN t_user_info u "
                            + "ON m.receive_user_id = u.user_id "
                            + "WHERE m.delete_flg = FALSE AND (m.send_user_id = '" + Id + "' OR m.receive_user_id = '" + Id + "')"
                            + "ORDER BY m.message_id ");
                System.out.println(sb.toString());
            //sbの箱SELECT * FROM t_message のうち送受信者のいずれかが自分で WHERE m.delete_flg = 'FALSE' message_id昇順にいれる
            // SQL文実行
            resultSet = statement.executeQuery(sb.toString());
            //resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
            // 実行結果の取得・次の行を呼ぶ
            while(resultSet.next()) {
                HashMap<String, String> messageInfo = new HashMap<String, String>();
                messageInfo.put("messageId", resultSet.getString("message_id"));
                messageInfo.put("messageTitle", resultSet.getString("message_title"));
                messageInfo.put("message", resultSet.getString("message"));
                messageInfo.put("sendUserName", resultSet.getString("send_user_name"));
                messageInfo.put("recUserName", resultSet.getString("receive_user_name"));
                messageInfo.put("sendUserId", resultSet.getString("send_user_id"));
                messageInfo.put("recUserId", resultSet.getString("receive_user_id"));
                messageInfo.put("postTime", resultSet.getString("create_date"));
                messageList.add(messageInfo);
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



    public static int updateMessage(String checkBox[]) {	 										//メッセージ削除

        Connection connection = null;
        Statement statement = null;
        int UpdateCount = 0;

        try

        {
            // メッセージ一覧照会実行
            connection = DBAccessUtils.getConnection();												//DBへ接続
            statement = connection.createStatement();												//Statementを取得するためのコード

            connection.setAutoCommit(true);							 								//自動コミットを有効にする
            for (int i = 0; i < checkBox.length; i++ ) {
            String sql = "UPDATE t_message SET delete_flg = 'TRUE' WHERE message_id =  '"+ checkBox[i] +"'";

                UpdateCount = statement.executeUpdate (sql);
                if(UpdateCount >= 1){																	//削除が成功しているかどうかの確認
                    System.out.println("削除成功");
                }
                else{
                    System.out.println("削除失敗");
                }
            }
        }
        catch (SQLException e){
        System.err.println("メッセージ削除のSQL failed.");
        e.printStackTrace ();																		//エラーの情報
        }
         finally{
        }
    return UpdateCount;
    }


    //削除(単独)
    public static int updateSoloMessage(String MessageId, String userId) {

        Connection connection = null;
        Statement statement = null;
        int updateCount = 0;

        try{
            //DB接続
            connection = DBAccessUtils.getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(true);

            //単独削除
            String sql = "UPDATE t_message SET delete_flg = TRUE, update_date = current_timestamp, update_user = '"+ userId +"' WHERE post_id = '"+ MessageId + "'";
            //SQL実行
            updateCount = statement.executeUpdate (sql);
        }catch (SQLException e){
            System.err.println("単独削除SQL failed.");
            e.printStackTrace ();
        }finally{
            try {
                //クローズ処理
                statement.close();
                connection.close();
            } catch (Exception e) {
                System.err.println("クローズ処理失敗!!");
                e.printStackTrace ();
            }
        }
        return updateCount;
    }
}