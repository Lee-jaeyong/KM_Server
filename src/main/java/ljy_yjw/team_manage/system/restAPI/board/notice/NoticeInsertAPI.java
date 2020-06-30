package ljy_yjw.team_manage.system.restAPI.board.notice;

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
import ljy_yjw.team_manage.system.domain.dto.NoticeDTO;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.notice.NoticeOneInsertService;
import ljy_yjw.team_manage.system.security.Current_User;
import lombok.var;

@NoticeController
public class NoticeInsertAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	NoticeOneInsertService noticeOneInsertService;

	@PostMapping("/{code}")
	@Memo("팀장이 팀의 공지사항을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid NoticeDTO noticeDTO, @Current_User Users user,
		Errors error) throws InputValidException, TeamCodeNotFountException, NotTeamLeaderException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		teamAuthService.checkTeamLeader(user, code);
		Team team = teamAuthService.getTeamObject(code);
		Notice saveNotice = noticeDTO.parseThis2Notice(team, user);
		saveNotice = noticeOneInsertService.insertNotice(saveNotice);
		var result = new CustomEntityModel<>(saveNotice, this, Long.toString(saveNotice.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}
}
