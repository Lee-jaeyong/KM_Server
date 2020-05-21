package ljy_yjw.team_manage.system.service.delete.freeBoard;

import java.io.IOException;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.FreeBoardDAO;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class FreeBoardOneDeleteService {

	@Autowired
	FreeBoardDAO freeBoardDAO;

	@Autowired
	CommonFileUpload commonFileUpload;

	public FreeBoard deleteFreeBoard(long seq) {
		freeBoardDAO.delete(seq);
		return FreeBoard.builder().seq(seq).build();
	}

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) throws IOException {
		if (commonFileUpload.deleteFile(seq, "freeBoard", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			freeBoardDAO.fileDelete(map);
		}
		return true;
	}
}
