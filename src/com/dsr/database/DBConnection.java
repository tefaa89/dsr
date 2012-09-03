package com.dsr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.dsr.configuration.DBConfig;
import com.dsr.util.Trace;

public class DBConnection {
	private static Connection connection = null;

	public static Connection connect() {
		Trace.trace("Connecting to Database on " + DBConfig.DB_CONNECTION);
		if (DBConnection.connection == null)
			try {
				Class.forName("com.mysql.jdbc.Driver");
				DBConnection.connection = DriverManager.getConnection(DBConfig.DB_CONNECTION,
						DBConfig.DB_USERNAME, DBConfig.DB_PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return DBConnection.connection;
	}
	
	public static boolean isConnected()
	{
		boolean isConn = false;
		try {
			isConn = connection == null ? false: (connection.isValid(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isConn;
	}
}
