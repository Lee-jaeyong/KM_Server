package ljy.book.admin.professor.restAPI;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.ReferenceData;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
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
		if(!checkBoard.getUser().getId().equals(user.getId())) {
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
		if(!checkBoard.getUser().getId().equals(user.getId())) {
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

	@Memo("자유게시판 파일 업로드")
	@PostMapping("/{seq}/fileUpload/{type}")
	public ResponseEntity<?> fileUpload(@RequestParam MultipartFile[] files, @PathVariable long seq, @PathVariable FileType type,
		@Current_User Users user) {
		for (MultipartFile f : files) {
			if (!CustomFileUpload.fileUploadFilter(f.getOriginalFilename(), type))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		ReferenceData checkReferenceData = teamReferenceDataService.getOne(seq);
		if (checkReferenceData == null || !checkReferenceData.getUser().getId().equals(user.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		teamReferenceDataService.fileUpload(files, seq, type);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자유게시판 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user) {
		teamReferenceDataService.fileDelete(fileName, seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("공지사항 파일 다운로드")
	@GetMapping("/{seq}/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long seq, @PathVariable String fileName,
		HttpServletRequest request) {
		Resource resource = teamReferenceDataService.fileDownload(seq, fileName);
		if (resource == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
	}
}
