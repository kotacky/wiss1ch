package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0060Model {

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
			sb.append("SELECT user_id, user_name, user_address, password FROM t_user_info WHERE delete_flg = FALSE");
			System.out.println("W0060M getUsertList:" + sb.toString());
			resultSet = statement.executeQuery(sb.toString());//resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
			// 実行結果の取得・次の行を呼ぶ
			while(resultSet.next()) {
				HashMap<String, String> userInfo = new HashMap<String, String>();
				userInfo.put("userId", resultSet.getString("user_id"));
				userInfo.put("userName", resultSet.getString("user_name"));
				userInfo.put("userAddress", resultSet.getString("user_address"));
				userInfo.put("passWord", resultSet.getString("password"));
				userList.add(userInfo);
				System.out.println(userInfo.get("userId"));
				System.out.println(userInfo.get("userName"));
				System.out.println(userInfo.get("userAddress"));
				System.out.println(userInfo.get("passWord"));
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


	public static int registarUser(String userId, String userName, String userAddress, String passWord, String conPassword) {
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
	        String insertSql = "INSERT INTO t_user_info (user_id, user_name, user_address, password, delete_flg, admin_flg, create_date, update_date)"
	        		+ " VALUES ('" + userId + "','"+ userName +"','"+ userAddress +"','"+ passWord +"', FALSE, FALSE, current_timestamp, current_timestamp)";
	        System.out.println("W0060M : INSERT");
	        insertCount = statement.executeUpdate(insertSql);
	        System.out.println(insertSql);

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
	public static int updateUser(String userId,String userName, String userAddress, String passWord) {
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
	        String updateSql = "UPDATE t_user_info" + "SET user_name = userName,userAddress = User_address,update_date=current_timestamp,update_user=userId password = passWord," + "WHERE user_ID = userid";
	        System.out.println("W0060M : update");
	        updateCount = statement.executeUpdate(updateSql);
	        System.out.println(updateSql);

		}catch (SQLException e){
			System.err.println("ユーザ情報変更SQL failed.");
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
		return updateCount;
	}


}

