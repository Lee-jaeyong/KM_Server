package ljy_yjw.team_manage.system.service.auth.joinTeam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.TeamJoinRequestAPI;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.AlreadyAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.CanNotAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;

@Service
public class JoinTeamAuthService {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamJoinRequestAPI teamJoinRequestAPI;

	public void beforeInsertJoinTeamAuthCheck(String code, Users user) throws CanNotAppliedException, AlreadyAppliedException {
		try {
			teamAuthService.checkTeamLeader(user, code);
			throw new CanNotAppliedException("팀장은 자신의 팀에게 승인요청을 할 수 없습니다.");
		} catch (NotTeamLeaderException e) {
			alreadyAppliedCheck(code, user.getId());
		}
	}

	public void existsJoinTeam(long seq) {
		if(!teamJoinRequestAPI.existsById(seq)) {
			
		}
	}
	
	public void alreadyAppliedCheck(String code, String id) throws AlreadyAppliedException {
		if (teamJoinRequestAPI.existsByTeam_CodeAndUser_Id(code, id)) {
			throw new AlreadyAppliedException("이미 신청한 팀입니다.");
		}
	}
	
}
