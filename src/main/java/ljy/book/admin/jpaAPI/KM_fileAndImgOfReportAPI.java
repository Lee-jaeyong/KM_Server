package ljy.book.admin.jpaAPI;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_fileAndImgOfReport;

public interface KM_fileAndImgOfReportAPI extends CommonRepository<KM_fileAndImgOfReport, Long> {
	List<KM_fileAndImgOfReport> findByKmReport_seqAndKmReport_KmClass_KmUser_Id(long seq, String id);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM km_file_and_img_of_report WHERE km_report_seq=:idx AND file_name=:fileName")
	void deleteByKmReport_seqAndFileName(@Param("idx") long idx, @Param("fileName") String fileName);
}
