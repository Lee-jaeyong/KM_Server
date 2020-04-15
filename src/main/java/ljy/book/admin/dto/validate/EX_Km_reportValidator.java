package ljy.book.admin.dto.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EX_Km_reportValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

//	@Autowired
//	KM_Class_Professor_Service km_classService;
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return KM_reportVO.class.equals(clazz);
//	}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//		KM_reportVO km_report = (KM_reportVO) target;
//		try {
//			String startDate = km_report.getStartDate();
//			String endDate = km_report.getEndDate();
//			Calendar date = Calendar.getInstance();
//			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date _startDate = transFormat.parse(startDate);
//			Date _endDate = transFormat.parse(endDate);
//			if (_startDate.getTime() > _endDate.getTime()) {
//				errors.reject("classStartDate", "날짜 입력이 잘못되었습니다.");
//				errors.reject("classEndDate", "날짜 입력이 잘못되었습니다.");
//			}
//		} catch (Exception e) {
//		}
//	}

}
