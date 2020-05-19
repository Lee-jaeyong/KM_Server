package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface NoticeAPI extends JpaRepository<Notice, Long> {
	boolean existsBySeqAndUser_Id(long seq, String id);

	@EntityGraph(attributePaths = { "team", "user" }, type = EntityGraphType.FETCH)
	Notice findBySeq(long seq);

	Notice findBySeqAndUser_Id(long seq, String id);

	@EntityGraph(attributePaths = { "user" }, type = EntityGraphType.FETCH)
	List<Notice> findByTeam_CodeAndState(String code, BooleanState booleanState, Pageable pageable);

	long countByTeam_CodeAndState(String code, BooleanState booleanState);

	Notice findBySeqAndState(long seq, BooleanState booleanState);
}
