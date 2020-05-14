package ljy_yjw.team_manage.system.dbConn.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ljy_yjw.team_manage.system.domain.entity.Team;

public interface TeamAPI extends JpaRepository<Team, Long> {
	Team findByCodeAndTeamLeader_Id(long seq, String id);

	Team findByCodeAndTeamLeader_Id(String code, String id);

	long countByCodeAndTeamLeader_Id(String code, String id);

	boolean existsByCodeAndTeamLeader_Id(String code, String id);

	boolean existsByCode(String code);

	@EntityGraph(attributePaths = { "teamLeader","joinPerson" }, type = EntityGraphType.FETCH)
	Team findByCode(String code);

	@Query("SELECT t FROM Team t WHERE t.code = :code")
	Team getTeamObject(@Param("code") String code);
}
