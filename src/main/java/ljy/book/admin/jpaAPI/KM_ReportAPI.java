package ljy.book.admin.jpaAPI;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.customRepository.CustomKm_reportAPI;
import ljy.book.admin.entity.KM_Report;

public interface KM_ReportAPI extends CommonRepository<KM_Report, Long>, CustomKm_reportAPI {
	//List<Km_ReportProjection> findByKmClass_ClassIdxOrderByReportIdxDesc(long idx, Pageable pageRequest);

	//Long countByKmClass_ClassIdx(long idx);

	//Km_ReportProjection findByReportIdx(Long idx);

//	@Modifying
//	@Transactional
//	@Query("UPDATE Km_Report p" + " SET p.reportTitle=:title," + "p.reportContent=:content,"
//			+ "p.reportStartDate=:startDate," + "p.reportEndDate=:endDate " + "WHERE p.reportIdx=:idx")
//	void update(@Param("title") String title, @Param("content") String content, @Param("startDate") String startDate,
//			@Param("endDate") String endDate, @Param("idx") Long idx);

//	@Modifying
//	@Transactional
//	@Query("DELETE FROM Km_Report p WHERE p.reportIdx=:reportIdx")
	//void deleteByReportIdx(@Param("reportIdx") Long idx);
}
