package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.enums.BooleanState;

public interface PlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	PlanByUser findBySeqAndUser_Id(long seq, String id);

	Page<PlanByUser> findByStateAndTeam_Code(BooleanState booleanState, String code, Pageable pageable);

	Page<PlanByUser> findByStateAndTeam_CodeAndStartGreaterThanEqual(BooleanState booleanState, String code, String start,
		Pageable pageable);

	@Memo("지난 TodoList 가져오기")
	Page<PlanByUser> findByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, String date,
		Pageable pageable);

	@Memo("TodoList 가져오기")
	Page<PlanByUser> findByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState booleanState, String code,
		String date1, String date2, Pageable pageable);

	@Memo("TodoList 모든 개수 가져오기")
	long countByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState booleanState, String code, String date1,
		String date2);

	@Memo("끝난 TodoList 개수 가져오기")
	long countByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, String date1);
	
	PlanByUser findBySeqAndState(long seq, BooleanState state);

	Page<PlanByUser> findByStateAndUser_IdAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(BooleanState state,
		String id, String searchTag, String searchContent, Pageable pageable);
}
