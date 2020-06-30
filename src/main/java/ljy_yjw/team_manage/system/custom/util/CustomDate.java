package ljy_yjw.team_manage.system.custom.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class CustomDate {
	static SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

	public static String getNowDate() {
		return s.format(Calendar.getInstance().getTime());
	}

	public static Date LocalDate2Date(LocalDate date) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
	}

	public static LocalDate dateToLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String dateToString(Date date) {
		return s.format(date);
	}
}
