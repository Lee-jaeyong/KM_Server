package ljy_yjw.team_manage.system.restAPI.plan;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.impl.ObjectEntityFactory;
import ljy_yjw.team_manage.system.domain.dto.plan.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.dto.valid.plan.PlanValidator;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.plan.PlanByUserOneInsertService;

@PlanController
public class PlanInsertAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	PlanByUserOneInsertService planByUserOneInsertService;

	@Autowired
	ObjectEntityFactory objectEntityFactory;

	@Autowired
	PlanValidator planValidator;

	@Memo("일정을 등록하는 메소드")
	@PostMapping("/{code}")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid PlanByUserDTO planByUser, Errors error,
		@Current_User Users user) throws InputValidException, TeamCodeNotFountException, CheckInputValidException {
		Team team = teamAuthService.getTeamObject(code);
		planValidator.validate(team, planByUser, error);

		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}

		teamAuthService.checkTeamAuth(user, code);
		PlanByUser savePlan = planByUserOneInsertService
			.insertPlanByUser(planByUser.parseThis2PlanByUser(planByUser, team, user));
		return ResponseEntity.ok(objectEntityFactory.get(savePlan, Long.toString(savePlan.getSeq())));
	}
}
