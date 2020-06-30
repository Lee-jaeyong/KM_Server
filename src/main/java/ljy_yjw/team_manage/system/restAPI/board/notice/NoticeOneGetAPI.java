package ljy_yjw.team_manage.system.restAPI.board.notice;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.notice.NoticeAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.notice.NoticeReadService;
import ljy_yjw.team_manage.system.service.update.notice.NoticeSettingService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;

@NoticeController
public class NoticeOneGetAPI {

	@Autowired
	NoticeAuthService noticeAuthService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	NoticeReadService noticeReadService;

	@Autowired
	NoticeSettingService noticeSettingService;

	@GetMapping("/{seq}")
	@Memo("해당 공지사항을 가져오는 메소드")
	public ResponseEntity<?> get(@PathVariable long seq, @Current_User Users user)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		Notice notice = noticeAuthService.getNoticeObject(seq);
		teamAuthService.checkTeamAuth(user, notice.getTeam().getCode());
		userSettingService.imgSetting(notice.getUser());
		noticeSettingService.settingImg(notice);
		return ResponseEntity.ok(getCustomEntityModel(notice, user));
	}

	private CustomEntityModel<Notice> getCustomEntityModel(Notice notice, Users user) {
		if (notice.getUser().getId().equals(user.getId()))
			return new CustomEntityModel<Notice>(notice, this, Long.toString(notice.getSeq()), Link.ALL);
		else
			return new CustomEntityModel<Notice>(notice, this, Long.toString(notice.getSeq()), Link.NOT_INCLUDE);
	}
}
