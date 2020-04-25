package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.Team;

public interface TeamAPI extends JpaRepository<Team, Long> {
	Team findByCodeAndTeamLeader_Id(long seq, String id);

	Team findByCodeAndTeamLeader_Id(String code, String id);

	Team findByCode(String code);
}
