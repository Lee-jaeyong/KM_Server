package ljy_yjw.team_manage.system.restAPI.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.delete.plan.PlanByUserOneDeleteService;
import lombok.var;

@PlanController
public class PlanDeleteAPI {

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	PlanByUserOneDeleteService planByUserOneDeleteService;

	@Memo("일정을 삭제하는 메소드")
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws PlanByUserNotAuthException {
		Team team = planAuthService.checkAuthThenGet(seq, user);
		PlanByUser planByUser = PlanByUser.builder().seq(seq).team(team).build();
		PlanByUser deletePlan = planByUserOneDeleteService.deletePlan(planByUser);
		var result = new CustomEntityModel<>(deletePlan, this, Long.toString(seq), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}
}
