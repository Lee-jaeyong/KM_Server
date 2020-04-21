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
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.service.impl.TeamFreeBoardService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/freeBoard")
public class TeamFreeBoardRestController {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamFreeBoardService teamFreeBoardService;

	@GetMapping("/{seq}")
	@Memo("자유게시판 단건 조회 메소드")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user) {
		FreeBoard checkBoard = teamFreeBoardService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		EntityModel<FreeBoard> result = new EntityModel<FreeBoard>(checkBoard);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{code}/all")
	@Memo("특정 팀의 모든 자유게시판을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<FreeBoard> assembler) {
		if (!teamService.checkTeamAuth(user, code)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		PagedModel<EntityModel<FreeBoard>> result = assembler.toModel(teamFreeBoardService.getList(code, pageable));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/{code}")
	@Memo("자유 게시판을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		TeamDTO checkTeam = teamService.checkAuthByCodeSuccessThenGet(code, user);
		if (checkTeam == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		FreeBoard saveBoard = teamFreeBoardService.save(freeBoardDTO, checkTeam, user);
		EntityModel<FreeBoard> result = new EntityModel<FreeBoard>(saveBoard);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{seq}")
	@Memo("자유 게시판을 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		FreeBoard checkBoard = teamFreeBoardService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		freeBoardDTO.setSeq(seq);
		teamFreeBoardService.update(freeBoardDTO);
		EntityModel<FreeBoardDTO> result = new EntityModel<FreeBoardDTO>(freeBoardDTO);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{seq}")
	@Memo("자유 게시판을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) {
		FreeBoard checkBoard = teamFreeBoardService.getOne(seq);
		if (checkBoard == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		if (!teamService.checkTeamAuth(user, checkBoard.getTeam().getCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamFreeBoardService.delete(seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

}
