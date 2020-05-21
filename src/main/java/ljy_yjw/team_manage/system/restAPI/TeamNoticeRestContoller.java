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

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.domain.dto.NoticeDTO;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.team.NotTeamLeaderException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.notice.NoticeAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.delete.notice.NoticeOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.notice.NoticeOneInsertService;
import ljy_yjw.team_manage.system.service.read.notice.NoticeReadService;
import ljy_yjw.team_manage.system.service.update.notice.NoticeOneUpdateService;
import lombok.var;

@RestController
@RequestMapping("/api/teamManage/notice")
public class TeamNoticeRestContoller {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	NoticeAuthService noticeAuthService;

	@Autowired
	NoticeOneInsertService noticeOneInsertService;

	@Autowired
	NoticeOneUpdateService noticeOneUpdateService;

	@Autowired
	NoticeOneDeleteService noticeOneDeleteService;

	@Autowired
	NoticeReadService noticeReadService;

	@GetMapping("/{seq}")
	@Memo("해당 공지사항을 가져오는 메소드")
	public ResponseEntity<?> get(@PathVariable long seq, @Current_User Users user)
		throws NotFoundException, TeamCodeNotFountException {
		Notice notice = noticeAuthService.getNoticeObject(seq);
		teamAuthService.checkTeamAuth(user, notice.getTeam().getCode());
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		CustomEntityModel<Notice> result = null;
		if (notice.getUser().getId().equals(user.getId()))
			result = new CustomEntityModel<Notice>(notice, this, Long.toString(notice.getSeq()), Link.ALL);
		else
			result = new CustomEntityModel<Notice>(notice, this, Long.toString(notice.getSeq()), Link.NOT_INCLUDE);
		ArrayList<byte[]> imgByte = new ArrayList<byte[]>();
		notice.getNoticeFileAndImg().forEach(c -> {
			if (c.getType() == FileType.IMG) {
				try {
					InputStream in = noticeReadService.fileDownload(seq, c.getName()).getInputStream();
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
	@Memo("팀의 모든 공지사항을 가져오는 메소드")
	public ResponseEntity<?> getAll(@PathVariable String code, @Current_User Users user, Pageable pageable,
		PagedResourcesAssembler<Notice> assembler) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
		List<Notice> noticeList = noticeReadService.getNoticeList(code, pageable);
		long totalCount = noticeReadService.getNoticeCount(code);
		var result = assembler.toModel(new PageImpl<Notice>(noticeList, pageable, totalCount));
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/{code}")
	@Memo("팀장이 팀의 공지사항을 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid NoticeDTO noticeDTO, @Current_User Users user,
		Errors error) throws InputValidException, TeamCodeNotFountException, NotTeamLeaderException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		teamAuthService.checkTeamLeader(user, code);
		Team team = teamAuthService.getTeamObject(code);
		Notice saveNotice = noticeDTO.parseThis2Notice(team, user);
		saveNotice = noticeOneInsertService.insertNotice(saveNotice);
		var result = new CustomEntityModel<>(saveNotice, this, Long.toString(saveNotice.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{seq}")
	@Memo("팀장이 팀의 공지사항을 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid NoticeDTO noticeDTO, @Current_User Users user,
		Errors error) throws InputValidException, NotFoundException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		noticeAuthService.checkNoticeByUser(seq, user.getId());
		Notice updateNotice = noticeOneUpdateService.updateNotice(seq, noticeDTO.parseThis2Notice(null, null));
		var result = new CustomEntityModel<>(updateNotice, this, Long.toString(updateNotice.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{seq}")
	@Memo("팀장이 팀의 공지사항을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		noticeAuthService.checkNoticeByUser(seq, user.getId());
		Notice deleteNotice = noticeOneDeleteService.deleteNotice(seq);
		var result = new CustomEntityModel<>(deleteNotice, this, Long.toString(deleteNotice.getSeq()), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

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
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("공지사항 파일 삭제")
	@PostMapping("/{seq}/fileUpload/{fileName:.+}/delete")
	public ResponseEntity<?> fileDelete(@PathVariable long seq, @PathVariable String fileName, @Current_User Users user)
		throws IOException {
		noticeOneDeleteService.fileDelete(fileName, seq);
		EntityModel<Long> result = new EntityModel<Long>(seq);
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		result.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
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
