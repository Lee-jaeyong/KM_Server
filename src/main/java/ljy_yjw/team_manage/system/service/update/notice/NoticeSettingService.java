package ljy_yjw.team_manage.system.service.update.notice;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.NoticeFileAndImg;
import ljy_yjw.team_manage.system.service.read.notice.NoticeReadService;

@Service
public class NoticeSettingService {

	@Autowired
	NoticeReadService noticeReadService;

	@Memo("자유 게시판의 이미지 파일을 모두 바이트 형태로 변환")
	public void settingImg(Notice notice) throws IOException {
		List<NoticeFileAndImg> list = notice.getFileList();
		for (NoticeFileAndImg c : list) {
			c.getImgByte(noticeReadService);
		}
	}
}
