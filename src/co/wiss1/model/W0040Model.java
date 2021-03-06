package co.wiss1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import co.wiss1.common.DBAccessUtils;

public class W0040Model {

    //コメント一覧の取得
    public static List<HashMap<String, String>> getCommentList(String categoryId) {
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
                //コメント、投稿者名、コメントID、ユーザID、カテゴリ名、いいね数、画像バイナリ
                sb.append("SELECT p.post, p.user_name, p.post_id, p.user_id, p.create_date, c.category_name, p.good_count, p.font_color "
                        + "FROM t_post p LEFT OUTER JOIN t_category c "
                        + "ON c.category_id = p.category_id "
                        + "WHERE p.category_id = '"+ categoryId +"' AND p.delete_flg = 'f' "
                        + "ORDER BY p.post_id	");
                System.out.println("W0040M getCommentList:" + sb.toString());
            // SQL文実行
                resultSet = statement.executeQuery(sb.toString());

                String color;
                String colorcode = null;
            // 実行結果の取得
            while(resultSet.next()) {
                //Idはpost_idを獲得
                HashMap<String, String> commentInfo = new HashMap<String, String>();
                commentInfo.put("categoryName", resultSet.getString("category_name"));
                commentInfo.put("commentId", resultSet.getString("post_id"));
                //v1.1 CreateDateを取得
                commentInfo.put("PostTime", resultSet.getString("create_date"));
                commentInfo.put("userName", resultSet.getString("user_name"));
                commentInfo.put("comment", resultSet.getString("post"));
                commentInfo.put("userId", resultSet.getString("user_id"));
                commentInfo.put("good_count", resultSet.getString("good_count"));

                //文字色の実装
                color = resultSet.getString("font_color");

                switch(color){
                    case "1":
                        colorcode = "#000000";
                        break;
                    case "2":
                        colorcode = "#ff0000";
                        break;
                    case "3":
                        colorcode = "#0000ff";
                        break;
                    case "4":
                        colorcode = "#008000";
                        break;
                    case "5":
                        colorcode = "#ffcc00";
                        break;
                    case "6":
                        colorcode = "#ffa500";
                        break;
                    case "7":
                        colorcode = "#800080";
                        break;
                    case "8":
                        colorcode = "#adff2f";
                        break;
                    case "9":
                        colorcode = "#87ceeb";
                        break;
                    case "10":
                        colorcode = "#000080";
                        break;
                    case "11":
                        colorcode = "#a52a2a";
                        break;
                    case "12":
                        colorcode = "#d2691e";
                        break;
                }

                commentInfo.put("font_color", colorcode);
                //コメントリストへコメントインフォを送る
                commentList.add(commentInfo);
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

    //画像データ一覧の取得
    public static HashMap<String, String> getImgList(String categoryId) {
    // 画像一覧を格納する二次元文字列配列
        HashMap<String, String> imgInfo = new HashMap<String, String>();
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
        //画像バイナリだけ
            sb.append("SELECT p.post_id, p.img_bin "
                    + "FROM t_post p LEFT OUTER JOIN t_category c "
                    + "ON c.category_id = p.category_id "
                    + "WHERE p.category_id = '"+ categoryId +"' AND p.delete_flg = 'f' "
                    + "ORDER BY p.post_id	");
            System.out.println("W0040M getImgList:" + sb.toString());
        // SQL文実行
            resultSet = statement.executeQuery(sb.toString());

        // 実行結果の取得
            while(resultSet.next()) {
                //画像の取得とImgInfoへのput
                byte[] Imgbyte = resultSet.getBytes("img_bin");

                //Base64エンコード
                String img = Base64.encode(Imgbyte);
                String commentId = resultSet.getString("post_id");
                imgInfo.put(commentId, img);
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
        return imgInfo;
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
                String sql = "UPDATE t_post SET delete_flg = TRUE WHERE post_id = '"+ checkBox[i] + "'";
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
    public static int insertComment(String comment, String categoryId,String userId,String userName, String color) {
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
            String insertSql = "INSERT INTO t_post(post,category_id,user_name,delete_flg,user_id,create_date,create_user,update_date,update_user,font_color)"
                             + " VALUES ('" + comment + "','"+ categoryId +"','"+ userName +"',FALSE,'"+ userId +"',current_timestamp,'"+ userId +"',current_timestamp,'"+ userId +"','"+ color + "')";
            System.out.println("W0040M : INSERT");
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


    //コメント投稿(新) 必要データ：コメント、カテID、ユーザID、ユーザ名、色情報[イメージバイナリは後で]
    public static int insertCommentAddImg(String comment, String categoryId, String userId, String userName, byte[] Img, String color) {

        Connection connection = null;
        Statement statement = null;
        int insertCount = 0;

        try{
            // コメント一覧照会実行
            connection = DBAccessUtils.getConnection();						//DBへ接続
            statement = connection.createStatement();						//Statementを取得するためのコード

            //自動コミットを有効にする
            connection.setAutoCommit(true);

            //コメント追加SQL作成
            String insertSql = "INSERT INTO t_post (post,category_id,user_name,delete_flg,user_id,create_date,create_user,update_date,update_user,font_color";
            String insertSqlEnd = ")";
            String insertValues = " VALUES('" + comment + "','"+ categoryId +"','"+ userName +"',FALSE,'"+ userId +"',current_timestamp,'"+ userId +"',current_timestamp,'"+ userId +"','"+ color +"'";
            String preSQL = insertSql + insertSqlEnd + insertValues + insertSqlEnd;
            String statementSQL = insertSql + ",img_bin" + insertSqlEnd + insertValues + ", ?" + insertSqlEnd;
            //パラメタ付SQL文の確認
            System.out.println("insertCommentAddImgSQL: " + statementSQL );

            //パラメタ付SQL文の作成
            PreparedStatement ps = connection.prepareStatement(insertSql + ",img_bin" + insertSqlEnd + insertValues + ", ?" + insertSqlEnd);
            ps.setBytes(1, Img);

            //SQLの実行
            insertCount = ps.executeUpdate();
            //insertCount = statement.executeUpdate(preSQL);

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