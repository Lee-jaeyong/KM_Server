package ljy_yjw.team_manage.system.restAPI.plan;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;

@PlanController
public class PlanOneGetAPI {
	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	PlanReadService planReadService;

	@Autowired
	UserSettingService userSettingService;

	@Memo("일정 단건 조회")
	@GetMapping("/{seq}")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user)
		throws PlanByUserNotAuthException, NotFoundException, ljy_yjw.team_manage.system.exception.exceptions.NotFoundException,
		TeamCodeNotFountException, IOException {
		planAuthService.checkPlanAuth(seq, user);
		PlanByUser resultPlan = planReadService.getPlanByUser(seq);
		resultPlan.setTodoList(TodoList.stateYesFilter(resultPlan.getTodoList()));
		userSettingService.imgSetting(resultPlan.getUser());
		return ResponseEntity.ok(getCustomEntityModel(resultPlan, user));
	}

	private CustomEntityModel<PlanByUser> getCustomEntityModel(PlanByUser resultPlan, Users user) {
		if (resultPlan.getUser().getId().equals(user.getId())) {
			return new CustomEntityModel<PlanByUser>(resultPlan, this, Long.toString(resultPlan.getSeq()), Link.ALL);
		}
		return new CustomEntityModel<PlanByUser>(resultPlan, this, Long.toString(resultPlan.getSeq()), Link.NOT_INCLUDE);
	}
}
