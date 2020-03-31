package ljy.book.admin.jpaAPI;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.enums.BooleanState;

public interface KM_ClassAPI extends CommonRepository<KM_class, Long> {

	@Memo("교수가 수업 리스트를 가져오는 메소드")
	Page<KM_class> findByKmUser_Id(String id, Pageable pageable);

	@Memo("학생이 수업 리스트를 가져오는 메소드")
	Page<KM_class> findByKmSignUpClassForStu_SignUpStateAndKmUser_Id(BooleanState booleanState, String id, Pageable pageable);

	KM_class findBySeqAndKmUser_Id(long idx, String id);

	KM_class findByClassCode(String code);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);

	@Transactional
	@Modifying
	@Query("UPDATE KM_class p SET p.plannerDocName = null WHERE p.seq = :idx")
	void deletePlannerDocFile(@Param("idx") long idx);

}
