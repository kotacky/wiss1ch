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

public class W0080Model {

	public static int sendInquiry(String loginUser, String inquiry) {
		Connection connection = null;
		Statement statement = null;
		int insertCount = 0;
		System.out.println("W80M 引数は"+ loginUser +"です" );

		try{
			// コメント一覧照会実行
			connection = DBAccessUtils.getConnection();									//DBへ接続
			statement = connection.createStatement();									//Statementを取得するためのコード

	        //自動コミットを有効にする
	        connection.setAutoCommit(true);
	        //コメントの追加
	        String insertSql = "INSERT INTO t_inquiry (user_id, inquiry, create_date, update_date, create_user, update_user)"
	        		+ " VALUES ('" + loginUser + "','" + inquiry + "', current_timestamp, current_timestamp, '" + loginUser + "', '" + loginUser +"')";
	        System.out.println("W0080M :" + insertSql);
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

