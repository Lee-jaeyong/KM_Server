package ljy_yjw.team_manage.system.exception.object;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

	private String message;
	private int status;
	@JsonInclude(value = Include.NON_NULL)
	private List<FieldError> errors;
	private String code;

	public ErrorResponse(String message, ErrorCode errorCode) {
		this.message = message;
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class FieldError {
		private String field;
		@JsonInclude(value = Include.NON_NULL)
		private String value;
		private String reason;

		public FieldError(String field, String reason) {
			this.field = field;
			this.reason = reason;
		}
	}

	public static List<FieldError> parseFieldError(List<org.springframework.validation.FieldError> fieldErrors) {
		return fieldErrors.stream().map(c -> new FieldError(c.getField(), c.getDefaultMessage())).collect(Collectors.toList());
	}
}