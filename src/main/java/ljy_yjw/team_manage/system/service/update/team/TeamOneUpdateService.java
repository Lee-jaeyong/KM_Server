package ljy_yjw.team_manage.system.service.update.team;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Service
@Transactional
public class TeamOneUpdateService {

	@Autowired
	TeamDAO teamDAO;

	public Team updateTeam(Team team) {
		teamDAO.update(team);
		return team;
	}

	public Team finishedTeam(String code) {
		Team team = createTeam(code);
		teamDAO.finished(team);
		return team;
	}

	public Team unFinishedTeam(String code) {
		Team team = createTeam(code);
		teamDAO.unFinished(team);
		return team;
	}

	private Team createTeam(String code) {
		Team team = new Team();
		team.setCode(code);
		return team;
	}
}
