package ljy_yjw.team_manage.system.dbConn.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.Users;

public interface UsersAPI extends JpaRepository<Users, Long> {
	Optional<Users> findById(String id);
}
