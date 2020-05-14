package ljy_yjw.team_manage.system.exception.exceptions;

import lombok.Getter;

@Getter
public class OtherInputValidException extends Exception {
	private static final long serialVersionUID = 1L;

	public OtherInputValidException(String message) {
		super(message);
	}
}
