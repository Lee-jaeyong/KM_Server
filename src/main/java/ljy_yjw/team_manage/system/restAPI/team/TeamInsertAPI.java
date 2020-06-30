package ljy_yjw.team_manage.system.restAPI.team;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.EntityFactory;
import ljy_yjw.team_manage.system.domain.dto.team.TeamInsertDTO;
import ljy_yjw.team_manage.system.domain.dto.valid.team.TeamValidator;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.insert.team.TeamOneInsertService;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;

@TeamController
public class TeamInsertAPI {

	@Autowired
	EntityFactory objectEntityFactory;

	@Autowired
	TeamOneInsertService teamOneInsertService;

	@Autowired
	TeamValidator teamValidator;

	@Autowired
	TeamReadService teamReadService;

	@Memo("팀을 등록하는 메소드")
	@PostMapping
	public ResponseEntity<?> createTeam(@RequestBody @Valid TeamInsertDTO team, Errors error, @Current_User Users user)
		throws InputValidException, CheckInputValidException, TeamCodeNotFountException, CanNotPerformException {
		teamValidator.validate(team, error);
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		Team searchTeam = teamReadService.getTeamByNameAndId(team.getName(), user.getId());
		if (searchTeam != null) {
			throw new CanNotPerformException("이미 등록한 팀입니다.");
		}
		Team saveTeam = teamOneInsertService.insertTeam(team.parseThis2Team(user));
		return ResponseEntity.ok(objectEntityFactory.get(saveTeam, saveTeam.getCode()));
	}
}
