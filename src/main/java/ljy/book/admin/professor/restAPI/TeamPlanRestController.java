package ljy.book.admin.professor.restAPI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.dto.validate.DateRequestDTOValid;
import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.professor.requestDTO.DateRequestDTO;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamPlanService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/plan")
public class TeamPlanRestController {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamPlanService teamPlanService;

	@Autowired
	DateRequestDTOValid dateRequestValid;

	@Memo("자신의 모든 일정 가져오기(끝난)")
	@GetMapping("/all/finished")
	public ResponseEntity<?> getMyPlanAllFinished(@RequestParam(defaultValue = "") String search, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) {
		PagedModel<EntityModel<PlanByUser>> result = assembler
			.toModel(teamPlanService.getMyPlanAllFinished(search, user, pageable));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신의 모든 일정 가져오기")
	@GetMapping("/all/unfinished")
	public ResponseEntity<?> getMyPlanAllUnfinished(@RequestParam(defaultValue = "") String search, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) {
		PagedModel<EntityModel<PlanByUser>> result = assembler
			.toModel(teamPlanService.getMyPlanAllUnFinished(search, user, pageable));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정 단건 조회")
	@GetMapping("/{seq}")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user) {
		PlanByUser checkPlan = teamPlanService.getOne(seq);
		if (!teamService.checkTeamAuth(user, checkPlan.getTeam().getCode()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		EntityModel<PlanByUser> result = new EntityModel<PlanByUser>(checkPlan);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드")
	@GetMapping("/{code}/all")
	public ResponseEntity<?> getAll(@PathVariable String code, @RequestBody @Valid DateRequestDTO dateRequestDTO, Errors error,
		@Current_User Users user, PagedResourcesAssembler<PlanByUser> assembler) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		dateRequestValid.validate(dateRequestDTO, error);
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (!teamService.checkTeamAuth(user, code))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		PagedModel<EntityModel<PlanByUser>> result = assembler.toModel(teamPlanService.getAll(code, dateRequestDTO));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 등록하는 메소드")
	@PostMapping("/{team}")
	public ResponseEntity<?> save(@PathVariable TeamDTO team, @RequestBody @Valid PlanByUserDTO planByUser, Errors error,
		@Current_User Users user) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (teamService.checkAuthSuccessThenGetTeam(team.getSeq(), user) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (planByUser.getTeamPlan() == BooleanState.YSE && !teamService.checkTeamByUser(team, user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		teamPlanService.save(team.getSeq(), planByUser, user);
		EntityModel<PlanByUserDTO> result = new EntityModel<PlanByUserDTO>(planByUser);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 수정하는 메소드")
	@PutMapping("/{seq}")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid PlanByUserDTO planByUser,
		@Current_User Users user) {
		// 1. first 자신의 일정이 맞는가를 확인
		PlanByUser plan = teamPlanService.checkAuthPlanSuccessThenGet(seq, user);
		if (plan == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamPlanService.update(seq, planByUser);
		EntityModel<PlanByUserDTO> result = new EntityModel<PlanByUserDTO>(planByUser);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 삭제하는 메소드")
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) {
		PlanByUser plan = teamPlanService.checkAuthPlanSuccessThenGet(seq, user);
		if (plan == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamPlanService.delete(seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
