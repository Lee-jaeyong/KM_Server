package ljy_yjw.team_manage.system.service.read.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.NoticeAPI;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class NoticeReadService {

	@Autowired
	NoticeAPI noticeAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	public List<Notice> getNoticeList(String code, Pageable pageable) {
		return noticeAPI.findByTeam_CodeAndState(code, BooleanState.YES, pageable);
	}

	public long getNoticeCount(String code) {
		return noticeAPI.countByTeam_CodeAndState(code, BooleanState.YES);
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "notice", fileName);
	}
}
