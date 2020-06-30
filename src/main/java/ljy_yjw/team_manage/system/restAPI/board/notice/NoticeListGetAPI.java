package ljy_yjw.team_manage.system.restAPI.board.notice;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.notice.NoticeReadService;
import ljy_yjw.team_manage.system.service.update.notice.NoticeSettingService;
import lombok.var;

@NoticeController
public class NoticeListGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	NoticeReadService noticeReadService;

	@Autowired
	NoticeSettingService noticeSettingService;

	@GetMapping("/{code}/all")
	@Memo("팀의 모든 공지사항을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<Notice> assembler) throws TeamCodeNotFountException, IOException {
		teamAuthService.checkTeamAuth(user, code);
		List<Notice> noticeList = noticeReadService.getNoticeList(code, pageable);
		for (Notice c : noticeList) {
			noticeSettingService.settingImg(c);
		}
		long totalCount = noticeReadService.getNoticeCount(code);
		var result = assembler.toModel(new PageImpl<Notice>(noticeList, pageable, totalCount));
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
