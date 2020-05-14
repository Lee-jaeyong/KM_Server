package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.query.Param;

import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface TeamJoinRequestAPI extends JpaRepository<JoinTeam, Long> {
	boolean existsByTeam_CodeAndUser_Id(String code, String id);

	@EntityGraph(attributePaths = "team", type = EntityGraphType.FETCH)
	JoinTeam findBySeq(long seq);

	JoinTeam findByTeam_CodeAndUser_Id(String code, String id);

	JoinTeam findBySeqAndTeam_TeamLeader_Id(long seq, String id);

	@Query("SELECT j FROM JoinTeam j JOIN FETCH j.team t JOIN FETCH j.user u WHERE t.code = :code AND j.state = :state")
	List<JoinTeam> findByStateAndTeam_CodeAndResonIsNull(@Param(value = "state") BooleanState state, @Param("code") String code);
}
