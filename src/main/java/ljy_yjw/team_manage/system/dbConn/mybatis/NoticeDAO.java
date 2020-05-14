package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;

import ljy_yjw.team_manage.system.domain.entity.Notice;

public interface NoticeDAO {
	void update(Notice notice);

	void delete(long seq);

	void fileDelete(HashMap<String, Object> map);
}
