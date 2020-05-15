package ljy_yjw.team_manage.system.service.update.team;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Service
public class TeamOneUpdateService {

	@Autowired
	TeamDAO teamDAO;

	@Transactional
	public Team updateTeam(Team team) {
		teamDAO.update(team);
		return team;
	}
}
