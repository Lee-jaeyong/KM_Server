package ljy.book.admin.common.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

public class CustomDate {
	static SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

	public static String getNowDate() {
		return s.format(Calendar.getInstance().getTime());
	}
}
