package ljy.book.admin.jpaAPI;

import java.util.List;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_fileAndImgOfReport;

public interface KM_fileAndImgOfReportAPI extends CommonRepository<KM_fileAndImgOfReport, Long> {
	List<KM_fileAndImgOfReport> findByKmReport_seq(long seq);
}
