package ljy_yjw.team_manage.system.restAPI.team;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.security.UsersService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.joinTeam.JoinTeamReadService;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import ljy_yjw.team_manage.system.service.update.team.TeamSettingService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;

@TeamController
public class TeamOneGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	UsersService userService;

	@Autowired
	JoinTeamReadService joinTeamReadService;

	@Autowired
	TeamSettingService teamSettingService;

	@Autowired
	UserSettingService userSettingService;
	
	@Memo("코드를 통해 팀의 상세 정보를 가져옴")
	@GetMapping("/{code}")
	public ResponseEntity<?> getTeamInfo(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException, IOException {
		teamAuthService.checkTeamAuth(user, code);
		Team team = teamReadService.getTeamByCode(code);
		userSettingService.imgSetting(team.getTeamLeader());
		List<JoinTeam> joinPerson = joinTeamReadService.getRealJoinPerson(team.getJoinPerson());
		teamSettingService.settingImg(team, joinPerson);
		return ResponseEntity.ok(team);
	}

}
