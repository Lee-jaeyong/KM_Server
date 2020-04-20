package ljy.book.admin.customRepository.mybaits;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.professor.requestDTO.PlanByUserDTO;

@Mapper
public interface PlanByUserDAO {
	void delete(long seq);

	void update(PlanByUserDTO planByUser);
}
