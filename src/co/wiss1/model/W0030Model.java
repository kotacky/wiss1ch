package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import co.wiss1.common.DBAccessUtils;

public class W0030Model {

	public static void main(String args[]){
	    //insertcategory_name("音楽");
	}


	public static int getOverlapCount(String categoryName) {

        //初期化

	        // SQL実行結果格納用Set
			ResultSet resultSet = null;
			// DB接続コネクション
			Connection connection = null;
			// SQLステートメント
			Statement statement = null;

		    int getOverlapCount = 0;

		        try {
		            // テーブル照会実行
		        	connection = DBAccessUtils.getConnection();//ＤＢに接続
		        	statement = connection.createStatement();//ＳＱＬ文をＤＢに送信

		        	//自動コミットを有効にする
		        	connection.setAutoCommit(true);

		        	//重複チェック
		        	String selectSql="SELECT COUNT(category_name) FROM t_category";
		        	System.out.println("1:" + selectSql);

		        	selectSql = selectSql
	             			+ "WHERE category_name = ('" + categoryName + "')";

		        	System.out.println("引数に" + categoryName + "が入力されました。");
	             	resultSet = statement.executeQuery(selectSql);
	             	getOverlapCount = resultSet.getInt("COUNT(category_name)");

		         }
		         catch (SQLException e){

		               System.err.println("SQL failed.");
		               e.printStackTrace ();
		         }
		         finally {
		 	        //データベースのクローズ
		         	try {
		                 statement.close();
		                 connection.close();
		         	}
		         	catch (Exception e){
		         		System.out.println("Close failed.");
		         	}
		         	System.out.println("一覧取得処理終了");
		         }
		         return getOverlapCount;
	}

	public static int insertCategory(String categoryName) {

        //初期化

               	// DB接続コネクション
				Connection connection = null;
				// SQLステートメント
				Statement statement = null;

		    	int insertCount = 0;


		        try {
		        	// テーブル照会実行
		        	connection = DBAccessUtils.getConnection();//ＤＢに接続
		        	statement = connection.createStatement();//ＳＱＬ文をＤＢに送信

		        	//自動コミットを有効にする
		        	connection.setAutoCommit(true);

		        	//カテゴリ名の追加
		        	String insertSql = "INSERT INTO t_category (category_name)"
			        + " VALUES ('" + categoryName + "')";
		            //System.out.println("1:" + insertSql );

		      	    insertCount = statement.executeUpdate(insertSql);
		            System.out.println(insertCount + " 行挿入しました。");

		            //どのようなSQL文が入っているか出力
		            System.out.println ("次のsqlを実行します" + insertSql);

		            //影響のあった行数を出力
		            insertCount = statement.executeUpdate(insertSql);
		            //executeUpdate:桁数(挿入)

		         }
		         catch (SQLException e) {

		               System.err.println("SQL failed.");
		               e.printStackTrace ();
		         }
		         finally {
		 		     //データベースのクローズ
		         	 try {
		                 statement.close();
		                 connection.close();
		         	 }
		         	 catch (Exception e){
		         	     System.out.println("Close failed.");
		         	 }
		         	 System.out.println("一覧取得処理終了");
		         }
		         return insertCount;
	}
}
