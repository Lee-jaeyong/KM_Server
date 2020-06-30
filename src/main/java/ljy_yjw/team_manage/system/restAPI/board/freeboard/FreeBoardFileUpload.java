package ljy_yjw.team_manage.system.restAPI.board.freeboard;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.FileUploadException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.FreeBoardController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.freeBoard.FreeBoardAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.freeBoard.FreeBoardOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.freeBoard.FreeBoardOneInsertService;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;

@FreeBoardController
public class FreeBoardFileUpload {

	@Autowired
	FreeBoardReadService freeBoardReadService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	FreeBoardAuthService freeBoardAuthService;

	@Autowired
	FreeBoardOneInsertService freeBoardOneInsertService;

	@Autowired
	FreeBoardOneDeleteService freeBoardOneDeleteService;

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
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("자유게시판 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user)
		throws NotFoundException, IOException {
		freeBoardAuthService.boardAuthCheck(seq, user.getId());
		freeBoardOneDeleteService.fileDelete(fileName, seq);
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("자유게시판 파일 다운로드")
	@GetMapping("/{seq}/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long seq, @PathVariable String fileName,
		HttpServletRequest request, @Current_User Users user) throws CanNotPerformException, NotFoundException {
		Resource resource = freeBoardReadService.fileDownload(seq, fileName);
		if (resource == null) {
			throw new CanNotPerformException("죄송합니다. 잠시 에러가 발생했습니다. 잠시후에 다시 시도해주세요.");
		}
		return returnResponseEntity(resource, request);
	}

	@Memo("자유게시판 알집 다운로드")
	@GetMapping("/{seq}/downloadFile/all")
	public ResponseEntity<Resource> downloadFileAll(@PathVariable long seq, @Current_User Users user, HttpServletRequest request)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		FreeBoard freeboard = freeBoardReadService.getFreeBoard(seq);
		teamAuthService.checkTeamAuth(user, freeboard.getTeam().getCode());
		freeboard.getFileList().forEach(c -> {
			try {
				c.getImgByte(freeBoardReadService);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Resource resource = freeBoardReadService.zipFileDownload(freeboard.getFileList(), freeboard);
		return returnResponseEntity(resource, request);
	}

	private ResponseEntity<Resource> returnResponseEntity(Resource resource, HttpServletRequest request) {
		String contentType = "";
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

	private EntityModel<?> getEntityModel(long seq) {
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return result;
	}
}
