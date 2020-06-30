package ljy_yjw.team_manage.system.restAPI.team;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.EntityFactory;
import ljy_yjw.team_manage.system.domain.dto.team.TeamUpdateDTO;
import ljy_yjw.team_manage.system.domain.dto.valid.team.TeamUnFinishValidator;
import ljy_yjw.team_manage.system.domain.dto.valid.team.TeamValidator;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.OtherInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.update.team.TeamOneUpdateService;

@TeamController
public class TeamUpdateAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamOneUpdateService teamOneUpdateService;

	@Autowired
	EntityFactory objectEntityFactory;

	@Autowired
	TeamValidator teamValidator;

	@Autowired
	TeamUnFinishValidator teamUnFinishValidator;

	@Memo("팀을 수정하는 메소드")
	@PutMapping("/{code}")
	public ResponseEntity<?> updateTeam(@PathVariable String code, @RequestBody @Valid TeamUpdateDTO team, Errors error,
		@Current_User Users user)
		throws InputValidException, NotTeamLeaderException, OtherInputValidException, CheckInputValidException {
		teamValidator.validate(team, error);
		teamAuthService.checkTeamLeader(user, code);
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		team.setCode(code);
		Team updateTeam = teamOneUpdateService.updateTeam(team.parseThis2Team(user));
		return ResponseEntity.ok(objectEntityFactory.get(updateTeam, updateTeam.getCode()));
	}

	@Memo("팀을 마감시키는 메소드")
	@PatchMapping("/finish/{code}")
	public ResponseEntity<?> finishTeam(@PathVariable String code, @Current_User Users user) throws NotTeamLeaderException {
		teamAuthService.checkTeamLeader(user, code);
		Team finishTeam = teamOneUpdateService.finishedTeam(code);
		return ResponseEntity.ok(objectEntityFactory.get(finishTeam, finishTeam.getCode()));
	}

	@Memo("마감된 팀을 소생시키는 메소드")
	@PatchMapping("/unfinish/{code}")
	public ResponseEntity<?> unFinishedTeam(@PathVariable String code, @Current_User Users user)
		throws InputValidException, TeamCodeNotFountException, NotTeamLeaderException {
		teamAuthService.checkTeamLeader(user, code);
		Team team = teamAuthService.getTeamObject(code);

		Errors error = new BeanPropertyBindingResult(team, "team");
		teamUnFinishValidator.validate(team, error);
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}

		Team unfinishTeam = teamOneUpdateService.unFinishedTeam(code);
		return ResponseEntity.ok(objectEntityFactory.get(unfinishTeam, unfinishTeam.getCode()));
	}
}
