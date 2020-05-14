package ljy_yjw.team_manage.system.exception.object;

import lombok.Getter;

@Getter
public enum ErrorCode {

	TEAM_CODE_NOT_FOUND(404, "C001", "Team Code Not Found"), NOT_TEAM_LEADER(401, "C002", "Not Team Leader"),
	INVALID_INPUT_VALUE(400, "C002", " Invalid Input Value"), METHOD_NOT_ALLOWED(405, "C003", " Invalid Input Value"),
	HANDLE_ACCESS_DENIED(403, "C004", "Access is Denied"), CAN_NOT_APPLIED(400, "C002", "Can Not Applied"),
	CAN_NOT_PERFORM(400, "C002", "Can Not Perform"), ALREADY_APPLIED(400, "C002", "Already Applied"),
	NOT_FOUND(404, "C001", "Not Found"), PLAN_NOT_FOUND(404, "C001", "Plan Not Found"),
	FILE_UPLOAD_ERROR(400, "C002", "File Upload Error");

	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}
