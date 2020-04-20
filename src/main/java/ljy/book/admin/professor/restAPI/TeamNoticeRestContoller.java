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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.NoticeDTO;
import ljy.book.admin.professor.service.impl.TeamNoticeService;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/notice")
public class TeamNoticeRestContoller {

	@Autowired
	TeamNoticeService noticeService;

	@Autowired
	TeamService teamService;

	@GetMapping("/{code}")
	@Memo("팀의 모든 공지사항을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<Notice> assembler) {
		if (!teamService.checkTeamAuth(user, code)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		PagedModel<EntityModel<Notice>> result = assembler.toModel(noticeService.getAll(code, pageable));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/{code}")
	@Memo("팀장이 팀의 공지사항을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid NoticeDTO noticeDTO, @Current_User Users user,
		Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		Team team = teamService.checkTeamByUserAndCode(code, user);
		if (team == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Notice saveNotice = noticeService.save(noticeDTO, team, user);
		noticeDTO.setSeq(saveNotice.getSeq());
		EntityModel<NoticeDTO> result = new EntityModel<NoticeDTO>(noticeDTO);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{seq}")
	@Memo("팀장이 팀의 공지사항을 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid NoticeDTO noticeDTO, @Current_User Users user) {
		if (noticeService.checkAuthSuccessThenGet(seq, user) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		noticeService.update(seq, noticeDTO);
		noticeDTO.setSeq(seq);
		EntityModel<NoticeDTO> result = new EntityModel<NoticeDTO>(noticeDTO);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{seq}")
	@Memo("팀장이 팀의 공지사항을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) {
		if (noticeService.checkAuthSuccessThenGet(seq, user) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		noticeService.delete(seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
