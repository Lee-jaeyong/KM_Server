package ljy_yjw.team_manage.system.service.update.freeBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.FreeBoardDAO;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;

@Service
public class FreeBoardOneUpdateService {

	@Autowired
	FreeBoardDAO freeBoardDAO;

	public FreeBoard updateFreeBoard(long seq, FreeBoard freeBoard) {
		freeBoard.setSeq(seq);
		freeBoardDAO.update(freeBoard);
		return freeBoard;
	}
}
