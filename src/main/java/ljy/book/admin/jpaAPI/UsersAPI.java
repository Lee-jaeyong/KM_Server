package ljy.book.admin.jpaAPI;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.Users;

public interface UsersAPI extends JpaRepository<Users, Long> {
	Optional<Users> findById(String id);
}
