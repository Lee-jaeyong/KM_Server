package ljy_yjw.team_manage.system.restAPI.board.referenceData;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.FileUploadException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.referenceData.ReferenceDataAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.referenceData.ReferenceDataOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.referenceData.ReferenceDataOneInsertService;
import ljy_yjw.team_manage.system.service.read.referenceData.ReferenceDataReadService;

@ReferenceDataController
public class ReferenceDataFileUpload {

	@Autowired
	ReferenceDataAuthService referenceDataAuthService;

	@Autowired
	ReferenceDataOneInsertService referenceDataOneInsertService;

	@Autowired
	ReferenceDataOneDeleteService referenceDataOneDeleteService;

	@Autowired
	ReferenceDataReadService referenceDataReadService;

	@Autowired
	TeamAuthService teamAuthService;

	@Memo("참고자료 파일 업로드")
	@PostMapping("/{seq}/fileUpload/{type}")
	public ResponseEntity<?> fileUpload(@RequestParam MultipartFile[] files, @PathVariable long seq, @PathVariable FileType type,
		@Current_User Users user) throws FileUploadException, NotFoundException {
		for (MultipartFile f : files) {
			if (!CustomFileUpload.fileUploadFilter(f.getOriginalFilename(), type))
				throw new FileUploadException("코드 형식의 파일은 등록할 수 없습니다.");
		}
		referenceDataAuthService.boardAuthCheck(seq, user.getId());
		referenceDataOneInsertService.fileUpload(files, seq, type);
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("참고자료 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user)
		throws NotFoundException, IOException {
		referenceDataAuthService.boardAuthCheck(seq, user.getId());
		referenceDataOneDeleteService.fileDelete(fileName, seq);
		return ResponseEntity.ok(getEntityModel(seq));
	}

	@Memo("참고자료 파일 다운로드")
	@GetMapping("/{seq}/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long seq, @PathVariable String fileName,
		HttpServletRequest request, @Current_User Users user) throws CanNotPerformException, NotFoundException {
		Resource resource = referenceDataReadService.fileDownload(seq, fileName);
		if (resource == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return returnResponseEntity(resource, request);
	}

	@Memo("자유게시판 알집 다운로드")
	@GetMapping("/{seq}/downloadFile/all")
	public ResponseEntity<Resource> downloadFileAll(@PathVariable long seq, @Current_User Users user, HttpServletRequest request)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		ReferenceData referenceData = referenceDataReadService.getReferenceData(seq);
		teamAuthService.checkTeamAuth(user, referenceData.getTeam().getCode());
		referenceData.getFileList().forEach(c -> {
			try {
				c.getImgByte(referenceDataReadService);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Resource resource = referenceDataReadService.zipFileDownload(referenceData.getFileList(), referenceData);
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
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return result;
	}
}
