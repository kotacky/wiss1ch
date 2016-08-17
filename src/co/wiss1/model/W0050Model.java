package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0050Model {

	public static void main (String args[]) {
        List<HashMap<String, String>> userList = getUserList();//22行目に飛ぶ
        for (HashMap<String, String> userInfo : userList) {
            System.out.println("ユーザID:[" + userInfo.get("userId") + "] ユーザ名:[" + userInfo.get("userName") + "]");
        }
    }

	public static List<HashMap<String, String>> getUserList() {

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
			StringBuffer sb = new StringBuffer();//一時的に文字を保管する
			sb.append("SELECT user_id, user_name, user_address, admin_flg FROM t_user_info WHERE delete_flg = 'FALSE' ORDER BY user_id");//sbの箱SELECT * FROM t_category WHERE delete_flg = 'FALSE' ORDER BY category_idにいれる
			// SQL文実行
			resultSet = statement.executeQuery(sb.toString());//resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
			// 実行結果の取得・次の行を呼ぶ
			while(resultSet.next()) {
				HashMap<String, String> userInfo = new HashMap<String, String>();//カテゴリインフォを新しく作る
				userInfo.put("userId", resultSet.getString("user_id"));//カテゴリー
				userInfo.put("userName", resultSet.getString("user_name"));
				userInfo.put("userAddress", resultSet.getString("user_address"));
				userInfo.put("userAdmin", resultSet.getString("admin_flg"));
				userList.add(userInfo);
				System.out.println(userInfo.get("userId"));
				System.out.println(userInfo.get("userName"));
				System.out.println(userInfo.get("userAddress"));
				System.out.println(userInfo.get("userAdmin"));
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



	public static int updateUser(String checkBox[]) {	 										//カテゴリ削除

		Connection connection = null;
		Statement statement = null;
		int UpdateCount = 0;

		try
        {
            // ユーザ一覧照会実行
        	connection = DBAccessUtils.getConnection();												//DBへ接続
        	statement = connection.createStatement();												//Statementを取得するためのコード

            connection.setAutoCommit(true);							 								//自動コミットを有効にする
            for (int i = 0; i < checkBox.length; i++ ) {
            String sql = "UPDATE t_user_info SET delete_flg = 'TRUE' WHERE user_id =  '"+ checkBox[i] +"'";

            	System.out.println("checkBoxに" + checkBox[i] + "が入力されました。");
            	UpdateCount = statement.executeUpdate (sql);
            	System.out.println(sql);
            	if(UpdateCount >= 1){																	//削除が成功しているかどうかの確認
                	System.out.println("削除成功");
                }
                else{
                	System.out.println("削除失敗");
                }
            }
        }
		catch (SQLException e){
		System.err.println("ユーザ削除のSQL failed.");
		e.printStackTrace ();																		//エラーの情報
		}
		finally{
		}
	return UpdateCount;
	}

}