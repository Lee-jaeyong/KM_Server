package ljy.book.admin.etc;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class EtcTEST {

	@Test
	public void nowDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(simple.format(c.getTime()));
	}
}
