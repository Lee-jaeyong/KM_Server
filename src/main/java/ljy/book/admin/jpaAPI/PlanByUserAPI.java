package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.professor.requestDTO.DateRequestDTO;

public interface PlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	PlanByUser findBySeqAndUser_Id(long seq, String id);

	Page<PlanByUser> findByStateAndTeam_CodeAndStartGreaterThanEqualAndEndLessThanEqual(BooleanState booleanState, String code,
		String start, String end, Pageable pageable);
}
