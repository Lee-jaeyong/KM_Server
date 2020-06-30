package ljy_yjw.team_manage.system.restAPI.board.freeboard;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.FreeBoardDTO;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.freeBoard.FreeBoardOneInsertService;
import lombok.var;

@FreeBoardController
public class FreeBoardInsertAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	FreeBoardOneInsertService freeBoardOneInsertService;

	@PostMapping("/{code}")
	@Memo("자유 게시판을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) throws InputValidException, TeamCodeNotFountException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		teamAuthService.checkTeamAuth(user, code);
		Team team = teamAuthService.getTeamObject(code);
		FreeBoard freeBoard = freeBoardDTO.parseThis2FreeBoard(user, team);
		FreeBoard saveFreeBoard = freeBoardOneInsertService.insertFreeBoard(freeBoard);
		var result = new CustomEntityModel<>(saveFreeBoard, this, Long.toString(saveFreeBoard.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

}
