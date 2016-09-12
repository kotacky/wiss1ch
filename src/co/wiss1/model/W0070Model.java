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

public class W0070Model {

	//問い合わせ一覧の取得
		public static List<HashMap<String, String>> getInquiryList(String Id) {
			// 問い合わせ一覧を格納する二次元文字列配列
				List<HashMap<String, String>> inquiryList = new ArrayList<HashMap<String, String>>();
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
						//コメント、投稿者名、コメントID、ユーザID、カテゴリ名、いいね数、画像バイナリ
						sb.append("SELECT i.inquiry_id, i.inquiry, i.user_id, i.create_date, u.user_name, u.user_mail "
								+ "FROM t_inquiry i INNER JOIN t_user_info u ON i.user_id = u.user_id "
								+ "WHERE i.delete_flg = FALSE "
								+ "ORDER BY i.inquiry_id DESC ");
						System.out.println("W0070M getInquiryList:" + sb.toString());
					// SQL文実行
						resultSet = statement.executeQuery(sb.toString());

					// 実行結果の取得
					while(resultSet.next()) {
						//Idはpost_idを獲得
						HashMap<String, String> inquiryInfo = new HashMap<String, String>();
						inquiryInfo.put("inquiryId", resultSet.getString("inquiry_id"));
						inquiryInfo.put("PostTime", resultSet.getString("create_date"));
						inquiryInfo.put("userName", resultSet.getString("user_name"));
						inquiryInfo.put("inquiry", resultSet.getString("inquiry"));
						inquiryInfo.put("userId", resultSet.getString("user_id"));
						inquiryInfo.put("userMail", resultSet.getString("user_mail"));

						//問い合わせリストへ問い合わせインフォを送る
						inquiryList.add(inquiryInfo);
						System.out.println("問い合わせIdは" + inquiryInfo.get("inquiryId") + "です" );
						//System.out.println("ユーザ名は" + inquiryInfo.get("userName") + "です" );
						//System.out.println("問い合わせ内容は" + inquiryInfo.get("inquiry") + "です" );
						//System.out.println("ユーザIdは" + inquiryInfo.get("userId") + "です" );

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
				return inquiryList;
		}

		//コメントの論理削除(まとめて)
		public static int updateInquiry(String checkBox[] ) {
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
		public static int updateSoloInquiry(String CommentId, String userId) {

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


}