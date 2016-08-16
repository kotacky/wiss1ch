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
		        	String selectSql="SELECT COUNT(category_name) as cnt FROM t_category WHERE category_name = ('" + categoryName + "') AND delete_flg = FALSE";
		        	System.out.println("1:" + selectSql);



		        	System.out.println("引数に" + categoryName + "が入力されました。");
	             	resultSet = statement.executeQuery(selectSql);
	             	System.out.println("resultSetに" + resultSet + "が入力されました。");
	             	resultSet.next();
	             	getOverlapCount = resultSet.getInt("cnt");
		        	System.out.println("getOverlapCountに" + getOverlapCount + "が入力されました。");

		            }
		         catch (SQLException e){

		               System.err.println("重複SQL failed.");
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

	public static int insertCategory(String categoryName,String userId) {

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

		        	System.out.println("userIdに" + userId + "が入力されている。");


		        	if (categoryid != null){

		        	//カテゴリ名の追加
		        	String insertSql = "INSERT INTO t_category (category_name,delete_flg,create_date,create_user,update_date,update_user)"
		        			+ " VALUES ('" + categoryName + "',FALSE,current_timestamp,('" + userId +"'),current_timestamp,('" + userId +"'))";
		            //System.out.println("1:" + insertSql );
		        	}else{
		        	String insertSql = "UPDATE t_category SET  category_name =('" + categoryName + "') , update_date = current_timestamp , update_user =('" + userId +"')  WHERE  category_id = ('"+ checkBox +"')"
		        	}
		      	    insertCount = statement.executeUpdate(insertSql);
		            System.out.println(insertCount + " 行挿入しました。");

		            //どのようなSQL文が入っているか出力
		            System.out.println ("次のsqlを実行します" + insertSql);


		         	 }
		         catch (SQLException e) {

		               System.err.println("追加SQL failed.");
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

