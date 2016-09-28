package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import co.wiss1.common.DBAccessUtils;

public class W0010Model{

    public static HashMap<String, String> getUserInfo(String userId){

        HashMap<String, String> userInfo = new HashMap<String, String>();

        // SQL実行結果格納用Set
        ResultSet resultSet = null;
        // DB接続コネクション
        Connection connection = null;
        // SQLステートメント
        Statement statement = null;

        try {
            // DB接続
            connection = DBAccessUtils.getConnection();
            // SQL実行準備
            statement = connection.createStatement();
            // SQL文作成
            StringBuffer sb  = new StringBuffer();
            sb.append("SELECT user_name, password, admin_flg, font_color, delete_flg FROM t_user_info WHERE user_id = '"+ userId + "'");
            // SQL文実行
            resultSet = statement.executeQuery(sb.toString());

            // ユーザー情報を格納する箱
            resultSet.next();
            userInfo.put("userName", resultSet.getString("user_name"));
            userInfo.put("password", resultSet.getString("password"));
            userInfo.put("adminFlg", resultSet.getString("admin_flg"));
            userInfo.put("fontColor", resultSet.getString("font_color"));
            userInfo.put("deleteFlg", resultSet.getString("delete_flg"));
        } catch (PSQLException e) {
            userInfo.put("残念", "残念");
            System.out.println(userInfo + "■■■■■");
        } catch (SQLException e) {
            System.out.println("SQL実行処理失敗!!!!!");
            e.printStackTrace();
        } finally {
            try {
                // もろもろクローズ処理
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                System.err.println("クローズ処理失敗!!!!!");
                e.printStackTrace ();
            }
        }
        return userInfo;
    }

}