package ljy_yjw.team_manage.system.restAPI.board.notice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.notice.NoticeAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.notice.NoticeOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.notice.NoticeOneInsertService;
import ljy_yjw.team_manage.system.service.read.notice.NoticeReadService;

@NoticeController
public class NoticeFileUpload {

	@Autowired
	NoticeAuthService noticeAuthService;

	@Autowired
	NoticeOneInsertService noticeOneInsertService;

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	NoticeOneDeleteService noticeOneDeleteService;

	@Autowired
	NoticeReadService noticeReadService;

	@Memo("공지사항 파일 업로드")
	@PostMapping("/{seq}/fileUpload/{type}")
	public ResponseEntity<?> fileUpload(@RequestParam MultipartFile[] files, @PathVariable long seq, @PathVariable FileType type,
		@Current_User Users user) throws NotFoundException {
		noticeAuthService.checkNoticeByUser(seq, user.getId());
		for (MultipartFile f : files) {
			if (!CustomFileUpload.fileUploadFilter(f.getOriginalFilename(), type))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		noticeOneInsertService.fileUpload(files, seq, type);
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("공지사항 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user)
		throws IOException {
		noticeOneDeleteService.fileDelete(fileName, seq);
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("공지사항 파일 다운로드")
	@GetMapping("/{seq}/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long seq, @PathVariable String fileName,
		HttpServletRequest request, @Current_User Users user) throws TeamCodeNotFountException, NotFoundException {
		Notice notice = noticeAuthService.getNoticeObject(seq);
		teamAuthService.checkTeamAuth(user, notice.getTeam().getCode());
		Resource resource = noticeReadService.fileDownload(seq, fileName);
		if (resource == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return returnResponseEntity(resource, request);
	}

	@Memo("공지사항 알집 다운로드")
	@GetMapping("/{seq}/downloadFile/all")
	public ResponseEntity<Resource> downloadFileAll(@PathVariable long seq, @Current_User Users user, HttpServletRequest request)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		Notice notice = noticeAuthService.getNoticeObject(seq);
		teamAuthService.checkTeamAuth(user, notice.getTeam().getCode());
		notice.getFileList().forEach(c -> {
			try {
				c.getImgByte(noticeReadService);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Resource resource = noticeReadService.zipFileDownload(notice.getFileList(), notice);
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
