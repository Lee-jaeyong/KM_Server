package ljy.book.admin.customRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.entity.KM_Report;

public interface CustomKm_reportAPI {
	long countSearch_Km_report(long seq, CustomSearchObject customSearchObj);

	List<KM_Report> search_Km_report(long seq, Pageable pageable, CustomSearchObject customSearchObj);
}
