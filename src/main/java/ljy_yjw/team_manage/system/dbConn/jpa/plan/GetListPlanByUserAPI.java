package ljy_yjw.team_manage.system.dbConn.jpa.plan;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface GetListPlanByUserAPI extends JpaRepository<PlanByUser, Long> {

	@Memo("날짜 입력 X 타이틀 X")
	@EntityGraph(value = "getTodoList", type = EntityGraphType.FETCH)
	List<PlanByUser> findByTeam_CodeAndUser_IdAndStateAndTagContainingOrderBySeqDesc(String code, String id, BooleanState state,
		String tag, Pageable pageable);

	@Memo("날짜 X 타이틀 O")
	@EntityGraph(value = "getTodoList", type = EntityGraphType.FETCH)
	List<PlanByUser> findByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingOrderBySeqDesc(String code,
		String id, BooleanState state, String tag, String title, Pageable pageable);

	@Memo("날짜 O 타이틀 X")
	@EntityGraph(value = "getTodoList", type = EntityGraphType.FETCH)
	List<PlanByUser> findByTeam_CodeAndUser_IdAndStateAndTagContainingAndStartGreaterThanEqualAndEndLessThanEqualOrderBySeqDesc(
		String code, String id, BooleanState state, String tag, Date start, Date end, Pageable pageable);

	@Memo("날짜 O 타이틀 O")
	@EntityGraph(value = "getTodoList", type = EntityGraphType.FETCH)
	List<PlanByUser> findByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingAndStartGreaterThanEqualAndEndLessThanEqualOrderBySeqDesc(
		String code, String id, BooleanState state, String tag, String title, Date start, Date end, Pageable pageable);

	@Memo("날짜 X 타이틀 X")
	long countByTeam_CodeAndUser_IdAndStateAndTagContaining(String code, String id, BooleanState state, String tag);

	@Memo("날짜 X 타이틀 O")
	long countByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContaining(String code, String id, BooleanState state,
		String tag, String title);

	@Memo("날짜 O 타이틀 X")
	long countByTeam_CodeAndUser_IdAndStateAndTagContainingAndStartGreaterThanEqualAndEndLessThanEqual(String code, String id,
		BooleanState state, String tag, Date start, Date end);

	@Memo("날짜 O 타이틀 O")
	long countByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingAndStartGreaterThanEqualAndEndLessThanEqual(
		String code, String id, BooleanState state, String tag, String title, Date start, Date end);
}
