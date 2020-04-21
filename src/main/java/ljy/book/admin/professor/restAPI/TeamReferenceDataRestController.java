package ljy.book.admin.professor.restAPI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.ReferenceData;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamFreeBoardService;
import ljy.book.admin.professor.service.impl.TeamReferenceDataService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/referenceData")
public class TeamReferenceDataRestController {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamReferenceDataService teamReferenceDataService;

	@GetMapping("/{seq}")
	@Memo("참고자료 단건 조회 메소드")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user) {
		ReferenceData checkBoard = teamReferenceDataService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		EntityModel<ReferenceData> result = new EntityModel<ReferenceData>(checkBoard);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{code}/all")
	@Memo("특정 팀의 모든 참고자료를 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<ReferenceData> assembler) {
		if (!teamService.checkTeamAuth(user, code)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		PagedModel<EntityModel<ReferenceData>> result = assembler.toModel(teamReferenceDataService.getList(code, pageable));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/{code}")
	@Memo("참고자료를 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid ReferenceDataDTO referenceData,
		@Current_User Users user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		TeamDTO checkTeam = teamService.checkAuthByCodeSuccessThenGet(code, user);
		if (checkTeam == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		ReferenceData saveBoard = teamReferenceDataService.save(referenceData, checkTeam, user);
		EntityModel<ReferenceData> result = new EntityModel<ReferenceData>(saveBoard);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{seq}")
	@Memo("참고자료를 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid ReferenceDataDTO referenceData,
		@Current_User Users user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		ReferenceData checkBoard = teamReferenceDataService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		referenceData.setSeq(seq);
		teamReferenceDataService.update(referenceData);
		EntityModel<ReferenceDataDTO> result = new EntityModel<ReferenceDataDTO>(referenceData);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{seq}")
	@Memo("참고자료를 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) {
		ReferenceData checkBoard = teamReferenceDataService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamReferenceDataService.delete(seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
