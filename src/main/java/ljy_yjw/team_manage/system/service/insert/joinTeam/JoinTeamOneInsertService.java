package ljy_yjw.team_manage.system.service.insert.joinTeam;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.TeamJoinRequestAPI;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Service
public class JoinTeamOneInsertService {

	@Autowired
	TeamJoinRequestAPI joinTeamAPI;
	
	public JoinTeam insertJoinTeam(Team team, Users user) {
		JoinTeam saveJoinTeam = JoinTeam.builder()
			.user(user)
			.team(team)
			.state(BooleanState.NO)
			.date(new Date())
			.build();
		return joinTeamAPI.save(saveJoinTeam);
	}
}
