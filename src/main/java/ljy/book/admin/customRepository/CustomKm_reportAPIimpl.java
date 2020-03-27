package ljy.book.admin.customRepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.entity.KM_Report;

public class CustomKm_reportAPIimpl implements CustomKm_reportAPI {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<KM_Report> search_Km_report(long seq, Pageable pageable, CustomSearchObject customSearchObj) {
		String type = customSearchObj.getSearchType();
		String searchName = customSearchObj.getName();
		String sql = "SELECT * " + "FROM km_report p " + "WHERE p.km_class_seq = " + seq;
		if (!type.equals("")) {
			sql += " AND p." + type + " LIKE '%" + searchName + "%' ";
		}
		if (!customSearchObj.getStartDate().equals("") && !customSearchObj.getEndDate().equals("")) {
			sql += " AND p.start_date >= '" + customSearchObj.getStartDate() + "' AND p.end_date <= '"
					+ customSearchObj.getEndDate() + "'";
		} else {
			if (!customSearchObj.getStartDate().equals("") && customSearchObj.getEndDate().equals("")) {
				sql += " AND p.start_date = '" + customSearchObj.getStartDate() + "'";
			}
			if (customSearchObj.getStartDate().equals("") && !customSearchObj.getEndDate().equals("")) {
				sql += " AND p.end_date= '" + customSearchObj.getEndDate() + "'";
			}
		}
		sql += " ORDER BY p.seq DESC LIMIT ?,?";
		return em.createNativeQuery(sql, KM_Report.class)
				.setParameter(1, pageable.getPageNumber() * pageable.getPageSize())
				.setParameter(2, pageable.getPageSize()).getResultList();
	}

	@Override
	public long countSearch_Km_report(long seq, CustomSearchObject customSearchObj) {
		String type = customSearchObj.getSearchType();
		String searchName = customSearchObj.getName();
		String sql = "SELECT * " + "FROM km_report p " + "WHERE p.km_class_seq = " + seq;
		if (!type.equals("")) {
			sql += " AND p." + type + " LIKE '%" + searchName + "%' ";
		}
		if (!customSearchObj.getStartDate().equals("") && !customSearchObj.getEndDate().equals("")) {
			sql += " AND p.start_date >= '" + customSearchObj.getStartDate() + "' AND p.end_date <= '"
					+ customSearchObj.getEndDate() + "'";
		} else {
			if (!customSearchObj.getStartDate().equals("") && customSearchObj.getEndDate().equals("")) {
				sql += " AND p.start_date = '" + customSearchObj.getStartDate() + "'";
			}
			if (customSearchObj.getStartDate().equals("") && !customSearchObj.getEndDate().equals("")) {
				sql += " AND p.end_date= '" + customSearchObj.getEndDate() + "'";
			}
		}
		return em.createNativeQuery(sql, KM_Report.class).getResultList().size();
	}
}
