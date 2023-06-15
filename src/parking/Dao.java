package parking;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {
	
	static String url = "jdbc:oracle:thin:@localhost:1521/xe";
	static String user = "park";
	static String pass = "park";
	static Connection conn;
	
	public static Connection getConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
