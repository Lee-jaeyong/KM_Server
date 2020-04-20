package ljy.book.admin.dto.validate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy.book.admin.professor.requestDTO.DateRequestDTO;

@Component
public class DateRequestDTOValid implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return DateRequestDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DateRequestDTO date = (DateRequestDTO) target;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String fullDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
			format.parse(fullDate);
		} catch (Exception e) {
			errors.reject("", "날짜 입력이 잘못되었습니다.");
		}
	}
}
