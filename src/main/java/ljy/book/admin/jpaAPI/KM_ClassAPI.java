package ljy.book.admin.jpaAPI;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_class;

public interface KM_ClassAPI extends CommonRepository<KM_class, Long> {
	Page<KM_class> findByKmUser_Id(String id, Pageable pageable);

	KM_class findBySeqAndKmUser_Id(long idx, String id);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = null WHERE p.seq = :idx")
	void deletePlannerDocFile(@Param("idx") long idx);

}
