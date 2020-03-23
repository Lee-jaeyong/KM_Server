package ljy.book.admin.customRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_class;

public interface CustomKm_reportAPI {
	long countSearch_Km_report(KM_class km_class, KM_Report km_report);

	List<KM_Report> search_Km_report(KM_class km_class, KM_Report km_report, Pageable pageable);
}
