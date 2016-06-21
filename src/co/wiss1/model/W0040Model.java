package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0040Model {

	public static List<HashMap<String, String>> getCommentList(String Id) {
				System.out.println("引数は" + Id + "になります!!");
		// コメント一覧を格納する箱
			List<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();
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
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT p.post,p.user_name,p.post_id,c.category_name "
						+ "FROM t_post p LEFT OUTER JOIN t_category c "
						+ "ON c.category_id = p.category_id "
						+ "WHERE p.category_id = '"+ Id +"' AND p.delete_flg = 't'"
						+ "ORDER BY p.post_id	");

			// SQL文実行
				resultSet = statement.executeQuery(sb.toString());
			// 実行結果の取得
			while(resultSet.next()) {
				HashMap<String, String> commentInfo = new HashMap<String, String>();
				commentInfo.put("CategoryName", resultSet.getString("category_name"));
				commentInfo.put("commentId", resultSet.getString("post_id"));
				commentInfo.put("userName", resultSet.getString("user_name"));
				commentInfo.put("comment", resultSet.getString("post"));
				commentInfo.put("Id", resultSet.getString("post_id"));
				commentList.add(commentInfo);
				System.out.println(commentInfo.get("commentId"));
				System.out.println(commentInfo.get("userName"));
				System.out.println(commentInfo.get("comment"));

			}
		} catch (SQLException e) {
			System.out.println("SQL実行処理失敗!!");
			e.printStackTrace();
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
		return commentList;
	}


public static int updatecomment(String[] checkBox ) {	 												//コメント削除

	//List<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();
	ResultSet resultSet = null;
	Connection connection = null;
	Statement statement = null;
	int updateCount = 0;

	try
    {
        // コメント一覧照会実行
    	connection = DBAccessUtils.getConnection();													//DBへ接続
    	statement = connection.createStatement();													//Statementを取得するためのコード

        connection.setAutoCommit(true);							 								//自動コミットを有効にする

        String sql = "UPDATE t_post SET delete_flg = TRUE WHERE (post_id IN( + checkBox + )";

        System.out.println("引数に" + checkBox + "が入力されました。");
        System.out.println(sql);

        statement.executeUpdate (sql);
    }
	catch (SQLException e){
	System.err.println("SQL failed.");
	e.printStackTrace ();																			//エラーの情報
	}
	finally{
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
return updateCount;
}



public static int insertComment(String comment) {

				Connection connection = null;
				Statement statement = null;
				int insertCount = 0;

			try
		{
			 // コメント一覧照会実行
				connection = DBAccessUtils.getConnection();													//DBへ接続
				statement = connection.createStatement();													//Statementを取得するためのコード

	        //自動コミットを有効にする
	        	connection.setAutoCommit(true);
	        	//コメントの追加
	        	 String insertSql = "INSERT INTO t_post(post)"
	             + "VALUES('" + comment + "')";
	        	 System.out.println("1:" + insertSql);
	             insertCount = statement.executeUpdate(insertSql);

	             System.out.println("引数に" + comment + "が入力されました。");

	             System.out.println(insertSql);


	        //影響のあった行数を出力
	             System.out.println(insertCount + " 行挿入しました。");
				}
		 catch (SQLException e)
	         {
			System.err.println("SQL failed.");
			e.printStackTrace ();
	         }
		 finally{
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
