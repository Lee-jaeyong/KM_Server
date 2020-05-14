package ljy_yjw.team_manage.system.dbConn.jpa.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

public interface AuthPlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	boolean existsBySeqAndUser_Id(long seq, String id);
}
