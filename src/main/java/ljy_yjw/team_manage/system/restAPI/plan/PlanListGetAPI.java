package ljy_yjw.team_manage.system.restAPI.plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.plan.SearchDTO;
import ljy_yjw.team_manage.system.domain.dto.valid.team.TeamValidator;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService;
import ljy_yjw.team_manage.system.service.read.plan.PlanSearchService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;
import lombok.var;

@PlanController
public class PlanListGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	PlanSearchService planSearchService;

	@Autowired
	PlanReadService planReadService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	TeamValidator teamValidator;

	@Memo("해당 팀의 팀장이 지정한 팀 일정 가져오기")
	@GetMapping
	public ResponseEntity<?> getTeamPlanList(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {

		teamValidator.check(code, user);

		searchDTO.setUserId(user.getId());
		searchDTO.setTeamPlan(BooleanState.YES);
		return getResponse(code, searchDTO, pageable, assembler);
	}

	@Memo("자신의 모든 일정 가져오기")
	@GetMapping("/{code}/all/my")
	public ResponseEntity<?> getPlanAll(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {

		teamValidator.check(code, user);
		
		searchDTO.setUserId(user.getId());

		return getResponse(code, searchDTO, pageable, assembler);
	}

	@Memo("자신의 모든 일정 가져오기(해당 팀의 끝난)")
	@GetMapping("/{code}/all/finished")
	public ResponseEntity<?> getMyPlanAllFinished(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {

		teamValidator.check(code, user);

		searchDTO.setUserId(user.getId());
		searchDTO.setFinished(BooleanState.FINISHED);

		return getResponse(code, searchDTO, pageable, assembler);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드(특정 달)")
	@GetMapping("/{code}/search/all")
	public ResponseEntity<?> getSearchAll(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {
		teamValidator.check(code, user);

		return getResponse(code, searchDTO, pageable, assembler);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드")
	@GetMapping("/{code}/all")
	public ResponseEntity<?> getAll(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException, IOException {
		teamValidator.check(code, user);

		return getResponse(code, searchDTO, pageable, assembler);
	}

	// 보류
	@Memo("해당 코드의 팀의 단건 일정을 모두 끝낸 일정을 가져오는 메소드")
	@GetMapping("/{code}/finishedAll")
	public ResponseEntity<?> finishedAll(@PathVariable String code, SearchDTO searchDTO, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {
		teamValidator.check(code, user);
		searchDTO.setAllFinished(BooleanState.FINISHED);
		return getResponse(code, searchDTO, pageable, assembler);
	}

	private ResponseEntity<?> getResponse(String code, SearchDTO searchDTO, Pageable pageable,
		PagedResourcesAssembler<PlanByUser> assembler) {
		long totalCount = planSearchService.countAllByCodeAndCheckDate(code, searchDTO);
		List<PlanByUser> planList = planSearchService.findAllByCodeAndCheckDate(code, searchDTO, pageable);
		planList.forEach(c->{
			try {
				userSettingService.imgSetting(c.getUser());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		var result = assembler.toModel(new PageImpl<>(planList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));

		return ResponseEntity.ok(result);
	}
}
