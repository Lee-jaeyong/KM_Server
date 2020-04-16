package ljy.book.admin.professor.restAPI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage")
public class TeamRestController {

	@Autowired
	TeamService teamService;

	@Memo("팀을 등록하는 메소드")
	@PostMapping
	public ResponseEntity<?> createTeam(@RequestBody @Valid TeamDTO team, Errors error, @Current_User Users user) {
		if (error.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		teamService.save(team, user);
		EntityModel<TeamDTO> result = new EntityModel<TeamDTO>(team);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/update").withRel("update"));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/delete").withRel("delete"));
		return ResponseEntity.ok(result);
	}
}
