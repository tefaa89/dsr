package com.dsr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.dsr.configuration.Config;
import com.dsr.util.Trace;

public class DBConnection {
	private static Connection connection = null;
	private static boolean connectToDBTable = false;

	public static Connection connect() {
		if (DBConnection.connection == null || connectToDBTable)
			try {
				String dbURL = Config.getMySqlURL() + "/document_system_recognition";
				Class.forName("com.mysql.jdbc.Driver");
				DBConnection.connection = DriverManager.getConnection(dbURL,
						Config.getDBUsername(), Config.getDBPassword());
				connectToDBTable = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return connection;
	}

	public static boolean checkConnection() {
		try {
			String dbURL = Config.getMySqlURL();
			Trace.trace("Database: Connecting to MySql ...");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dbURL, Config.getDBUsername(),
					Config.getDBPassword());
			Trace.trace("Database : Connected Successfully ...");
		} catch (Exception e) {
			String cause = e.getCause() + "";
			if (!cause.equals("null"))
				Trace.trace("MySql Error: " + cause);
			else
				Trace.trace("MySql Error: " + e);
			return false;
		}
		return true;
	}

	public static void setConnectToDBTable(boolean connectToDBTable) {
		DBConnection.connectToDBTable = connectToDBTable;
	}

	public static boolean isConnected() {
		boolean isConn = false;
		try {
			isConn = connection == null ? false : (connection.isValid(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isConn;
	}
}
