package ljy.book.admin.jpaAPI;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.customRepository.CustomKm_reportAPI;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.enums.BooleanState;

public interface KM_ReportAPI extends CommonRepository<KM_Report, Long>, CustomKm_reportAPI {

	@Memo("교수가 레포트의 정보를 가져오는 메소드")
	KM_Report findByKmClass_KmUser_IdAndSeq(String id, long seq);

	@Memo("학생이 레포트의 정보를 가져오는 메소드")
	KM_Report findBySeqAndKmClass_KmSignUpClassForStu_SignUpStateAndKmClass_KmUser_Id(long idx, BooleanState booleanState,
		String id);

	List<KM_Report> findByKmClass_SeqOrderBySeqDesc(long seq, Pageable pageable);

	long countByKmClass_Seq(long seq);
}
