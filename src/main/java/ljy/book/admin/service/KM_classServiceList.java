package ljy.book.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.request.KM_classVO;

public interface KM_classServiceList {
	public Page<KM_class> getClassListPage(String id, Pageable pageable);

	public KM_classVO getClassInfo(long idx, String id);
}
