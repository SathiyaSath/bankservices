package org.in.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionDAO {
	public static Connection getConnection() throws Exception {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/bankservices";
			String username = "root";
			String passward = "root";
			connection = DriverManager.getConnection(url, username, passward);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return connection;
	}

}
