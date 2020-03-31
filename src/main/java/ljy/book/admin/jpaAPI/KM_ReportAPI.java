package ljy.book.admin.jpaAPI;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.customRepository.CustomKm_reportAPI;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.enums.BooleanState;

public interface KM_ReportAPI extends CommonRepository<KM_Report, Long>, CustomKm_reportAPI {
	KM_Report findByKmClass_KmUser_IdAndSeq(String id, long seq);

	List<KM_Report> findByKmClass_SeqOrderBySeqDesc(long seq, Pageable pageable);

	long countByKmClass_Seq(long seq);
}
