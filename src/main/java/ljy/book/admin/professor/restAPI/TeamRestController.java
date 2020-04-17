package ljy.book.admin.professor.restAPI;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.professor.requestDTO.JoinTeamDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamJoinRequestService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage")
public class TeamRestController {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamJoinRequestService teamJoinRequestService;

	@Memo("자신이 소속되어있는 모든 팀 정보 가져오기(기간이 만료된)")
	@GetMapping("/finished")
	public ResponseEntity<?> getJoinTeamFinished(@Current_User Users user) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("_embedded", teamService.getTeamsFinished(user));
		resultMap.put("_links", ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		resultMap.put("profile", ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(resultMap);
	}
	
	@Memo("자신이 소속되어있는 모든 팀 정보 가져오기(기간이 만료되지 않은)")
	@GetMapping
	public ResponseEntity<?> getJoinTeam(@Current_User Users user) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("_embedded", teamService.getTeamsUnfinished(user));
		resultMap.put("_links", ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		resultMap.put("profile", ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(resultMap);
	}

	@Memo("팀을 수정하는 메소드")
	@PutMapping("/{teamseq}")
	public ResponseEntity<?> updateTeam(@PathVariable TeamDTO teamseq, @RequestBody @Valid TeamDTO team, Errors error,
		@Current_User Users user) {
		if (error.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		if (!teamService.checkTeamByUser(teamseq, user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		teamService.update(team);
		EntityModel<TeamDTO> result = new EntityModel<TeamDTO>(team);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withRel("delete"));
		return ResponseEntity.ok(result);
	}

	@Memo("팀을 등록하는 메소드")
	@PostMapping
	public ResponseEntity<?> createTeam(@RequestBody @Valid TeamDTO team, Errors error, @Current_User Users user) {
		if (error.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		Team saveTeam = teamService.save(team, user);
		team.setSeq(saveTeam.getSeq());
		team.setCode(saveTeam.getCode());
		EntityModel<TeamDTO> result = new EntityModel<TeamDTO>(team);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withRel("update"));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withRel("delete"));
		return ResponseEntity.ok(result);
	}

	@Memo("팀을 삭제하는 메소드")
	@DeleteMapping("/{team}")
	public ResponseEntity<?> deleteTeam(@PathVariable TeamDTO team, @Current_User Users user) {
		if (!teamService.checkTeamByUser(team, user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		teamService.delete(team);
		EntityModel<TeamDTO> result = new EntityModel<TeamDTO>(team);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withRel("insert"));
		return ResponseEntity.ok(result);
	}

	@Memo("팀의 전체 진척도를 수정하는 메소드")
	@PatchMapping("/{team}")
	public ResponseEntity<?> updateCounter(@PathVariable TeamDTO team, @RequestBody TeamDTO teamContainProgess,
		@Current_User Users user) {
		if (!teamService.checkTeamByUser(team, user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		if (teamContainProgess.getProgress() > 100) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		teamService.updateProgress(team, teamContainProgess);
		EntityModel<TeamDTO> result = new EntityModel<TeamDTO>(team);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(result);
	}

	@Memo("코드 번호를 통해 팀의 유무를 확인 후 있다면 그 팀의 팀장에게 승인요청을 보내는 메소드")
	@PostMapping("/{code}/joinTeam")
	public ResponseEntity<?> checkTeamByCode(@PathVariable String code, @Current_User Users user) {
		// 팀장이 자기 자신의 팀에게 승인 요청을 보낼 수 없음
		if (teamService.checkTeamByUserAndCode(code, user)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		// 이미 신청한 경우
		if (teamJoinRequestService.checkExistRequest(code, user)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		Team findTeam = teamService.getTeamByCode(code);
		if (findTeam == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		JoinTeam joinTeam = teamJoinRequestService.saveJoinTeamReq(findTeam, user);
		JoinTeamDTO joinTeamResult = new JoinTeamDTO();
		joinTeamResult.setDate(joinTeam.getDate());
		joinTeamResult.setSeq(joinTeam.getSeq());
		joinTeamResult.setState(joinTeam.getState());
		EntityModel<JoinTeamDTO> result = new EntityModel<JoinTeamDTO>(joinTeamResult);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(result);
	}

	@Memo("팀 승인요청을 수락하는 메소드")
	@PatchMapping("/{seq}/joinTeam")
	public ResponseEntity<?> successJoinTeam(@PathVariable long seq, @Current_User Users user) {
		JoinTeam checkAuth = teamJoinRequestService.checkJoinTeamAuth(seq, user);
		if (checkAuth == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamJoinRequestService.signUpSuccessJoinTeam(seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(result);
	}

	@Memo("팀 승인요청을 반려하는 메소드")
	@PatchMapping("/{seq}/joinTeam/faild")
	public ResponseEntity<?> faildJoinTeam(@PathVariable long seq, String reson, @Current_User Users user) {
		JoinTeam checkAuth = teamJoinRequestService.checkJoinTeamAuth(seq, user);
		if (checkAuth == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (checkAuth.getState() == BooleanState.YSE) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		teamJoinRequestService.signUpFaildJoinTeam(seq, reson);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(result);
	}
}
