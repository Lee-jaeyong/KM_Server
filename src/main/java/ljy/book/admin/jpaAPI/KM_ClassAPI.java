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
	Page<KM_class> findByKmUser_Id(String id, Pageable pageable);

	KM_class findBySeqAndKmUser_Id(long idx, String id);

	Optional<KM_class> findBykmUser_Id(String id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value = 
		"UPDATE KM_class p " + 
		"SET p.name = :name, " + 
		"p.start_Date = :startDate, " + 
		"p.end_Date = :endDate, " + 
		"p.content = :content, " + 
		"p.type = :type, " + 
		"p.reply_Permit_state = :replyPermit_state, " + 
		"p.select_Menu = :selectMenu, " + 
		"p.use_state = :use_state WHERE p.seq = :idx AND p.km_user_seq = (SELECT seq FROM km_user WHERE id = :userId)")
	void updateKm_class(@Param("name") String name, @Param("startDate") String startDate, @Param("endDate") String endDate,
		@Param("content") String content, @Param("type") String type,
		@Param("replyPermit_state") String replyPermit_state, @Param("selectMenu") String selectMenu,
		@Param("use_state") String use_state, @Param("idx") long idx, @Param("userId") String id);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = null WHERE p.seq = :idx")
	void deletePlannerDocFile(@Param("idx") long idx);

}
