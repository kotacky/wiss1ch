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

public class W0060Model {

	//郵便番号をもとに住所を引っ張る。
		public static String getAddressInfo(String postalCode) {
		System.out.println("W0060 getAddressinfo ("+ postalCode +")");
		    // 住所データを格納する箱
		    String addressInfo = null;
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
		        statement = connection.createStatement();
		        // SQL文作成
		        StringBuffer sb = new StringBuffer();
		        // SQL文実行
		        System.out.println("W0060M ," + sb.toString());
		        sb.append("SELECT prefecture_name, city_name, town_area, chome FROM t_address_master WHERE postal_code = '"+ postalCode +"'");
		        System.out.println("W0060M getAddressinfo:" + sb.toString());
		        resultSet = statement.executeQuery(sb.toString());
		        // 実行結果の取得・次の行を呼ぶ
		        if (resultSet.next()) {
			        addressInfo = resultSet.getString("prefecture_name") + resultSet.getString("city_name") + resultSet.getString("town_area") + resultSet.getString("chome");
		        }

		    } catch (SQLException e) {
		        System.out.println("住所マスタSQL実行処理失敗!!");
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
		    return addressInfo;
		}

	//ユーザ一覧
    public static List<HashMap<String, String>> getUserList(String userId) {
        System.out.println("W0060 getUserList (" + userId + ")");

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
            StringBuffer sb = new StringBuffer();
            //sb.append("SELECT user_id);
            // SQL文実行
            System.out.println("W0060M ," + sb.toString());
            sb.append("SELECT user_id, user_name, postal_code, user_address, user_mail, password FROM t_user_info WHERE delete_flg = FALSE");
            System.out.println("W0060M getUsertList:" + sb.toString());
            resultSet = statement.executeQuery(sb.toString());//resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
            // 実行結果の取得・次の行を呼ぶ
            while(resultSet.next()) {
                HashMap<String, String> userInfo = new HashMap<String, String>();
                userInfo.put("userId", resultSet.getString("user_id"));
                userInfo.put("userName", resultSet.getString("user_name"));
                userInfo.put("postalCode", resultSet.getString("postal_code"));
                userInfo.put("userAddress", resultSet.getString("user_address"));
                userInfo.put("userMail", resultSet.getString("user_mail"));
                userInfo.put("passWord", resultSet.getString("password"));
                userList.add(userInfo);
            }
        } catch (SQLException e) {
            System.out.println("ユーザ一覧SQL実行処理失敗!!");
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

    //ユーザ登録
    public static int registarUser(String loginUser, String userId, String userName,String postalCode, String userAddress, String userMail, String passWord, String conPassword, String fontColor) {
        Connection connection = null;
        Statement statement = null;
        int insertCount = 0;
        System.out.println("W60M 引数は"+ userId +"です" );

        try{
            // コメント一覧照会実行
            connection = DBAccessUtils.getConnection();													//DBへ接続
            statement = connection.createStatement();													//Statementを取得するためのコード

            //自動コミットを有効にする
            connection.setAutoCommit(true);
            //コメントの追加
            String insertSql = "INSERT INTO t_user_info (user_id, user_name,postal_code, user_address, user_mail, password, delete_flg, admin_flg, create_date, update_date, create_user, update_user, font_color)"
                    + " VALUES ('" + userId + "','"+ userName +"','"+ postalCode +"','"+ userAddress +"', '"+ userMail +"','"+ passWord +"', FALSE, FALSE, current_timestamp, current_timestamp, '" + loginUser + "', '" + loginUser +"', '" + fontColor +"')";
            insertCount = statement.executeUpdate (insertSql);
        }catch(PSQLException pe){
            System.err.println("ユーザ重複SQL failed.");
            pe.printStackTrace ();
        }catch (SQLException e){
            System.err.println("ユーザ登録SQL failed.");
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

    //ユーザ情報更新
    public static int updateUser(String loginUser ,String userId,String userName, String postalCode, String userAddress, String userMail, String passWord, String conPassword, String fontColor) {
        Connection connection = null;
        Statement statement = null;
        int updateCount = 0;

        try{
            // コメント一覧照会実行
            connection = DBAccessUtils.getConnection();													//DBへ接続
            statement = connection.createStatement();													//Statementを取得するためのコード

            //自動コミットを有効にする
            connection.setAutoCommit(true);

            //コメントの追加
            String updateSql = "UPDATE t_user_info SET user_name ='" + userName + "',postal_code ='" + postalCode +"' , user_address ='" + userAddress +"', user_mail ='" + userMail +"', password ='" + passWord + "', update_date = current_timestamp , update_user ='" + loginUser +"', font_color = '" + fontColor +"'  WHERE  user_id = '"+ userId +"';";

            updateCount = statement.executeUpdate( updateSql );

        }catch (SQLException e){
            System.err.println("ユーザ情報変更SQL failed.");
            e.printStackTrace ();
        }finally{
            try {
                //もろもろクローズ処理
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
