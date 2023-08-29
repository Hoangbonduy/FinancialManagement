package javaUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     public static Connection getConnection() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://sql6.freesqldatabase.com:3306/sql6641047";
			String username = "sql6641047";
			String password = "NcEf1XEd1l";
			
			connection = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return connection;
	}
}