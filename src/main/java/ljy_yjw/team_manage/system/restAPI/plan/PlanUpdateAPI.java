package ljy_yjw.team_manage.system.restAPI.plan;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.plan.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.update.plan.PlanByUserOneUpdateService;
import lombok.var;

@PlanController
public class PlanUpdateAPI {

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	PlanByUserOneUpdateService planByUserOneUpdateService;

	@Memo("일정을 수정하는 메소드")
	@PutMapping("/{seq}")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid PlanByUserDTO planByUser,
		@Current_User Users user, Errors error) throws PlanByUserNotAuthException, CheckInputValidException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
//		planByUser.isAfter("시작일은 종료일보다 작아야합니다.");
		Team team = planAuthService.checkAuthThenGet(seq, user);
		PlanByUser updatePlan = planByUser.parseThis2PlanByUser(planByUser, team, null);
		updatePlan.setSeq(seq);
		updatePlan = planByUserOneUpdateService.updatePlanByUser(updatePlan);
		var result = new CustomEntityModel<>(updatePlan, this, Long.toString(seq), Link.ALL);
		return ResponseEntity.ok(result);
	}
}
