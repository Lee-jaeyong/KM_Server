package ljy.book.admin.dto.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy.book.admin.dto.Km_userLoginDTO;
import ljy.book.admin.security.KM_UserService;

@Component
public class Km_userLoginValidator implements Validator {

	@Autowired
	KM_UserService km_userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Km_userLoginDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Km_userLoginDTO user = (Km_userLoginDTO) target;
//		try {
////			if (km_userService.login(user) == null) {
////				errors.rejectValue("id", "login_Faild", "아이디 혹은 비밀번호가 잘못되었습니다.");
////				errors.rejectValue("pass", "login_Faild", "아이디 혹은 비밀번호가 잘못되었습니다.");
////			}
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}

}
