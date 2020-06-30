package ljy_yjw.team_manage.system.restAPI.board.referenceData;

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
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.referenceData.ReferenceDataReadService;
import ljy_yjw.team_manage.system.service.update.referenceData.ReferenceDataSettingService;
import lombok.var;

@ReferenceDataController
public class ReferenceDataListGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	ReferenceDataReadService referenceDataReadService;

	@Autowired
	ReferenceDataSettingService referenceDataSettingService;

	@GetMapping("/{code}/all")
	@Memo("특정 팀의 모든 참고자료를 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<ReferenceData> assembler) throws TeamCodeNotFountException, IOException {
		teamAuthService.checkTeamAuth(user, code);
		List<ReferenceData> referenceDataList = referenceDataReadService.getReferenceDataList(code, pageable);
		for (ReferenceData c : referenceDataList) {
			referenceDataSettingService.settingImg(c);
		}
		long totalCount = referenceDataReadService.countReferenceData(code);
		var result = assembler.toModel(new PageImpl<>(referenceDataList, pageable, totalCount));
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
