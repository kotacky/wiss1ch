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

	//コメント一覧の取得
	public static List<HashMap<String, String>> getCommentList(String Id) {
		System.out.println("カテゴリIDは" + Id + "になります!!");
		// コメント一覧を格納する二次元文字列配列
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
				//コメント、投稿者名、コメントID、ユーザID、カテゴリ名[、画像バイナリ、いいね数]
				sb.append("SELECT p.post,p.user_name,p.post_id,p.user_id,p.create_date,c.category_name "
						// + ", p.img_bin, p.good_count "
						+ "FROM t_post p LEFT OUTER JOIN t_category c "
						+ "ON c.category_id = p.category_id "
						+ "WHERE p.category_id = '"+ Id +"' AND p.delete_flg = 'f'"
						+ "ORDER BY p.post_id	");
				System.out.println(sb);
			// SQL文実行
				resultSet = statement.executeQuery(sb.toString());
			// 実行結果の取得
			while(resultSet.next()) {
				//Idはpost_idを獲得
				HashMap<String, String> commentInfo = new HashMap<String, String>();
				commentInfo.put("CategoryName", resultSet.getString("category_name"));
				commentInfo.put("commentId", resultSet.getString("post_id"));
				//v1.1 CreateDateを取得
				commentInfo.put("PostTime", resultSet.getString("create_date"));
				commentInfo.put("userName", resultSet.getString("user_name"));
				commentInfo.put("comment", resultSet.getString("post"));
				commentInfo.put("Id", resultSet.getString("post_id"));
				commentInfo.put("userId", resultSet.getString("user_id"));
				//commentInfo.put("img_bin", resultSet.getString("img_bin"));
				//commentInfo.put("good_count", resultSet.getString("good_count"));
				commentList.add(commentInfo);
				System.out.println("コメントIdは" + commentInfo.get("commentId") + "です" );
				//System.out.println("ユーザ名は" + commentInfo.get("userName") + "です" );
				//System.out.println("コメントは" + commentInfo.get("comment") + "です" );
				//System.out.println("ユーザIdは" + commentInfo.get("userId") + "です" );
			}
		} catch (SQLException e) {
			System.out.println("リスト取得SQL実行処理失敗!!");
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
		//作ったコメントリストを返す
		return commentList;
	}


	//いいね
	public static int goodComment(String CommentId, String userId) {
		//いいねが押されたときのSQL操作
		Connection connection = null;
		Statement statement = null;
		int goodCount = 0;

		try{
			//コメント一覧照会を実行
			//DB接続
			connection = DBAccessUtils.getConnection();
			statement = connection.createStatement();
			connection.setAutoCommit(true);

			//実行するSQL
			//コメントIDに対応するもののgood_countを増加
			String sql = "UPDATE t_post SET good_count=good_count+1, update_date=current_timestamp, update_user='"+ userId +"' WHERE post_id = " + CommentId;
			System.out.println("goodComment:SQL実行");
			System.out.println(sql);

			//SQL実行
			goodCount = statement.executeUpdate (sql);
		}catch (SQLException e){
			//SQL失敗
			System.err.println("いいねSQL failed.");
			e.printStackTrace ();																		//エラーの情報
		}finally{
			try{
				// もろもろクローズ処理
				statement.close();
				connection.close();
			}catch (Exception e){
				//クローズエラー
				System.err.println("クローズ処理失敗!!");
				e.printStackTrace ();
			}
		}

		return goodCount;
	}


	//コメントの論理削除(まとめて)
	public static int updateComment(String checkBox[] ) {
		Connection connection = null;
		Statement statement = null;
		int updateCount = 0;

		try{
			// コメント一覧照会実行
			//DBへ接続
			connection = DBAccessUtils.getConnection();
			statement = connection.createStatement();
			connection.setAutoCommit(true);

			//ループ処理!checkBox[ ]分処理する。
			for( int i = 0; i < checkBox.length; i++) {
				System.out.println("CheckBoxに" + checkBox[i] + "が入力されました!!");
				String sql = "UPDATE t_post SET delete_flg = TRUE WHERE post_id = '"+ checkBox[i] + "'";
				System.out.println("checkBoxに!" + checkBox + "が入力されました!!");
				System.out.println(sql);
				updateCount = statement.executeUpdate (sql);
			}
		}catch (SQLException e){
			System.err.println("削除SQL failed.");
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


	//コメント削除(単独)
	public static int updateSoloComment(String CommentId, String userId) {

		Connection connection = null;
		Statement statement = null;
		int updateCount = 0;

		try{
			//DB接続
			connection = DBAccessUtils.getConnection();
			statement = connection.createStatement();
			connection.setAutoCommit(true);

			//単独削除
			String sql = "UPDATE t_post SET delete_flg = TRUE, update_date = current_timestamp, update_user = '"+ userId +"' WHERE post_id = '"+ CommentId + "'";
			System.out.println("");
			System.out.println(sql);
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


	//コメント投稿(旧)
	public static int insertComment(String comment, String categoryId,String userId,String userName) {
		Connection connection = null;
		Statement statement = null;
		int insertCount = 0;
		System.out.println("W40M 引数は"+ categoryId +"です" );

		try{
			// コメント一覧照会実行
			connection = DBAccessUtils.getConnection();													//DBへ接続
			statement = connection.createStatement();													//Statementを取得するためのコード

	        //自動コミットを有効にする
	        connection.setAutoCommit(true);
	        //コメントの追加
	        String insertSql = "INSERT INTO t_post(post,category_id,user_name,delete_flg,user_id,create_date,create_user,update_date,update_user)"
	        		+ " VALUES('" + comment + "','"+ categoryId +"','"+ userName +"',FALSE,'"+ userId +"',current_timestamp,'"+ userId +"',current_timestamp,'"+ userId +"')";
	        System.out.println("1:" + insertSql);
	        insertCount = statement.executeUpdate(insertSql);

	        System.out.println("W40M 引数に" + comment + "が入力されました。");
	        System.out.println(insertSql);

	        //影響のあった行数を出力
	        System.out.println(insertCount + " 行挿入しました。");
		}catch (SQLException e){
			System.err.println("投稿SQL failed.");
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


	//コメント投稿(新) 必要データ：コメント、カテID、ユーザID、ユーザ名、イメージバイナリ、色情報
	public static int insertCommentAddImg(String comment, String categoryId, String userId, String userName, byte[] Img, int color) {

		Connection connection = null;
		Statement statement = null;
		int insertCount = 0;
		System.out.println("W40M categoryID引数は"+ categoryId +"です" );

		try{
			// コメント一覧照会実行
			connection = DBAccessUtils.getConnection();													//DBへ接続
			statement = connection.createStatement();													//Statementを取得するためのコード

			//自動コミットを有効にする
			connection.setAutoCommit(true);

			//コメント追加SQL作成
			String insertSql = "INSERT INTO t_post(post,category_id,user_name,delete_flg,user_id,create_date,create_user,update_date,update_user,img_bin,font_color)"
					+ " VALUES('" + comment + "','"+ categoryId +"','"+ userName +"',FALSE,'"+ userId +"',current_timestamp,'"+ userId +"',current_timestamp,'"+ userId +"',"+ Img + "," + color + ")" ;
			System.out.println("insertCommentAddImgSQL:" + insertSql);

			//SQLの実行
			insertCount = statement.executeUpdate(insertSql);

			//影響のあった行数を出力
			System.out.println(insertCount + " 行挿入しました。");
		}catch (SQLException e){
			System.err.println("投稿SQL failed.");
			e.printStackTrace ();
		}finally{
			try {
				// もろもろクローズ処理
				statement.close();
				connection.close();
			}catch (Exception e){
				System.err.println("クローズ処理失敗!!");
				e.printStackTrace ();
			}
		}
		return insertCount;
	}
}