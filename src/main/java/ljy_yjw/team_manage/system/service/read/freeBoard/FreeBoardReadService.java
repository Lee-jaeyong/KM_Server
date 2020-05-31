package ljy_yjw.team_manage.system.service.read.freeBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.FreeBoardAPI;
import ljy_yjw.team_manage.system.domain.entity.BoardFileAndImg;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class FreeBoardReadService {

	@Autowired
	FreeBoardAPI freeBoardAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	public FreeBoard getFreeBoard(long seq) throws NotFoundException {
		FreeBoard freeBoard = freeBoardAPI.findBySeqAndState(seq, BooleanState.YES);
		if (freeBoard == null)
			throw new NotFoundException("해당 번호의 자유게시판이 존재하지 않습니다.");
		return freeBoard;
	}

	public List<FreeBoard> getFreeBoardList(String code, Pageable pageable) {
		return freeBoardAPI.findByTeam_CodeAndStateOrderBySeqDesc(code, BooleanState.YES, pageable);
	}

	public long countFreeBoard(String code) {
		return freeBoardAPI.countByTeam_CodeAndState(code, BooleanState.YES);
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "freeBoard", fileName);
	}

	@Memo("파일 알집 다운로드")
	public Resource zipFileDownload(List<BoardFileAndImg> fileList, FreeBoard freeboard) throws IOException {
		List<HashMap<String, Object>> list = new ArrayList<>();
		fileList.forEach(c -> {
			HashMap<String, Object> map = new HashMap<>();
			map.put("fileName", c.getName());
			map.put("fileByte", c.getImgByte());
			list.add(map);
		});
		return commonFileUpload.zipFileDownload(list, freeboard.getTitle(), "freeBoard", freeboard.getSeq());
	}
}
