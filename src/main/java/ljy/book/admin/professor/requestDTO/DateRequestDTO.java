package ljy.book.admin.professor.requestDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateRequestDTO {
	@NotNull(message = "해당 년도를 입력해주세요")
	String year;
	@NotNull(message = "해당 월을 입력해주세요")
	String month;
	@NotNull(message = "해당 일을 입력해주세요")
	String day;

	@JsonIgnore
	public String[] getFirstAndLastDay() {
		try {
			int year = Integer.parseInt(this.year);
			int month = Integer.parseInt(this.month);
			int day = Integer.parseInt(this.day);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			String[] result = new String[2];
			String _month = month / 10 == 1 ? Integer.toString(month) : "0" + month;
			String firstDay = Integer.toString(cal.getMinimum(Calendar.DAY_OF_MONTH));
			firstDay = firstDay.length() == 1 ? "0" + firstDay : firstDay;
			result[0] = year + "-" + _month + "-" + firstDay;
			result[1] = year + "-" + _month + "-" + Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
