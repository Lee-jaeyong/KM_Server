package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface TeamAPI extends JpaRepository<Team, Long> {
	@Memo("자신이 팀장이고 아직 만료되지 않은 팀 정보 모두 가져오기")
	List<Team> findByTeamLeader_IdAndEndDateGreaterThanEqualAndFlag(String id,Date date, BooleanState state);

	@Memo("자신이 등록한 팀인지 확인")
	Team findByNameAndTeamLeader_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndFlag(String name, String id, Date start,
		Date end, BooleanState state);

	Team findByCodeAndTeamLeader_Id(long seq, String id);

	Team findByCodeAndTeamLeader_Id(String code, String id);

	long countByCodeAndTeamLeader_Id(String code, String id);

	boolean existsByCodeAndTeamLeader_Id(String code, String id);

	boolean existsByCode(String code);

	@EntityGraph(attributePaths = { "teamLeader", "joinPerson" }, type = EntityGraphType.FETCH)
	Team findByCode(String code);

	@Query("SELECT t FROM Team t WHERE t.code = :code")
	Team getTeamObject(@Param("code") String code);
}
