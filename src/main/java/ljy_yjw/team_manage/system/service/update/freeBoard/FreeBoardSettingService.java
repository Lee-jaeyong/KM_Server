package ljy_yjw.team_manage.system.service.update.freeBoard;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.BoardFileAndImg;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;

@Service
public class FreeBoardSettingService {

	@Autowired
	FreeBoardReadService freeBoardReadService;

	@Memo("자유 게시판의 이미지 파일을 모두 바이트 형태로 변환")
	public void settingImg(FreeBoard freeBoard) throws IOException {
		List<BoardFileAndImg> list = freeBoard.getFileList();
		for (BoardFileAndImg c : list) {
			c.getImgByte(freeBoardReadService);
		}
	}
}
