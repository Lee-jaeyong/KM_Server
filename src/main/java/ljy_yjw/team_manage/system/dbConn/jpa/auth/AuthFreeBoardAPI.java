package ljy_yjw.team_manage.system.dbConn.jpa.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.FreeBoard;

public interface AuthFreeBoardAPI extends JpaRepository<FreeBoard, Long> {
	boolean existsBySeqAndUser_Id(long seq, String id);
}
