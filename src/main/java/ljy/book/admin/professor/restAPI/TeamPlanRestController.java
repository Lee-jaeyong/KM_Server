package ljy.book.admin.professor.restAPI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamPlanService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/plan")
public class TeamPlanRestController {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamPlanService teamPlanService;

	@Memo("일정을 등록하는 메소드")
	@PostMapping("/{team}")
	public ResponseEntity<?> save(@PathVariable TeamDTO team, @RequestBody @Valid PlanByUserDTO planByUser, Errors error,
		@Current_User Users user) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (teamService.checkAuthSuccessThenGetTeam(team.getSeq(), user) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (planByUser.getTeamPlan() == BooleanState.YSE && !teamService.checkTeamByUser(team, user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		teamPlanService.save(team.getSeq(), planByUser, user);
		EntityModel<PlanByUserDTO> result = new EntityModel<PlanByUserDTO>(planByUser);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
