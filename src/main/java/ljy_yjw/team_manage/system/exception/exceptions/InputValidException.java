package ljy_yjw.team_manage.system.exception.exceptions;

import java.util.List;

import ljy_yjw.team_manage.system.exception.object.ErrorResponse.FieldError;
import lombok.Getter;

@Getter
public class InputValidException extends Exception {
	private static final long serialVersionUID = 1L;
	List<FieldError> errors;

	public InputValidException(List<FieldError> errors) {
		this.errors = errors;
	}
}
