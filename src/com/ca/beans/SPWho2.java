package com.ca.beans;

import java.util.Date;

public class SPWho2 {
	public String getSPID() {
		return SPID;
	}

	public void setSPID(String sPID) {
		SPID = sPID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public String getBlkBy() {
		return BlkBy;
	}

	public void setBlkBy(String blkBy) {
		BlkBy = blkBy;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}

	public String getCommand() {
		return Command;
	}

	public void setCommand(String command) {
		Command = command;
	}

	public String getCPUTime() {
		return CPUTime;
	}

	public void setCPUTime(String cPUTime) {
		CPUTime = cPUTime;
	}

	public String getDiskIO() {
		return DiskIO;
	}

	public void setDiskIO(String diskIO) {
		DiskIO = diskIO;
	}

	public String getLastBatch() {
		return LastBatch;
	}

	public void setLastBatch(String lastBatch) {
		LastBatch = lastBatch;
	}

	public String getProgramName() {
		return ProgramName;
	}

	public void setProgramName(String programName) {
		ProgramName = programName;
	}

	public String getREQUESTID() {
		return REQUESTID;
	}

	public void setREQUESTID(String rEQUESTID) {
		REQUESTID = rEQUESTID;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	private String Status;
	private String Login;
	private String HostName;
	private String BlkBy;
	private String DBName;
	private String Command;
	private String CPUTime;
	private String DiskIO;
	private String LastBatch;
	private String ProgramName;
	private String REQUESTID;
	private Date recordTime;
	private String SPID;
	
	@Override
	public String toString() {
		return "\n=================SP_Who2============================\nDate:" + recordTime + "\n\tStatus:" + Status + "\n\tLogin:" + Login
				+ "\n\tHostName:" + HostName + "\n\tBlkBy:" + BlkBy + "\n\tDBName:"
				+ DBName + "\n\tCommand:" + Command + "\n\tCPUTime:" + CPUTime
				+ "\n\tDiskIO:" + DiskIO + "\n\tLastBatch:" + LastBatch
				+ "\n\tProgramName:" + ProgramName + "\n\tREQUESTID:" + REQUESTID+"\n\tSPID:"+SPID+"\n=================================================\n";
	}
}
