package ljy_yjw.team_manage.system.restAPI.team.signUp;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.projections.MyJoinTeam;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.joinTeam.JoinTeamReadService;
import ljy_yjw.team_manage.system.service.update.team.SignUp.JoinTeamSettingService;
import lombok.var;

@TeamController
public class TeamSignUpGetAPI {

	@Autowired
	JoinTeamReadService joinTeamReadService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	JoinTeamSettingService joinTeamSettingService;

	@Memo("자신이 신청한 팀 정보 가져오기")
	@GetMapping("/signUpList")
	public ResponseEntity<?> getMySignUpList(@Current_User Users user) {
		List<MyJoinTeam> joinTeamList = joinTeamReadService.getMyJoinTeam(user.getId());
		var result = new CollectionModel<>(joinTeamList);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신이 팀장이고, 승인요청을 명단 가져오기")
	@GetMapping("/{code}/signUpList")
	public ResponseEntity<?> getSignUpList(@PathVariable String code, @Current_User Users user)
		throws NotTeamLeaderException, IOException {
		teamAuthService.checkTeamLeader(user, code);
		List<JoinTeam> joinTeamList = joinTeamReadService.getJoinTeamList(code);

		joinTeamSettingService.filterStateNo(joinTeamList);
		joinTeamSettingService.imgSetting(joinTeamList);

		return ResponseEntity.ok(returnCollectionModel(joinTeamList));
	}

	private CollectionModel<?> returnCollectionModel(List<?> list) {
		var result = new CollectionModel<>(list);
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return result;
	}
}
