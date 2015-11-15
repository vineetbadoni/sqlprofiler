package com.ca.beans;

public class DBCC {
	private long waitTime;
	
	private String eventType;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getEventInfo() {
		return eventInfo;
	}

	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}

	private String parameters;
	private String eventInfo;
	
	@Override
	public String toString() {
		return "\n*******************************************************\n\tEventType:"+eventType+"\n\tEvenINFO(Query):\n\t\t["+eventInfo+"]\n\tParameters::"+parameters+"\n*******************************************************\n";
	}
}
