package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;

import ljy_yjw.team_manage.system.domain.entity.FreeBoard;

public interface FreeBoardDAO {
	void update(FreeBoard freeboard);

	void delete(long seq);

	void fileDelete(HashMap<String, Object> map);
}
