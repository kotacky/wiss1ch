package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0020Model {

	public static void main (String args[]) {
        List<HashMap<String, String>> categoryList = getCategoryList();
        for (HashMap<String, String> categoryInfo : categoryList) {
            System.out.println("カテゴリID:[" + categoryInfo.get("categoryId") + "] カテゴリ名:[" + categoryInfo.get("categoryName") + "]");
        }
    }



	public static List<HashMap<String, String>> getCategoryList() {

		// カテゴリ一覧を格納する箱
		List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
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
			sb.append("SELECT * FROM t_category WHERE delete_flg = 'FALSE' ORDER BY category_id");
			// SQL文実行
			resultSet = statement.executeQuery(sb.toString());
			// 実行結果の取得
			while(resultSet.next()) {
				HashMap<String, String> categoryInfo = new HashMap<String, String>();
				categoryInfo.put("categoryId", resultSet.getString("category_id"));
				categoryInfo.put("categoryName", resultSet.getString("category_name"));
				categoryList.add(categoryInfo);
				System.out.println(categoryInfo.get("categoryId"));
				System.out.println(categoryInfo.get("categoryName"));
			}
		} catch (SQLException e) {
			System.out.println("カテゴリ一覧SQL実行処理失敗!!");
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
		return categoryList;
	}



	public static int updateCategory(String checkBox[]) {	 										//カテゴリ削除

		//List<HashMap<String, String>> categoryList = categoryList<HashMap<String, String>>() ;
		//ResultSet resultSet = null;
		Connection connection = null;
		Statement statement = null;
		int UpdateCount = 0;

		try
        {
            // カテゴリ一覧照会実行
        	connection = DBAccessUtils.getConnection();												//DBへ接続
        	statement = connection.createStatement();												//Statementを取得するためのコード

            connection.setAutoCommit(true);							 								//自動コミットを有効にする
            for (int i = 0; i < checkBox.length; i++ ) {
            String sql = "UPDATE t_category SET delete_flg = 'TRUE' WHERE category_id =  '"+ checkBox[i] +"'";

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
		System.err.println("カテゴリ削除のSQL failed.");
		e.printStackTrace ();																		//エラーの情報
		}
		finally{
		}
	return UpdateCount;
	}

}
