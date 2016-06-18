package co.wiss1.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccessUtils {

	public static Connection getConnection() {
		String driver   = "org.postgresql.Driver";
		String host     = "localhost";
		String port     = "5432";
		String dbname   = "postgres";
		String user     = "postgres";
		String password = "postgres";
		String url      = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		Connection con  = null;
		try {
			Class.forName (driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
