package ljy_yjw.team_manage.system.restAPI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
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

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.security.UsersService;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.plan.PlanByUserOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.plan.PlanByUserOneInsertService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService.GetType;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import ljy_yjw.team_manage.system.service.update.plan.PlanByUserOneUpdateService;
import lombok.var;

@RestController
@RequestMapping("/api/teamManage/plan")
public class TeamPlanRestController {

	@Autowired
	UsersService userSerivce;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	PlanReadService planReadService;

	@Autowired
	PlanByUserOneInsertService planByUserOneInsertService;

	@Autowired
	PlanByUserOneUpdateService planByUserOneUpdateService;

	@Autowired
	PlanByUserOneDeleteService planByUserOneDeleteService;

	@Memo("개인별 일정 개수 가져오기")
	@GetMapping("/{code}/group-by-user")
	public ResponseEntity<?> getChartDataGroupByUser(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		var result = new CollectionModel<>(planReadService.getPlanCountGroupByUser(code));
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신의 모든 일정 가져오기")
	@GetMapping("/all")
	public ResponseEntity<?> getPlanAll(@Current_User Users user, PagedResourcesAssembler<PlanByUser> assembler,
		Pageable pageable) {
		List<PlanByUser> planList = planReadService.getPlanByMy(user.getId(), null, null, pageable, null);
		for (int i = 0; i < planList.size(); i++) {
			planList.get(i).setTodoList(TodoList.stateYesFilter(planList.get(i).getTodoList()));
		}
		long planCount = planReadService.countPlanByMy(user.getId(), null, null, null);
		var result = assembler.toModel(new PageImpl<PlanByUser>(planList, pageable, planCount));
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신의 모든 일정 가져오기(해당 팀의 끝난)")
	@GetMapping("/all/finished")
	public ResponseEntity<?> getMyPlanAllFinished(@RequestParam(defaultValue = "") String search, @RequestParam String code,
		@Current_User Users user, PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable)
		throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> planByUserList = planReadService.getPlanByMy(user.getId(), search, code, pageable, GetType.FINISHED);
		for (int i = 0; i < planByUserList.size(); i++) {
			planByUserList.get(i).setTodoList(TodoList.stateYesFilter(planByUserList.get(i).getTodoList()));
		}
		long totalCount = planReadService.countPlanByMy(user.getId(), search, code, GetType.FINISHED);
		var result = assembler.toModel(new PageImpl<>(planByUserList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신의 모든 일정 가져오기")
	@GetMapping("/all/unfinished")
	public ResponseEntity<?> getMyPlanAllUnfinished(@RequestParam(defaultValue = "") String search, String code,
		@Current_User Users user, PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable)
		throws TeamCodeNotFountException {
		if (code != null)
			teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> planByUserList = planReadService.getPlanByMy(user.getId(), search, code, pageable, GetType.NON);
		for (int i = 0; i < planByUserList.size(); i++) {
			planByUserList.get(i).setTodoList(TodoList.stateYesFilter(planByUserList.get(i).getTodoList()));
		}
		long totalCount = planReadService.countPlanByMy(user.getId(), search, code, GetType.NON);
		var result = assembler.toModel(new PageImpl<>(planByUserList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정 단건 조회")
	@GetMapping("/{seq}")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user)
		throws PlanByUserNotAuthException, NotFoundException, ljy_yjw.team_manage.system.exception.exceptions.NotFoundException,
		TeamCodeNotFountException, IOException {
		planAuthService.checkPlanAuth(seq, user);
		PlanByUser resultPlan = planReadService.getPlanByUser(seq);
		resultPlan.setTodoList(TodoList.stateYesFilter(resultPlan.getTodoList()));
		resultPlan.getUser().setMyImg(resultPlan.getUser().getImageByte(userSerivce));
		CustomEntityModel<PlanByUser> result = null;
		if (resultPlan.getUser().getId().equals(user.getId())) {
			result = new CustomEntityModel<PlanByUser>(resultPlan, this, Long.toString(resultPlan.getSeq()), Link.ALL);
		} else {
			result = new CustomEntityModel<PlanByUser>(resultPlan, this, Long.toString(resultPlan.getSeq()), Link.NOT_INCLUDE);
		}
		return ResponseEntity.ok(result);
	}

	@Memo("특정 팀의 마감된 일정을 가져오는 메소드")
	@GetMapping("/{code}/search/finished")
	public ResponseEntity<?> getSearchFinished(@PathVariable String code, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> resultPlanList = planReadService.getPlanList(code, pageable, null, GetType.FINISHED);
		for (int i = 0; i < resultPlanList.size(); i++) {
			resultPlanList.get(i).setTodoList(TodoList.stateYesFilter(resultPlanList.get(i).getTodoList()));
		}
		long totalCount = planReadService.getPlanCount(code, null, GetType.FINISHED);
		var result = assembler.toModel(new PageImpl<>(resultPlanList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드(특정 달)")
	@GetMapping("/{code}/search/all")
	public ResponseEntity<?> getSearchAll(@PathVariable String code, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
		@Current_User Users user, PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable)
		throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> resultPlanList = planReadService.getPlanList(code, pageable, date, GetType.SEARCH);
		for (int i = 0; i < resultPlanList.size(); i++) {
			resultPlanList.get(i).setTodoList(TodoList.stateYesFilter(resultPlanList.get(i).getTodoList()));
		}
		long totalCount = planReadService.getPlanCount(code, date, GetType.SEARCH);
		var result = assembler.toModel(new PageImpl<>(resultPlanList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("특정 일자에 진행중인 일정을 가져오는 메소드")
	@GetMapping("/{code}/search")
	public ResponseEntity<?> getSearch(@PathVariable String code,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> resultPlanList = PlanByUser.getMyPlanList(
			PlanByUser.getPlanList_TodoListSuccess(planReadService.getPlanList(code, pageable, date, GetType.NON)), user);
		for (int i = 0; i < resultPlanList.size(); i++) {
			resultPlanList.get(i).setTodoList(TodoList.stateYesFilter(resultPlanList.get(i).getTodoList()));
		}
		long totalCount = planReadService.getPlanCount(code, date, GetType.NON);
		var result = assembler.toModel(new PageImpl<>(resultPlanList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드")
	@GetMapping("/{code}/all")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user,
		PagedResourcesAssembler<PlanByUser> assembler, Pageable pageable) throws TeamCodeNotFountException, IOException {
		teamAuthService.checkTeamAuth(user, code);
		List<PlanByUser> resultPlanList = planReadService.getPlanList(code, pageable, null, GetType.NON);
		for (int i = 0; i < resultPlanList.size(); i++) {
			resultPlanList.get(i).setTodoList(TodoList.stateYesFilter(resultPlanList.get(i).getTodoList()));
		}
		for (int i = 0; i < resultPlanList.size(); i++) {
			PlanByUser updatePlan = resultPlanList.get(i);
			Users planUser = updatePlan.getUser();
			planUser.setMyImg(planUser.getImageByte(userSerivce));
			updatePlan.setUser(planUser);
		}
		long totalCount = planReadService.getPlanCount(code, null, GetType.NON);
		var result = assembler.toModel(new PageImpl<>(resultPlanList, pageable, totalCount));
		result.add(linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 등록하는 메소드")
	@PostMapping("/{code}")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid PlanByUserDTO planByUser, Errors error,
		@Current_User Users user) throws InputValidException, TeamCodeNotFountException, CheckInputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		Team team = teamAuthService.getTeamObject(code);
		if (team == null) {
			throw new TeamCodeNotFountException("해당하는 코드의 팀이 존재하지 않습니다.");
		}
		teamAuthService.checkTeamAuth(user, code);
		planByUser.isAfter();
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		PlanByUser savePlan = planByUserOneInsertService
			.insertPlanByUser(planByUser.parseThis2PlanByUser(planByUser, team, user));
		var result = new CustomEntityModel<>(savePlan, this, Long.toString(savePlan.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 수정하는 메소드")
	@PutMapping("/{seq}")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid PlanByUserDTO planByUser,
		@Current_User Users user, Errors error) throws PlanByUserNotAuthException, CheckInputValidException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		planByUser.isAfter();
		planAuthService.checkAuth(seq, user);
		PlanByUser updatePlan = planByUser.parseThis2PlanByUser(planByUser, null, null);
		updatePlan.setSeq(seq);
		updatePlan = planByUserOneUpdateService.updatePlanByUser(updatePlan);
		var result = new CustomEntityModel<>(updatePlan, this, Long.toString(seq), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@Memo("일정을 삭제하는 메소드")
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws PlanByUserNotAuthException {
		planAuthService.checkAuth(seq, user);
		PlanByUser deletePlan = planByUserOneDeleteService.deletePlan(seq);
		var result = new CustomEntityModel<>(deletePlan, this, Long.toString(seq), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}
}
