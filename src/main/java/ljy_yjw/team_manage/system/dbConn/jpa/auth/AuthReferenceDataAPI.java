package ljy_yjw.team_manage.system.dbConn.jpa.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.ReferenceData;

public interface AuthReferenceDataAPI extends JpaRepository<ReferenceData, Long> {
	boolean existsBySeqAndUser_Id(long seq, String id);
}
