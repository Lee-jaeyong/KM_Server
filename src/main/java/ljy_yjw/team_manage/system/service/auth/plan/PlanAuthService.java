package ljy_yjw.team_manage.system.service.auth.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.auth.AuthPlanByUserAPI;
import ljy_yjw.team_manage.system.dbConn.jpa.plan.PlanByUserAPI;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;

@Service
public class PlanAuthService {

	@Autowired
	AuthPlanByUserAPI authPlanByUserAPI;

	@Autowired
	PlanByUserAPI planByUserAPI;

	@Autowired
	TeamAuthService teamAuthService;

	@Memo("해당 일정을 볼 권한이 있는가를 체크하는 메소드")
	public void checkPlanAuth(long seq, Users user)
		throws PlanByUserNotAuthException, NotFoundException, TeamCodeNotFountException {
		PlanByUser plan = planByUserAPI.findBySeq(seq);
		if (plan == null) {
			throw new NotFoundException("해당 번호의 일정이 존재하지 않습니다.");
		}
		teamAuthService.checkTeamAuth(user, plan.getTeam().getCode());
	}

	@Memo("해당 일정에 대한 권한이 있는가를 체크하는 메소드")
	public void checkAuth(long seq, Users user) throws PlanByUserNotAuthException {
		if (!authPlanByUserAPI.existsBySeqAndUser_Id(seq, user.getId())) {
			throw new PlanByUserNotAuthException("해당 팀이 존재하지 않거나 자신의 일정이 아닙니다.");
		}
	}

}
