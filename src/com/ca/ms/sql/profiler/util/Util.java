package com.ca.ms.sql.profiler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ca.logger.BaseLogger;

public class Util{
	
	public static void printResults(ArrayList spWho2Results,BaseLogger logger) {
		for (Object obj : spWho2Results) {
			logger.info(obj.toString());
		}
	}
	
	public static Date parseDate(String date,String format) throws ParseException {
		if(format==null){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
}
