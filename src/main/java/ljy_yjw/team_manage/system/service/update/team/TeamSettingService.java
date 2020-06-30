package ljy_yjw.team_manage.system.service.update.team;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.security.UsersService;
import ljy_yjw.team_manage.system.service.update.team.SignUp.JoinTeamSettingService;

@Service
public class TeamSettingService {

	@Autowired
	UsersService userService;

	@Autowired
	JoinTeamSettingService joinTeamSettingService;

	public void settingImg(Team team, List<JoinTeam> joinPerson) throws IOException {
		joinTeamSettingService.imgSetting(joinPerson);
		Users leader = team.getTeamLeader();
		leader.setMyImg(leader.getImageByte(userService));
		team.setJoinPerson(joinPerson);
		team.setTeamLeader(leader);
	}
}
