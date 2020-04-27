package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.professor.requestDTO.PlanByUserDTO;

@Mapper
public interface PlanByUserDAO {
	void delete(long seq);

	void update(PlanByUserDTO planByUser);

	void updateProgress(HashMap<String, Object> map);

	List<HashMap<String, Object>> chartDataByPlan(String code);
}
