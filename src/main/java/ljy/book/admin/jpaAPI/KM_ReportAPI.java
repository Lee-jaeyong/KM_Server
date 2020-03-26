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
	List<KM_Report> findByKmClass_SeqOrderBySeqDesc(long seq, Pageable pageable);

	long countByKmClass_Seq(long seq);

	@Modifying
	@Transactional
	@Query("UPDATE KM_Report p SET " + "p.name = :name," + "p.startDate = :startDate," + "p.endDate = :endDate,"
			+ "p.content = :content," + "p.submitOverDue_state = :submitOverDue_state,"
			+ "p.showOtherReportOfStu_state = :showOtherReportOfStu_state" + " WHERE p.seq = :seq")
	void updateByReportIdx(@Param("name") String name, @Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("content") String content,
			@Param("submitOverDue_state") BooleanState submitOverDue_state,
			@Param("showOtherReportOfStu_state") BooleanState showOtherReportOfStu_state, @Param("seq") long seq);
}
