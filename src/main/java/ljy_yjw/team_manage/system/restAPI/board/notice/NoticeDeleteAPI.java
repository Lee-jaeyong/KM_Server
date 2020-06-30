package ljy_yjw.team_manage.system.restAPI.board.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.restAPI.NoticeController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.notice.NoticeAuthService;
import ljy_yjw.team_manage.system.service.delete.notice.NoticeOneDeleteService;
import lombok.var;

@NoticeController
public class NoticeDeleteAPI {

	@Autowired
	NoticeAuthService noticeAuthService;

	@Autowired
	NoticeOneDeleteService noticeOneDeleteService;

	@DeleteMapping("/{seq}")
	@Memo("팀장이 팀의 공지사항을 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		noticeAuthService.checkNoticeByUser(seq, user.getId());
		Notice deleteNotice = noticeOneDeleteService.deleteNotice(seq);
		var result = new CustomEntityModel<>(deleteNotice, this, Long.toString(deleteNotice.getSeq()), Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

}
