package ljy.book.admin.jpaAPI;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_class;

public interface KM_ClassAPI extends CommonRepository<KM_class, Long> {
	Optional<KM_class> findBykmUser_Id(String id);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);

}
