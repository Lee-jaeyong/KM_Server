package ljy.book.admin.etc;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class SessionTest {

	@Test
	public void sessionTEST() throws IOException, ParseException {
		String time = "2020-03-14";
		Calendar date = Calendar.getInstance();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date to = transFormat.parse(time);
		
		System.out.println(to.getTime());
	}
}