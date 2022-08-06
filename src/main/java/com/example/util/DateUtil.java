package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	
	public Date convertStringDateToDate(String strDate) {
		Date date = null;
		try {
			date=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	@SuppressWarnings("unused")
	private Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		
		
//		Date now = new Date();
//		SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//		String seed = isoDate.format(now);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		
		return cal.getTime();
	}

}
