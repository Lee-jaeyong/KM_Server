package ljy_yjw.team_manage.system.service.delete.notice;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.NoticeDAO;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class NoticeOneDeleteService {

	@Autowired
	NoticeDAO noticeDAO;

	@Autowired
	CommonFileUpload commonFileUpload;

	public Notice deleteNotice(long seq) {
		noticeDAO.delete(seq);
		return Notice.builder().seq(seq).build();
	}

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) {
		if (commonFileUpload.deleteFile(seq, "notice", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			noticeDAO.fileDelete(map);
		}
		return true;
	}
}
