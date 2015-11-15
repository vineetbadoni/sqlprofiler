package com.ca.ms.sql.profiler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.ca.logger.BaseLogger;
import com.ca.ms.sql.profiler.util.DBConnectionsUtil;
import com.ca.ms.sql.profiler.util.PropertiesUtil;

public class KillQuery {
	
	private static BaseLogger logger = BaseLogger.getInstance(KillQuery.class);
	
	private static Connection targetDBConn = null;

	public static void main(String[] args) {
		try {
			targetDBConn = DBConnectionsUtil.getTargetDBConnection();
			killHangingQuery(String.valueOf(55));
			logger.info("Successfully killed the blocking query");
		} catch (Exception e) {
			logger.error("Error occured while executing kill query command",e);
		} finally {
			DBConnectionsUtil.dropConnection(targetDBConn);
		}
	}

	private static void killHangingQuery(String SPID) throws SQLException {
		Statement statement = targetDBConn.createStatement();
		boolean isKilled = statement.execute(PropertiesUtil.getQueryFromPropertyFile("killProc",new String[] { SPID.trim() }));
		if (isKilled) {
			logger.info("Successfully killed the query with SPID" + SPID);
		}
	}
}
