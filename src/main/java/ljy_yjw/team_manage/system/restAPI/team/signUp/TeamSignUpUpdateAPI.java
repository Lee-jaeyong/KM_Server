package ljy_yjw.team_manage.system.restAPI.team.signUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.EntityFactory;
import ljy_yjw.team_manage.system.domain.dto.valid.team.JoinTeamValidator;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.AlreadyAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.CanNotAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.joinTeam.JoinTeamAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.joinTeam.JoinTeamOneInsertService;
import ljy_yjw.team_manage.system.service.read.joinTeam.JoinTeamReadService;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import ljy_yjw.team_manage.system.service.update.joinTeam.JoinTeamOneUpdateService;

@TeamController
public class TeamSignUpUpdateAPI {

	@Autowired
	JoinTeamReadService joinTeamReadService;

	@Autowired
	JoinTeamOneUpdateService joinTeamOneUpdateService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	JoinTeamAuthService joinTeamAuthService;

	@Autowired
	JoinTeamOneInsertService joinTeamOneInsertService;

	@Autowired
	JoinTeamValidator joinTeamValidator;

	@Autowired
	EntityFactory objectEntityFactory;
	
	@Memo("코드 번호를 통해 팀의 유무를 확인 후 있다면 그 팀의 팀장에게 승인요청을 보내는 메소드")
	@PostMapping("/{code}/joinTeam")
	public ResponseEntity<?> checkTeamByCode(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException, CanNotAppliedException, AlreadyAppliedException, InputValidException {
		Team findTeam = teamReadService.getTeamByCode(code);
		teamAuthService.checkTeamFinished(findTeam, joinTeamValidator);
		joinTeamAuthService.beforeInsertJoinTeamAuthCheck(findTeam.getCode(), user);
		JoinTeam saveJoinTeam = joinTeamOneInsertService.insertJoinTeam(findTeam, user);
		return ResponseEntity.ok(objectEntityFactory.get(saveJoinTeam, Long.toString(saveJoinTeam.getSeq())));
	}

	@Memo("팀 승인요청을 수락하는 메소드")
	@PutMapping("/{seq}/joinTeam/success")
	public ResponseEntity<?> successJoinTeam(@PathVariable long seq, @Current_User Users user)
		throws NotTeamLeaderException, NotFoundException, InputValidException {
		JoinTeam checkJoinTeam = joinTeamReadService.getJoinTeam(seq);
		Team joinTeam = checkJoinTeam.getTeam();
		
		teamAuthService.checkTeamFinished(joinTeam, joinTeamValidator);
		
		teamAuthService.checkTeamLeader(user, joinTeam.getCode());
		JoinTeam successJoinTeam = joinTeamOneUpdateService.signUpSuccess(seq);
		return ResponseEntity.ok(objectEntityFactory.get(successJoinTeam, Long.toString(successJoinTeam.getSeq())));
	}

	@Memo("팀 승인요청을 반려하는 메소드")
	@PutMapping("/{seq}/joinTeam/faild")
	public ResponseEntity<?> faildJoinTeam(@PathVariable long seq, String reson, @Current_User Users user)
		throws NotFoundException, CanNotPerformException {
		JoinTeam checkJoinTeam = joinTeamReadService.getJoinTeam(seq);

		if (checkJoinTeam.getState() == BooleanState.YES) {
			throw new CanNotPerformException("이미 승인된 요청 번호입니다.");
		}
		
		JoinTeam faildJoinTeam = joinTeamOneUpdateService.signUpFaild(reson, seq);
		return ResponseEntity.ok(objectEntityFactory.get(faildJoinTeam, Long.toString(faildJoinTeam.getSeq())));
	}
	
}
