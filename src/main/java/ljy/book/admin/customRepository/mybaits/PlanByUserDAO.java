package ljy.book.admin.customRepository.mybaits;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanByUserDAO {
	void delete(long seq);
}
