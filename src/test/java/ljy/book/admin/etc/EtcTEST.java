package ljy.book.admin.etc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class EtcTEST {

	@Test
	public void getDate() {
		int year = 2020;
		int month = 04;
		int day = 20;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day); // 월은 -1해줘야 해당월로 인식
		System.out.println("첫번째 일: " + cal.getMinimum(Calendar.DAY_OF_MONTH));
		System.out.println("마지막 일(현재 날짜 기준 최대수): " + cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // 기본적으로 이걸 사용
	}

	@Test
	@Ignore
	public void nowDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(simple.format(c.getTime()));
	}
}
