package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.enums.BooleanState;

public interface PlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	PlanByUser findBySeqAndUser_Id(long seq, String id);

	Page<PlanByUser> findByStateAndTeam_Code(BooleanState booleanState, String code, Pageable pageable);
}
