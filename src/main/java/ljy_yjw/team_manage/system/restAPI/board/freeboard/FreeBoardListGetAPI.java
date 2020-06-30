package ljy_yjw.team_manage.system.restAPI.board.freeboard;

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
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;
import ljy_yjw.team_manage.system.service.update.freeBoard.FreeBoardSettingService;
import lombok.var;

@FreeBoardController
public class FreeBoardListGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	FreeBoardReadService freeBoardReadService;

	@Autowired
	FreeBoardSettingService freeBoardSettingService;

	@GetMapping("/{code}/all")
	@Memo("특정 팀의 모든 자유게시판을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<FreeBoard> assembler) throws TeamCodeNotFountException, IOException {
		teamAuthService.checkTeamAuth(user, code);
		List<FreeBoard> freeBoardList = freeBoardReadService.getFreeBoardList(code, pageable);
		for (FreeBoard c : freeBoardList) {
			freeBoardSettingService.settingImg(c);
		}
		long totalCount = freeBoardReadService.countFreeBoard(code);
		var result = assembler.toModel(new PageImpl<>(freeBoardList, pageable, totalCount));
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
