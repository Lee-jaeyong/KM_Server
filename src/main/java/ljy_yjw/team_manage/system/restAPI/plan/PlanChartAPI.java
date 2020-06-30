package ljy_yjw.team_manage.system.restAPI.plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService;
import lombok.var;

@PlanController
public class PlanChartAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	PlanReadService planReadService;

	@Memo("팀의 월별 일정률 가져오기")
	@GetMapping("/{code}/progress-all")
	public ResponseEntity<?> getChartDataProgress(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		HashMap<String, Object> resultChartData = new HashMap<String, Object>();
		resultChartData.put("end", planReadService.getPlanCountByEndDate(code));
		resultChartData.put("start", planReadService.getPlanCountByStartDate(code));
		return ResponseEntity.ok(resultChartData);
	}

	@Memo("개인별 일정 개수 가져오기")
	@GetMapping("/{code}/group-by-user")
	public ResponseEntity<?> getChartDataGroupByUser(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		var result = new CollectionModel<>(planReadService.getPlanCountGroupByUser(code));
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
