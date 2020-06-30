package ljy_yjw.team_manage.system.restAPI.board.freeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.freeBoard.FreeBoardAuthService;
import ljy_yjw.team_manage.system.service.delete.freeBoard.FreeBoardOneDeleteService;
import lombok.var;

@FreeBoardController
public class FreeBoardDeleteAPI {

	@Autowired
	FreeBoardAuthService freeBoardAuthService;

	@Autowired
	FreeBoardOneDeleteService freeBoardOneDeleteService;

	@DeleteMapping("/{seq}")
	@Memo("자유 게시판을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		FreeBoard deleteFreeBoard = freeBoardOneDeleteService.deleteFreeBoard(seq);
		var result = new CustomEntityModel<>(deleteFreeBoard, this, Long.toString(deleteFreeBoard.getSeq()), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

}
