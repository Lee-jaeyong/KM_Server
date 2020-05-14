package ljy_yjw.team_manage.system.service.insert.team;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomCodeCreator;
import ljy_yjw.team_manage.system.dbConn.jpa.TeamAPI;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Service
public class TeamOneInsertService {

	@Autowired
	TeamAPI teamAPI;

	@Transactional
	@Memo("팀을 저장하는 메소드")
	public Team insertTeam(Team team) {
		Team saveTeam = teamAPI.save(team);
		saveTeam.setCode(new CustomCodeCreator().createCode(Long.toString(saveTeam.getSeq())));
		return saveTeam;
	}
}
