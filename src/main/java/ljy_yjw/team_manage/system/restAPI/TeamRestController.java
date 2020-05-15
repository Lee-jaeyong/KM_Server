package ljy_yjw.team_manage.system.restAPI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.TeamDTO;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.OtherInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.AlreadyAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.joinTeam.CanNotAppliedException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.joinTeam.JoinTeamAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.team.TeamOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.joinTeam.JoinTeamOneInsertService;
import ljy_yjw.team_manage.system.service.insert.team.TeamOneInsertService;
import ljy_yjw.team_manage.system.service.read.joinTeam.JoinTeamReadService;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import ljy_yjw.team_manage.system.service.update.joinTeam.JoinTeamOneUpdateService;
import ljy_yjw.team_manage.system.service.update.team.TeamOneUpdateService;
import lombok.var;

@RestController
@RequestMapping("/api/teamManage")
public class TeamRestController {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	JoinTeamAuthService joinTeamAuthService;

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	JoinTeamReadService joinTeamReadService;

	@Autowired
	TeamOneInsertService teamOneInsertService;

	@Autowired
	JoinTeamOneInsertService joinTeamOneInsertService;

	@Autowired
	TeamOneUpdateService teamOneUpdateService;

	@Autowired
	JoinTeamOneUpdateService joinTeamOneUpdateService;

	@Autowired
	TeamOneDeleteService teamOneDeleteService;

	@Memo("코드를 통해 팀의 상세 정보를 가져옴")
	@GetMapping("/{code}")
	public ResponseEntity<?> getTeamInfo(@PathVariable String code, @Current_User Users user) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code); // 팀 코드가 존재하지 않으면 TeamCodeNotFoundException 발생
		Team team = teamReadService.getTeamByCode(code);
		Link link = Link.NOT_INCLUDE;
		if (team.getTeamLeader().getId().equals(user.getId())) {
			link = Link.ALL;
		}
		CustomEntityModel<Team> result = new CustomEntityModel<Team>(team, this, code, link);
		return ResponseEntity.ok(result);
	}

	@Memo("자신이 팀장이고, 승인요청을 명단 가져오기")
	@GetMapping("/{code}/signUpList")
	public ResponseEntity<?> getSignUpList(@PathVariable String code, @Current_User Users user) throws NotTeamLeaderException {
		teamAuthService.checkTeamLeader(user, code);
		List<CustomEntityModel<JoinTeam>> joinTeamList = joinTeamReadService.getJoinTeamList(code).stream()
			.map(c -> new CustomEntityModel<>(c, this, Long.toString(c.getSeq()), Link.NOT_INCLUDE)).collect(Collectors.toList());
		CollectionModel<CustomEntityModel<JoinTeam>> result = new CollectionModel<>(joinTeamList);
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자신이 소속되어있는 모든 팀 정보 가져오기")
	@GetMapping
	public ResponseEntity<?> getJoinTeamFinished(@Current_User Users user, @RequestParam(required = false) String flag) {
		CollectionModel<CustomEntityModel<Team>> result;
		if (flag != null && flag.equals("finished")) {
			List<CustomEntityModel<Team>> teamList = teamReadService.getTeamListFinished(user.getId()).stream()
				.map(c -> new CustomEntityModel<>(c, this, c.getCode(), Link.NOT_INCLUDE)).collect(Collectors.toList());
			result = new CollectionModel<>(teamList);
		} else {
			List<CustomEntityModel<Team>> teamList = teamReadService.getTeamListUnFinished(user.getId()).stream()
				.map(c -> c.getTeamLeader().getId().equals(user.getId()) ? new CustomEntityModel<>(c, this, c.getCode(), Link.ALL)
					: new CustomEntityModel<>(c, this, c.getCode(), Link.NOT_INCLUDE))
				.collect(Collectors.toList());
			result = new CollectionModel<>(teamList);
		}
		return ResponseEntity.ok(result);
	}

	@Memo("팀을 등록하는 메소드")
	@PostMapping
	public ResponseEntity<?> createTeam(@RequestBody @Valid TeamDTO team, Errors error, @Current_User Users user)
		throws InputValidException, CheckInputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		team.isAfter();
		Team saveTeam = teamOneInsertService.insertTeam(team.parseThis2Team(user));
		CustomEntityModel<Team> result = new CustomEntityModel<>(saveTeam, this, saveTeam.getCode(), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@Memo("팀을 수정하는 메소드")
	@PutMapping("/{code}")
	public ResponseEntity<?> updateTeam(@PathVariable String code, @RequestBody @Valid TeamDTO team, Errors error,
		@Current_User Users user)
		throws InputValidException, NotTeamLeaderException, OtherInputValidException, CheckInputValidException {
		teamAuthService.checkTeamLeader(user, code);
		team.isAfter();
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		team.setCode(code);
		Team updateTeam = teamOneUpdateService.updateTeam(team.parseThis2Team(user));
		CustomEntityModel<Team> result = new CustomEntityModel<>(updateTeam, this, updateTeam.getCode(), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@Memo("팀을 삭제하는 메소드")
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteTeam(@PathVariable String code, @Current_User Users user) throws NotTeamLeaderException {
		teamAuthService.checkTeamLeader(user, code);
		Team deleteTeam = teamOneDeleteService.deleteTeam(code);
		CustomEntityModel<Team> result = new CustomEntityModel<Team>(deleteTeam, this, "", Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

	@Memo("코드 번호를 통해 팀의 유무를 확인 후 있다면 그 팀의 팀장에게 승인요청을 보내는 메소드")
	@PostMapping("/{code}/joinTeam")
	public ResponseEntity<?> checkTeamByCode(@PathVariable String code, @Current_User Users user)
		throws TeamCodeNotFountException, CanNotAppliedException, AlreadyAppliedException {
		Team findTeam = teamReadService.getTeamByCode(code);
		if (findTeam == null) {
			throw new TeamCodeNotFountException("해당 코드의 팀이 존재하지 않습니다.");
		}
		joinTeamAuthService.beforeInsertJoinTeamAuthCheck(findTeam.getCode(), user);
		JoinTeam saveJoinTeam = joinTeamOneInsertService.insertJoinTeam(findTeam, user);
		var result = new CustomEntityModel<>(saveJoinTeam, this, "", Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

	@Memo("팀 승인요청을 수락하는 메소드")
	@PutMapping("/{seq}/joinTeam/success")
	public ResponseEntity<?> successJoinTeam(@PathVariable long seq, @Current_User Users user)
		throws NotTeamLeaderException, NotFoundException {
		JoinTeam checkJoinTeam = joinTeamReadService.getJoinTeam(seq);
		if (checkJoinTeam == null) {
			throw new NotFoundException("해당 승인 요청 번호가 존재하지 않습니다.");
		}
		teamAuthService.checkTeamLeader(user, checkJoinTeam.getTeam().getCode());
		JoinTeam successJoinTeam = joinTeamOneUpdateService.signUpSuccess(seq);
		var result = new CustomEntityModel<>(successJoinTeam, this, "", Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

	@Memo("팀 승인요청을 반려하는 메소드")
	@PutMapping("/{seq}/joinTeam/faild")
	public ResponseEntity<?> faildJoinTeam(@PathVariable long seq, String reson, @Current_User Users user)
		throws NotFoundException, CanNotPerformException {
		JoinTeam checkJoinTeam = joinTeamReadService.getJoinTeam(seq);
		if (checkJoinTeam == null) {
			throw new NotFoundException("해당 승인 요청 번호가 존재하지 않습니다.");
		}
		if (checkJoinTeam.getState() == BooleanState.YES) {
			throw new CanNotPerformException("이미 승인된 요청 번호입니다.");
		}
		JoinTeam faildJoinTeam = joinTeamOneUpdateService.signUpFaild(reson, seq);
		var result = new CustomEntityModel<>(faildJoinTeam, this, "", Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}
}
