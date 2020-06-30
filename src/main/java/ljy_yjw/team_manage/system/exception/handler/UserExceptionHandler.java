package ljy_yjw.team_manage.system.exception.handler;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ljy_yjw.team_manage.system.exception.exceptions.user.DupIdException;
import ljy_yjw.team_manage.system.exception.object.ErrorCode;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(DupIdException.class)
	public ResponseEntity<?> teamCodeNotFound(DupIdException ex) {
		log.error("-- DupIdException!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.CAN_NOT_PERFORM);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
