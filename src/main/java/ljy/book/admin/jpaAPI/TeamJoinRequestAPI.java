package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.JoinTeam;

public interface TeamJoinRequestAPI extends JpaRepository<JoinTeam, Long> {
	JoinTeam findByTeam_CodeAndUser_Id(String code,String id);
}
