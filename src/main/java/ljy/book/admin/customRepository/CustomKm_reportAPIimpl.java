package ljy.book.admin.customRepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_class;

public class CustomKm_reportAPIimpl implements CustomKm_reportAPI {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<KM_Report> search_Km_report(KM_class km_class, KM_Report km_report, Pageable pageable) {
//		String sql = "SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n"
//				+ "AND report_title LIKE ?\r\n" + "AND report_start_date >= ?\r\n"
//				+ "AND report_end_date <= ? ORDER BY report_idx DESC LIMIT ?,?";
//		if (km_report.getReportStartDate().equals("") || km_report.getReportEndDate().equals("")) {
//			sql = "SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n" + "AND report_title LIKE ?\r\n"
//					+ " ORDER BY report_idx DESC LIMIT ?,?";
//			return em.createNativeQuery(sql, KM_Report.class).setParameter(1, km_class.getClassIdx())
//					.setParameter(2, "%" + km_report.getReportTitle() + "%")
//					.setParameter(3, pageable.getPageNumber() * pageable.getPageSize())
//					.setParameter(4, pageable.getPageSize()).getResultList();
//		}
//		return em.createNativeQuery(sql, KM_Report.class).setParameter(1, km_class.getClassIdx())
//				.setParameter(2, "%" + km_report.getReportTitle() + "%").setParameter(3, km_report.getReportStartDate())
//				.setParameter(4, km_report.getReportEndDate())
//				.setParameter(5, pageable.getPageNumber() * pageable.getPageSize())
//				.setParameter(6, pageable.getPageSize()).getResultList();
		return null;
	}

	@Override
	public long countSearch_Km_report(KM_class km_class, KM_Report km_report) {
//		String sql = "SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n"
//				+ "AND report_title LIKE ?\r\n" + "AND report_start_date >= ?\r\n" + "AND report_end_date <= ?";
//		if (km_report.getReportStartDate().equals("") || km_report.getReportEndDate().equals("")) {
//			sql = "SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n" + "AND report_title LIKE ?\r\n"
//					+ "AND report_start_date >= ?\r\n" + "AND report_end_date <= ?";
//			return em
//					.createNativeQuery("SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n"
//							+ "AND report_title LIKE ?", KM_Report.class)
//					.setParameter(1, km_class.getClassIdx()).setParameter(2, "%" + km_report.getReportTitle() + "%")
//					.getResultList().size();
//		}
//		return em
//				.createNativeQuery("SELECT *\r\n" + "FROM km_report\r\n" + "WHERE km_class_idx = ?\r\n"
//						+ "AND report_title LIKE ?\r\n" + "AND report_start_date >= ?\r\n" + "AND report_end_date <= ?",
//						KM_Report.class)
//				.setParameter(1, km_class.getClassIdx()).setParameter(2, "%" + km_report.getReportTitle() + "%")
//				.setParameter(3, km_report.getReportStartDate()).setParameter(4, km_report.getReportEndDate())
//				.getResultList().size();
		return 0;
	}
}
