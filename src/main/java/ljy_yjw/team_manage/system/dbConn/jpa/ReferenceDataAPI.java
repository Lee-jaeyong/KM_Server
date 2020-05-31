package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface ReferenceDataAPI extends JpaRepository<ReferenceData, Long> {
	@EntityGraph(attributePaths = { "user", "fileList" }, type = EntityGraphType.FETCH)
	ReferenceData findBySeqAndState(long seq, BooleanState state);

	@EntityGraph(attributePaths = { "user", "fileList" }, type = EntityGraphType.FETCH)
	List<ReferenceData> findByTeam_CodeAndStateOrderBySeqDesc(String code, BooleanState state, Pageable pageable);

	long countByTeam_CodeAndState(String code, BooleanState state);
}
