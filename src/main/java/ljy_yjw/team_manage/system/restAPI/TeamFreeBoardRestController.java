package ljy_yjw.team_manage.system.restAPI;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
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

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.domain.dto.FreeBoardDTO;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.FileUploadException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.freeBoard.FreeBoardAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.freeBoard.FreeBoardOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.freeBoard.FreeBoardOneInsertService;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;
import ljy_yjw.team_manage.system.service.update.freeBoard.FreeBoardOneUpdateService;
import lombok.var;

@RestController
@RequestMapping("/api/teamManage/freeBoard")
public class TeamFreeBoardRestController {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	FreeBoardAuthService freeBoardAuthService;

	@Autowired
	FreeBoardReadService freeBoardReadService;

	@Autowired
	FreeBoardOneInsertService freeBoardOneInsertService;

	@Autowired
	FreeBoardOneUpdateService freeBoardOneUpdateService;

	@Autowired
	FreeBoardOneDeleteService freeBoardOneDeleteService;

	@GetMapping("/{seq}")
	@Memo("자유게시판 단건 조회 메소드")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user)
		throws NotFoundException, TeamCodeNotFountException {
		FreeBoard freeBoard = freeBoardReadService.getFreeBoard(seq);
		teamAuthService.checkTeamAuth(user, freeBoard.getTeam().getCode());
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		CustomEntityModel<FreeBoard> result = null;
		if (freeBoard.getUser().getId().equals(user.getId()))
			result = new CustomEntityModel<>(freeBoard, this, Long.toString(freeBoard.getSeq()), Link.ALL);
		else
			result = new CustomEntityModel<>(freeBoard, this, Long.toString(freeBoard.getSeq()), Link.NOT_INCLUDE);
		ArrayList<byte[]> imgByte = new ArrayList<byte[]>();
		freeBoard.getFileList().forEach(c -> {
			if (c.getType() == FileType.IMG) {
				try {
					InputStream in = freeBoardReadService.fileDownload(seq, c.getName()).getInputStream();
					imgByte.add(IOUtils.toByteArray(in));
				} catch (Exception e) {
				}
			}
		});
		;
		jsonResult.put("data", result);
		jsonResult.put("image", imgByte);
		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/{code}/all")
	@Memo("특정 팀의 모든 자유게시판을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<FreeBoard> assembler) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<FreeBoard> freeBoardList = freeBoardReadService.getFreeBoardList(code, pageable);
		long totalCount = freeBoardReadService.countFreeBoard(code);
		var result = assembler.toModel(new PageImpl<>(freeBoardList, pageable, totalCount));
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/{code}")
	@Memo("자유 게시판을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) throws InputValidException, TeamCodeNotFountException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		teamAuthService.checkTeamAuth(user, code);
		Team team = teamAuthService.getTeamObject(code);
		FreeBoard freeBoard = freeBoardDTO.parseThis2FreeBoard(user, team);
		FreeBoard saveFreeBoard = freeBoardOneInsertService.insertFreeBoard(freeBoard);
		var result = new CustomEntityModel<>(saveFreeBoard, this, Long.toString(saveFreeBoard.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{seq}")
	@Memo("자유 게시판을 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid FreeBoardDTO freeBoardDTO,
		@Current_User Users user, Errors error) throws NotFoundException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		FreeBoard updateFreeBoard = freeBoardDTO.parseThis2FreeBoard(user, null);
		updateFreeBoard = freeBoardOneUpdateService.updateFreeBoard(seq, updateFreeBoard);
		var result = new CustomEntityModel<>(updateFreeBoard, this, Long.toString(updateFreeBoard.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{seq}")
	@Memo("자유 게시판을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		FreeBoard deleteFreeBoard = freeBoardOneDeleteService.deleteFreeBoard(seq);
		var result = new CustomEntityModel<>(deleteFreeBoard, this, Long.toString(deleteFreeBoard.getSeq()), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

	@Memo("자유게시판 파일 업로드")
	@PostMapping("/{seq}/fileUpload/{type}")
	public ResponseEntity<?> fileUpload(@RequestParam MultipartFile[] files, @PathVariable long seq, @PathVariable FileType type,
		@Current_User Users user) throws NotFoundException, FileUploadException {
		for (MultipartFile f : files) {
			if (!CustomFileUpload.fileUploadFilter(f.getOriginalFilename(), type))
				throw new FileUploadException("코드 형식의 파일은 등록할 수 없습니다.");
		}
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		freeBoardOneInsertService.fileUpload(files, seq, type);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자유게시판 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user)
		throws NotFoundException {
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		freeBoardOneDeleteService.fileDelete(fileName, seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("자유게시판 파일 다운로드")
	@GetMapping("/{seq}/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long seq, @PathVariable String fileName,
		HttpServletRequest request) throws CanNotPerformException {
		Resource resource = freeBoardReadService.fileDownload(seq, fileName);
		if (resource == null) {
			throw new CanNotPerformException("죄송합니다. 잠시 에러가 발생했습니다. 잠시후에 다시 시도해주세요.");
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
