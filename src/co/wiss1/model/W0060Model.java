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

	public static void main(String args[]){
	                   					  }


	//登録時　入力された新規ユーザーの情報をDBへ登録
	public static int insertCount(String User_name,String user_Id,String pass_word,String User_address) {

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

		        	System.out.println("userIdに" + user_Id + "が入力されている。");

		        	//ユーザー情報の追加
		        	String insertSql = "INSERT INTO t_user_info (user_name,user_id,password,user_address,delete_flg,create_date,create_user,update_date,update_user)"
		        			+ " VALUES (('" + User_name + "'),('" + user_Id +"'),('" + pass_word + "'),('" + User_address + "'),FALSE,current_timestamp,('" + user_Id + "'),current_timestamp,('" + user_Id +"'))";
		            //System.out.println("1:" + insertSql );

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

    //ユーザー変更時の処理 IDからユーザー情報（ID、名前、住所、文字色）取得
	public static List<HashMap<String, String>> getuserinfo(String user_Id) {
		System.out.println("ユーザーIDは" +user_Id + "になります!!");
// コメント一覧を格納する箱
	List<HashMap<String, String>> getuserinfolist = new ArrayList<HashMap<String, String>>();
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
		sb.append("SELECT user_id,user_name,password,font_color"
				+ "FROM t_user_info"
				+ "WHERE user_id = '"+ user_Id +"' AND p.delete_flg = 'f'"
				);
		System.out.println(sb);
	// SQL文実行
		resultSet = statement.executeQuery(sb.toString());	    }
		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
	    	System.out.println("ユーザー情報取得失敗");
			e.printStackTrace();
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


		return getuserinfolist;


	    }


//ユーザー変更時の処理　ユーザーが入力した変更情報をDBへ送る
public static int user_update (String user_name,String user_address,String password,String fontcolor,String pass_word){
	 	//初期化

    	// SQL実行結果格納用Set
		ResultSet resultSet = null;
		// DB接続コネクション
		Connection connection = null;
		// SQLステートメント
		Statement statement = null;

		int getuser_id = 0;

        	try {
        		// テーブル照会実行
        		connection = DBAccessUtils.getConnection();//ＤＢに接続
        		statement = connection.createStatement();//ＳＱＬ文をＤＢに送信

        		//自動コミットを有効にする
        		connection.setAutoCommit(true);

        		//ユーザー変更情報をDBへ送る。
        		String update_userinfo_SQL = "Update t_user_info" + "Set user_name = User_name, user_address = User_address, update_date=current_timestamp, update_user=user_id, password = pass_word, font_color = Font_color" + "Where user_ID = userid";
        		System.out.println("1:" + update_userinfo_SQL);
        		getuser_id = statement.executeUpdate(update_userinfo_SQL);
	            System.out.println(getuser_id + " 行挿入しました。");

	            //どのようなSQL文が入っているか出力
	            System.out.println ("次のsqlを実行します" + update_userinfo_SQL);


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
        	return getuser_id;
}
        	}