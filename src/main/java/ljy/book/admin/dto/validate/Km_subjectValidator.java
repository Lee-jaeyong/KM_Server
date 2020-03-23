package ljy.book.admin.dto.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy.book.admin.dto.Km_subjectDTO;
import ljy.book.admin.entity.KM_subject;
import ljy.book.admin.jpaAPI.KM_subjectAPI;

@Component
public class Km_subjectValidator implements Validator {

	@Autowired
	KM_subjectAPI km_subjectAPI;

	@Override
	public boolean supports(Class<?> clazz) {
		return KM_subject.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		KM_subject km_subject = (KM_subject) target;
		if (!km_subjectAPI.findById(km_subject.getSeq()).isPresent()) {
			errors.reject("empty_subject", "존재하지 않는 학과입니다.");
		}
	}

}
