package ljy_yjw.team_manage.system.service.auth.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.NoticeAPI;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;

@Service
public class NoticeAuthService {

	@Autowired
	NoticeAPI noticeAPI;

	public Notice getNoticeObject(long seq) throws NotFoundException {
		Notice notice = noticeAPI.findBySeq(seq);
		if (notice == null) {
			throw new NotFoundException("해당 번호의 공지사항이 존재하지 않습니다.");
		}
		return notice;
	}

	public void checkNoticeByUser(long seq, String id) throws NotFoundException {
		if (!noticeAPI.existsBySeqAndUser_Id(seq, id)) {
			throw new NotFoundException("해당하는 번호의 공지사항이 존재하지 않거나 혹은 권한이 존재하지 않습니다.");
		}
	}
}
