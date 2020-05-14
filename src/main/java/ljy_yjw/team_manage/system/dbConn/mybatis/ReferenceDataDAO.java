package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;

import ljy_yjw.team_manage.system.domain.entity.ReferenceData;

public interface ReferenceDataDAO {
	void update(ReferenceData refereneceData);

	void delete(long seq);

	void fileDelete(HashMap<String, Object> map);
}
