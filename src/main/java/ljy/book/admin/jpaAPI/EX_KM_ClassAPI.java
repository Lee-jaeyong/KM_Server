package ljy.book.admin.jpaAPI;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.PlanFileAndImg;

public interface EX_KM_ClassAPI extends CommonRepository<PlanFileAndImg, Long> {

//	@Memo("교수가 수업 리스트를 가져오는 메소드")
//	Page<PlanFileAndImg> findByKmUser_Id(String id, Pageable pageable);
//
//	@Memo("학생이 수업 리스트를 가져오는 메소드")
//	Page<PlanFileAndImg> findByKmSignUpClassForStu_SignUpStateAndKmUser_Id(BooleanState booleanState, String id, Pageable pageable);
//
//	@Memo("학생이 자신이 수강하는 수업이 맞는지의 여부를 판단하는 메소드")
//	PlanFileAndImg findBySeqAndKmSignUpClassForStu_SignUpStateAndKmUser_Id(long idx, BooleanState booleanState, String id);
//
//	PlanFileAndImg findBySeqAndKmUser_Id(long idx, String id);
//
//	PlanFileAndImg findByClassCode(String code);
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE KM_class p SET p.plannerDocName = :docName WHERE p.seq = :idx")
//	void plannerDocFileUpload(@Param("docName") String docName, @Param("idx") long idx);
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE KM_class p SET p.plannerDocName = null WHERE p.seq = :idx")
//	void deletePlannerDocFile(@Param("idx") long idx);

}
