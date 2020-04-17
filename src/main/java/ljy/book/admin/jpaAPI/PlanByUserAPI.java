package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.PlanByUser;

public interface PlanByUserAPI extends JpaRepository<PlanByUser, Long> {
	PlanByUser findBySeqAndUser_Id(long seq, String id);
}
