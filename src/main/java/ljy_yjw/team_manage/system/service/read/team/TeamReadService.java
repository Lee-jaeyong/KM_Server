package ljy_yjw.team_manage.system.service.read.team;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.TeamAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Service
public class TeamReadService {

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	TeamAPI teamAPI;

	@Memo("코드를 통해 팀의 정보를 가져옴")
	public Team getTeamByCode(String code) {
		return teamAPI.findByCode(code);
	}

	@Memo("만료된 팀을 가져옴")
	public List<Team> getTeamListFinished(String id) {
		return teamDAO.getTeamsFinished(id);
	}

	@Memo("만료되지 않은(진행중인) 팀을 가져옴")
	public List<Team> getTeamListUnFinished(String id) {
		return teamDAO.getTeamsUnfinished(id);
	}
}
