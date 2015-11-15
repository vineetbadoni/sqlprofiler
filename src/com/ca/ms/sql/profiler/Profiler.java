package com.ca.ms.sql.profiler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.ca.beans.DBCC;
import com.ca.beans.SPWho2;
import com.ca.logger.BaseLogger;
import com.ca.ms.sql.profiler.util.DBConnectionsUtil;
import com.ca.ms.sql.profiler.util.PropertiesUtil;
import com.ca.ms.sql.profiler.util.Util;

public class Profiler {
	
	private static BaseLogger logger = BaseLogger.getInstance(Profiler.class);
	
	private static Connection targetDBConn = null;
	private static Connection samplesDBConn = null;

	public static void main(String[] args) throws Exception {
		int i = 0;
		while (shouldContinue()) {
			
			logger.info("Going to sleep for "+System.getProperty("profiler.sleep.interval","2")+" min(s)");
			Thread.currentThread().sleep((int)(Float.parseFloat(System.getProperty("profiler.sleep.interval","2"))*60*1000));
			logger.info("Collecting Data");
			logger.info("===================================[\tSet "+i+"\t]======================================================");
															 startWatcher();
			logger.info("===================================[\tSet "+i+++"\t]======================================================");
		}
	}

	private static boolean shouldContinue() {
		Properties props = new Properties();
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			props.load(stream);
			return Boolean.parseBoolean(props.getProperty("loop.continue"));
		} catch (IOException e) {
			logger.error("Error occured while reading property. Exiting the loop", e);
		}finally{
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private static void startWatcher() throws ClassNotFoundException,
			SQLException, ParseException {
		logger.info("Going to watch DB :" +DBConnectionsUtil.getConnectionURL());
		targetDBConn = DBConnectionsUtil.getTargetDBConnection();
		ArrayList<SPWho2> spWho2Results = getSP_who2Data();
		
//		Util.printResults(spWho2Results);
		
		if(spWho2Results.size()==0){
			logger.info("No Query is blocked by another query.");
		}
		
		for (SPWho2 spWhoData : spWho2Results) {
			Date startDate = Util.parseDate(spWhoData.getLastBatch(), "MM/dd HH:mm:ss");
			startDate.setYear(new Date().getYear());
			logger.info("Client["+spWhoData.getHostName()+"] Blocked Query[SPID="+spWhoData.getSPID().trim()+"].[Query Start Time="+startDate+"]"+" [Wait Duration(secs)="+((System.currentTimeMillis()-startDate.getTime())/1000)+"]");
			Util.printResults(getDBCCData(spWhoData.getSPID()),logger);
			logger.info("Client["+spWhoData.getHostName()+"] Blocking Query[SPID="+spWhoData.getBlkBy().trim()+"]");
			Util.printResults(getDBCCData(spWhoData.getBlkBy()),logger);
		}
		
		DBConnectionsUtil.dropConnection(targetDBConn);
	}

	private static ArrayList<SPWho2> getSP_who2Data()
			throws SQLException {
		java.sql.CallableStatement cs = targetDBConn.prepareCall("{"+PropertiesUtil.getQueryFromPropertyFile("querySPWho2")+"}");
		ArrayList<SPWho2> spWho2Results = new ArrayList<SPWho2>();
		ResultSet rs = cs.executeQuery();
		while (rs.next()) {
			String blockedBy = rs.getString("BlkBy");
			if (!blockedBy.trim().equalsIgnoreCase("")
					&& !blockedBy.trim().equalsIgnoreCase(".")) {
				SPWho2 who2Results = new SPWho2();
				who2Results.setRecordTime(new Date());
				who2Results.setBlkBy(rs.getString("BlkBy"));
				who2Results.setCommand(rs.getString("Command"));
				who2Results.setCPUTime(rs.getString("CPUTime"));
				who2Results.setDBName(rs.getString("DBName"));
				who2Results.setDiskIO(rs.getString("DiskIO"));
				who2Results.setHostName(rs.getString("HostName"));
				who2Results.setLastBatch(rs.getString("LastBatch"));
				who2Results.setLogin(rs.getString("Login"));
				who2Results.setProgramName(rs.getString("ProgramName"));
				who2Results.setREQUESTID(rs.getString("REQUESTID"));
				who2Results.setSPID(rs.getString("SPID"));
				who2Results.setStatus(rs.getString("Status"));
				spWho2Results.add(who2Results);
			}
		}
		return spWho2Results;
	}

	private static ArrayList<DBCC> getDBCCData(String blkBy)
			throws SQLException {
		ArrayList<DBCC> spWho2Results = new ArrayList<DBCC>();
		Statement statement = targetDBConn.createStatement();
		ResultSet rs = statement.executeQuery(PropertiesUtil.getQueryFromPropertyFile("queryDBCC", new String[]{blkBy.trim()}));
		while (rs.next()) {
			DBCC dbccData = new DBCC();
			dbccData.setEventType(rs.getString("EventType"));
			dbccData.setParameters(rs.getString("Parameters"));
			dbccData.setEventInfo(rs.getString("EventInfo"));
			spWho2Results.add(dbccData);
		}
		return spWho2Results;
	}
}