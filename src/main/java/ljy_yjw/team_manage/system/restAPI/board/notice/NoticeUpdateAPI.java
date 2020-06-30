package ljy_yjw.team_manage.system.restAPI.board.notice;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.NoticeDTO;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.notice.NoticeAuthService;
import ljy_yjw.team_manage.system.service.update.notice.NoticeOneUpdateService;
import lombok.var;

@NoticeController
public class NoticeUpdateAPI {

	@Autowired
	NoticeAuthService noticeAuthService;

	@Autowired
	NoticeOneUpdateService noticeOneUpdateService;

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

}
