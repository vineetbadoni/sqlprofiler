package com.ca.ms.sql.profiler.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionsUtil {

	public static Connection getTargetDBConnection()
			throws ClassNotFoundException, SQLException {
		Class.forName(PropertiesUtil.getConnectionProperty("driverClass"));
		Connection conn = java.sql.DriverManager.getConnection(
				getConnectionURL(),
				PropertiesUtil.getConnectionProperty("username"),
				PropertiesUtil.getConnectionProperty("password"));
		return conn;
	}

	public static String getConnectionURL() {
		return PropertiesUtil.getConnectionProperty("databaseURL",
				new String[] {
						PropertiesUtil.getConnectionProperty("hostname"),
						PropertiesUtil.getConnectionProperty("port"),
						PropertiesUtil.getConnectionProperty("databaseName") });
	}

	public static void dropConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
	}
}
