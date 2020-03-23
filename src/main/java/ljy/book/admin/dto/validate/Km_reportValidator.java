package ljy.book.admin.dto.validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy.book.admin.dto.Km_ReportDTO;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.service.KM_ClassService;

@Component
public class Km_reportValidator implements Validator {

	@Autowired
	KM_ClassService km_classService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Km_ReportDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Km_ReportDTO km_report = (Km_ReportDTO) target;
		KM_class km_class = new KM_class();
		//km_class.setClassIdx(km_report.getClassIdx());
//		if (km_classService.findByClassIdx(km_class) == null) {
//			errors.reject("classIdx", "존재하지 않는 수업입니다.");
//		}

		try {
			String startDate = km_report.getReportStartDate();
			String endDate = km_report.getReportEndDate();
			Calendar date = Calendar.getInstance();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date _startDate = transFormat.parse(startDate);
			Date _endDate = transFormat.parse(endDate);
			if (_startDate.getTime() > _endDate.getTime()) {
				errors.reject("classStartDate", "날짜 입력이 잘못되었습니다.");
				errors.reject("classEndDate", "날짜 입력이 잘못되었습니다.");
			}
		} catch (Exception e) {
		}
	}

}
