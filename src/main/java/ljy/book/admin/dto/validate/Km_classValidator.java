package ljy.book.admin.dto.validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy.book.admin.dto.Km_classDTO;
import ljy.book.admin.request.KM_classVO;

@Component
public class Km_classValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return KM_classVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		KM_classVO km_class = (KM_classVO) target;
		try {
			String startDate = km_class.getStartDate();
			String endDate = km_class.getEndDate();
			Calendar date = Calendar.getInstance();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date _startDate = transFormat.parse(startDate);
			Date _endDate = transFormat.parse(endDate);
			if (_startDate.getTime() > _endDate.getTime()) {
				errors.reject("classStartDate", "날짜 입력이 잘못되었습니다.");
				errors.reject("classEndDate", "날짜 입력이 잘못되었습니다.");
			}
		} catch (Exception e) {
			errors.reject("classStartDate", "날짜 입력이 잘못되었습니다.");
			errors.reject("classEndDate", "날짜 입력이 잘못되었습니다.");
		}
	}

}
