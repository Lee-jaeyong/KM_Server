package ljy_yjw.team_manage.system.service.auth.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.auth.AuthPlanByUserAPI;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;

@Service
public class PlanAuthService {

	@Autowired
	AuthPlanByUserAPI authPlanByUserAPI;
	
	@Memo("해당 일정에 대한 권한이 있는가를 체크하는 메소드")
	public void checkAuth(long seq,Users user) throws PlanByUserNotAuthException {
		if(!authPlanByUserAPI.existsBySeqAndUser_Id(seq, user.getId())) {
			throw new PlanByUserNotAuthException("해당 팀이 존재하지 않거나 자신의 일정이 아닙니다.");
		}
	}
	
}
