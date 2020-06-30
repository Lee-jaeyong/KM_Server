package ljy_yjw.team_manage.system.restAPI.board.freeboard;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;
import ljy_yjw.team_manage.system.service.update.freeBoard.FreeBoardSettingService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;

@FreeBoardController
public class FreeBoardOneGetAPI {

	@Autowired
	FreeBoardReadService freeBoardReadService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	FreeBoardSettingService freeBoardSettingService;

	@GetMapping("/{seq}")
	@Memo("자유게시판 단건 조회 메소드")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		FreeBoard freeBoard = freeBoardReadService.getFreeBoard(seq);
		userSettingService.imgSetting(freeBoard.getUser());
		teamAuthService.checkTeamAuth(user, freeBoard.getTeam().getCode());
		freeBoardSettingService.settingImg(freeBoard);
		return ResponseEntity.ok(getCustomEntityModel(freeBoard, user));
	}

	private CustomEntityModel<?> getCustomEntityModel(FreeBoard freeBoard, Users user) {
		if (freeBoard.getUser().getId().equals(user.getId()))
			return new CustomEntityModel<>(freeBoard, this, Long.toString(freeBoard.getSeq()), Link.ALL);
		else
			return new CustomEntityModel<>(freeBoard, this, Long.toString(freeBoard.getSeq()), Link.NOT_INCLUDE);
	}
}
