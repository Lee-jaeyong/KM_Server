package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

@Mapper
public interface PlanByUserDAO {
	void delete(long seq);

	void update(PlanByUser planByUser);

	List<HashMap<String, Object>> chartDataByPlan(String code);

	List<HashMap<String, Object>> chartDataByTodo(String code);

	List<HashMap<String, Object>> chartDataBySuccessTodo(String code);
}
