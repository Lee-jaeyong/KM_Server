package ljy_yjw.team_manage.system.service.auth.team;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.TeamAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;

@Service
public class TeamAuthService {

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	TeamAPI teamAPI;

	@Memo("팀 오브젝트를 가져오는 메소드")
	public Team getTeamObject(String code) throws TeamCodeNotFountException {
		Team team = teamAPI.getTeamObject(code);
		if (team == null) {
			throw new TeamCodeNotFountException("팀이 존재하지 않거나 해당 팀에 대한 권한이 존재하지 않습니다.");
		}
		return team;
	}

	@Memo("팀에 대한 권한이 존재하는지 확인하는 메소드")
	public void checkTeamAuth(Users user, String code) throws TeamCodeNotFountException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("code", code);
		if (!teamDAO.checkTeamAuthBool(map)) {
			throw new TeamCodeNotFountException("팀이 존재하지 않거나 해당 팀에 대한 권한이 존재하지 않습니다.");
		}
	}

	@Memo("사용자가 팀의 팀장인지를 확인하는 메소드")
	public void checkTeamLeader(Users user, String code) throws NotTeamLeaderException {
		if (!teamAPI.existsByCodeAndTeamLeader_Id(code, user.getId())) {
			throw new NotTeamLeaderException("팀장의 권한이 존재하지 않습니다.");
		}
	}

	@Memo("해당 코드의 팀이 존재하는지 체크하는 메소드")
	public void checkExistTeam(String code) throws TeamCodeNotFountException {
		if (!teamAPI.existsByCode(code)) {
			throw new TeamCodeNotFountException("해당 팀이 존재하지 않습니다.");
		}
	}
}
