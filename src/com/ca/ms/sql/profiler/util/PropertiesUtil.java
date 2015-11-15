package com.ca.ms.sql.profiler.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.ca.logger.BaseLogger;

public class PropertiesUtil {
	
	private static Properties dbConnProperties = null;
	private static Properties queriesProperties = null;
	
	private static BaseLogger logger = BaseLogger.getInstance(PropertiesUtil.class);
	
	static{
		try {
			loadProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadProperties() throws Exception {
		dbConnProperties = new Properties();
		if(System.getProperty("dbConnection.properties.url")!=null){
			dbConnProperties.load(new FileInputStream(new File(System.getProperty("dbConnection.properties.url"))));
		}else{
		dbConnProperties.load(new FileInputStream(new File("resources/dbConnection.properties")));
		}

		queriesProperties = new Properties();
		queriesProperties.load(new FileInputStream(new File("resources/dbqueries.properties")));
	}
	
	public static String getConnectionProperty(String propName,String...strings) {
		String propertyBaseValue = dbConnProperties.getProperty(propName);
		MessageFormat format = new MessageFormat(propertyBaseValue);
		return format.format(strings);
	}
	
	public static String getQueryFromPropertyFile(String propName,String...strings ) {
		String propertyBaseValue = queriesProperties.getProperty(propName);
		MessageFormat format = new MessageFormat(propertyBaseValue);
		return format.format(strings);
	}
}
