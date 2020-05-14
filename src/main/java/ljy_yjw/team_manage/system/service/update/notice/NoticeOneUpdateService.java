package ljy_yjw.team_manage.system.service.update.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.NoticeDAO;
import ljy_yjw.team_manage.system.domain.entity.Notice;

@Service
public class NoticeOneUpdateService {

	@Autowired
	NoticeDAO noticeDAO;

	public Notice updateNotice(long seq, Notice updateNotice) {
		updateNotice.setSeq(seq);
		noticeDAO.update(updateNotice);
		return updateNotice;
	}

}
