package ljy_yjw.team_manage.system.restAPI.board.freeboard;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.FreeBoardDTO;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.freeBoard.FreeBoardAuthService;
import ljy_yjw.team_manage.system.service.update.freeBoard.FreeBoardOneUpdateService;
import lombok.var;

@FreeBoardController
public class FreeBoardUpdateAPI {

	@Autowired
	FreeBoardAuthService freeBoardAuthService;

	@Autowired
	FreeBoardOneUpdateService freeBoardOneUpdateService;

	@PutMapping("/{seq}")
	@Memo("자유 게시판을 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) throws NotFoundException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		FreeBoard updateFreeBoard = freeBoardDTO.parseThis2FreeBoard(user, null);
		updateFreeBoard = freeBoardOneUpdateService.updateFreeBoard(seq, updateFreeBoard);
		var result = new CustomEntityModel<>(updateFreeBoard, this, Long.toString(updateFreeBoard.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}
}
