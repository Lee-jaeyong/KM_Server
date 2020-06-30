package ljy_yjw.team_manage.system.exception.handler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.FileUploadException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.OtherInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.AlreadyAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.CanNotAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorCode;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> teamCodeNotFound(HttpMessageNotReadableException ex) {
		log.error("-- teamCodeNotFound Exception!!!");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> teamCodeNotFound(HttpMediaTypeNotSupportedException ex) {
		log.error("-- teamCodeNotFound Exception!!!");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(TeamCodeNotFountException.class)
	public ResponseEntity<?> teamCodeNotFound(TeamCodeNotFountException ex) {
		log.error("-- teamCodeNotFound Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.TEAM_CODE_NOT_FOUND);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NotTeamLeaderException.class)
	public ResponseEntity<?> notTeamLeader(NotTeamLeaderException ex) {
		log.error("-- notTeamLeader Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.NOT_TEAM_LEADER);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InputValidException.class)
	public ResponseEntity<?> notTeamLeader(InputValidException ex) {
		log.error("-- InputValid Exception!!!");
		ErrorResponse error = new ErrorResponse("입력값이 잘못되었습니다.", ErrorCode.INVALID_INPUT_VALUE);
		error.setErrors(ex.getErrors());
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(OtherInputValidException.class)
	public ResponseEntity<?> otherInputValid(OtherInputValidException ex) {
		log.error("-- OtherInputValid Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.INVALID_INPUT_VALUE);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AlreadyAppliedException.class)
	public ResponseEntity<?> alreadyApplied(AlreadyAppliedException ex) {
		log.error("-- ALREADY_APPLIED Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.ALREADY_APPLIED);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(CanNotAppliedException.class)
	public ResponseEntity<?> canNotApplied(CanNotAppliedException ex) {
		log.error("-- CAN_NOT_APPLIED Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.CAN_NOT_APPLIED);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException ex) {
		log.error("-- NOT_FOUND Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.NOT_FOUND);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(CanNotPerformException.class)
	public ResponseEntity<?> canNotPerform(CanNotPerformException ex) {
		log.error("-- CAN_NOT_PERFORM Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.CAN_NOT_PERFORM);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(PlanByUserNotAuthException.class)
	public ResponseEntity<?> planByUserNotAuth(PlanByUserNotAuthException ex) {
		log.error("-- CAN_NOT_PERFORM Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.PLAN_NOT_FOUND);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(CheckInputValidException.class)
	public ResponseEntity<?> checkInputValid(CheckInputValidException ex) {
		log.error("-- CAN_NOT_PERFORM Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.INVALID_INPUT_VALUE);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(FileUploadException.class)
	public ResponseEntity<?> fileUpload(FileUploadException ex) {
		log.error("-- CAN_NOT_PERFORM Exception!!!");
		ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.FILE_UPLOAD_ERROR);
		EntityModel<ErrorResponse> result = new EntityModel<>(error);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
