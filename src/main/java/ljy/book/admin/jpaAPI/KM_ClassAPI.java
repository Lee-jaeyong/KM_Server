package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.ClassType;

public interface KM_ClassAPI extends CommonRepository<KM_class, Long> {
	Page<KM_class> findByName(String name, Pageable pageable);

	Optional<KM_class> findBykmUser_Id(String id);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p " + " SET p.name = :name," + "p.startDate = :startDate," + "p.endDate = :endDate,"
			+ "p.content = :content," + "p.type = :type" + ",p.replyPermit_state = :replyPermit_state,"
			+ "p.selectMenu = :selectMenu," + "p.use_state = :use_state" + " WHERE p.seq = :idx")
	void updateKm_class(@Param("name") String name, @Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("content") String content, @Param("type") ClassType type,
			@Param("replyPermit_state") BooleanState replyPermit_state, @Param("selectMenu") String selectMenu,
			@Param("use_state") BooleanState use_state, @Param("idx") long idx);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = null WHERE p.seq = :idx")
	void deletePlannerDocFile(@Param("idx") long idx);

}
