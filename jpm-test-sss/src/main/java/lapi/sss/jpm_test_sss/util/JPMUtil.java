package lapi.sss.jpm_test_sss.util;

import java.util.Calendar;
import java.util.Date;

public class JPMUtil {
	public static Date getNMinutesAgo(int minutes) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, minutes*-1);
		
		return c.getTime();
	}
}
