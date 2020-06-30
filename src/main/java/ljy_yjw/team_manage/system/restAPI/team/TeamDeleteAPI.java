package ljy_yjw.team_manage.system.restAPI.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.EntityFactory;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.team.TeamOneDeleteService;

@TeamController
public class TeamDeleteAPI {

	@Autowired
	EntityFactory objectEntityFactory;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamOneDeleteService teamOneDeleteService;

	@Memo("팀을 삭제하는 메소드")
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteTeam(@PathVariable String code, @Current_User Users user) throws NotTeamLeaderException {
		teamAuthService.checkTeamLeader(user, code);
		Team deleteTeam = teamOneDeleteService.deleteTeam(code, user.getId());
		return ResponseEntity.ok(objectEntityFactory.get(deleteTeam, deleteTeam.getCode()));
	}

	@Memo("팀을 탈퇴하는 메소드")
	@DeleteMapping("/{code}/out")
	public ResponseEntity<?> outTeam(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException, CanNotPerformException {
		try {
			teamAuthService.checkTeamLeader(user, code);
			throw new CanNotPerformException("팀장은 자신의 팀을 탈퇴할 수 없습니다.");
		} catch (NotTeamLeaderException e) {
			teamAuthService.checkTeamAuth(user, code);
			Team outTeam = teamOneDeleteService.outTeam(code, user.getId());
			return ResponseEntity.ok(objectEntityFactory.get(outTeam, outTeam.getCode()));
		}
	}
}
