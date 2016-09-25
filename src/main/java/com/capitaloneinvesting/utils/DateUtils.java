package com.capitaloneinvesting.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static int getCurrentMonth() {
		Calendar cal = org.apache.commons.lang3.time.DateUtils.toCalendar(new Date());
		return cal.get(Calendar.MONTH);
	}

	public static int getCurrentYear() {
		Calendar cal = org.apache.commons.lang3.time.DateUtils.toCalendar(new Date());
		return cal.get(Calendar.YEAR);
	}

}
