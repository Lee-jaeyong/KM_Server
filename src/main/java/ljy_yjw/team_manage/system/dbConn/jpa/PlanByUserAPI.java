package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface PlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	@EntityGraph(attributePaths = "user", type = EntityGraphType.FETCH)
	PlanByUser findBySeq(long seq);

	@EntityGraph(attributePaths = "user", type = EntityGraphType.FETCH)
	List<PlanByUser> findByStateAndTeam_Code(BooleanState booleanState, String code, Pageable pageable);

	long countByStateAndTeam_Code(BooleanState booleanState, String code);

	@EntityGraph(attributePaths = {"user","todoList"}, type = EntityGraphType.FETCH)
	List<PlanByUser> findByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, Date start, Pageable pageable);

	@EntityGraph(attributePaths = "user", type = EntityGraphType.FETCH)
	List<PlanByUser> findByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState booleanState, String id, String code,
		Date start, Pageable pageable);

	@Memo("지난 TodoList 가져오기")
	List<PlanByUser> findByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, String date,
		Pageable pageable);

	@Memo("TodoList 가져오기")
	@EntityGraph(attributePaths = "user", type = EntityGraphType.FETCH)
	List<PlanByUser> findByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState booleanState, String code,
		Date date1, Date date2, Pageable pageable);

	long countByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, Date start);

	long countByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState booleanState, String id, String code, Date start);

	@Memo("TodoList 모든 개수 가져오기")
	long countByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState booleanState, String code, Date date1,
		Date date2);

	@Memo("끝난 TodoList 개수 가져오기")
	long countByStateAndTeam_CodeAndEndLessThan(BooleanState booleanState, String code, String date1);

	PlanByUser findBySeqAndState(long seq, BooleanState state);

	List<PlanByUser> findByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrderBySeq(BooleanState state, String id,
		String code, String searchTag, Pageable pageable);

	long countByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrderBySeq(BooleanState state, String id, String code,
		String searchTag);

	List<PlanByUser> findByStateAndUser_IdAndTagContainsIgnoreCaseOrderBySeq(BooleanState state, String id, String searchTag,
		Pageable pageable);

	long countByStateAndUser_IdAndTagContainsIgnoreCaseOrderBySeq(BooleanState state, String id, String searchTag);
}
