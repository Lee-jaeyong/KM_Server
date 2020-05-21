package ljy_yjw.team_manage.system.service.delete.team;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Service
public class TeamOneDeleteService {

	@Autowired
	TeamDAO teamDAO;

	public Team deleteTeam(String code) {
		Team team = Team.builder().code(code).build();
		teamDAO.delete(code);
		return team;
	}

	public Team outTeam(String code, String id) {
		Team team = Team.builder().code(code).build();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("id", id);
		teamDAO.outTeam(map);
		return team;
	}
}
