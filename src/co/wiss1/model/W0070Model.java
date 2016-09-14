package co.wiss1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wiss1.common.DBAccessUtils;

public class W0070Model {

	public static List<HashMap<String, String>> getInqList() {
		System.out.println("W0070 getinquiryList ()");

		// ユーザ一覧を格納する箱
		List<HashMap<String, String>> inquiryList = new ArrayList<HashMap<String, String>>();
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
			sb.append("SELECT i.inquiry_id, i.inquiry, i.user_id, i.create_date, u.user_name, u.user_mail "
					+ "FROM t_inquiry i INNER JOIN t_user_info u ON i.user_id = u.user_id "
					+ "WHERE i.delete_flg = FALSE "
					+ "ORDER BY i.inquiry_id DESC "); //問い合わせを降順で保管する
			// SQL文実行
			System.out.println("W0070M ," + sb.toString());
			resultSet = statement.executeQuery(sb.toString());//resultSet実行した結executeQuery＝要求をＳＱＬとしてＤＢに投げる
			// 実行結果の取得・次の行を呼ぶ
			while(resultSet.next()) {
				HashMap<String, String> inquiryInfo = new HashMap<String, String>();
				inquiryInfo.put("inquiryId", resultSet.getString("inquiry_id"));
				inquiryInfo.put("PostTime", resultSet.getString("create_date"));
				inquiryInfo.put("userName", resultSet.getString("user_name"));
				inquiryInfo.put("inquiry", resultSet.getString("inquiry"));
				inquiryInfo.put("userId", resultSet.getString("user_id"));
				inquiryInfo.put("userMail", resultSet.getString("user_mail"));
				inquiryList.add(inquiryInfo);
				System.out.println("問い合わせIDは" + inquiryInfo.get("inquiryId") + "です");
//				System.out.println(inquiryInfo.get("userName"));
//				System.out.println(inquiryInfo.get("userAddress"));
//				System.out.println(inquiryInfo.get("userMail"));
//				System.out.println(inquiryInfo.get("userAdmin"));
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
		return inquiryList;
	}


	//コメント削除(単独)
	public static int updateSoloInq(String inquiryId, String userId) {

		Connection connection = null;
		Statement statement = null;
		int updateCount = 0;

		try{
			//DB接続
			connection = DBAccessUtils.getConnection();
			statement = connection.createStatement();
			connection.setAutoCommit(true);

			//単独削除
			String sql = "UPDATE t_inquiry SET delete_flg = TRUE, update_date = current_timestamp, update_user = '"+ userId +"' WHERE inquiry_id = '"+ inquiryId + "'";
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

	//一括削除
	public static int updateInq(String checkBox[]) {

		Connection connection = null;
		Statement statement = null;
		int UpdateCount = 0;

		try

        {
            // 問い合わせ一覧照会実行
        	connection = DBAccessUtils.getConnection();												//DBへ接続
        	statement = connection.createStatement();												//Statementを取得するためのコード

            connection.setAutoCommit(true);							 								//自動コミットを有効にする
            for (int i = 0; i < checkBox.length; i++ ) {
            String sql = "UPDATE t_inquiry SET delete_flg = 'TRUE' WHERE inquiry_id =  '"+ checkBox[i] +"'";

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