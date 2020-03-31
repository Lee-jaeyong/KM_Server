package ljy.book.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.request.KM_classVO;

public interface KM_classServiceList {
	
	@Memo("해당 사용자의 수업 리스트를 가져오는 메소드")
	public Page<KM_class> getClassListPage(String id, Pageable pageable);

	@Memo("해당 사용자의 수업 정보를 가져오는 메소드")
	public KM_classVO getClassInfo(long idx, String id);

	@Memo("해당 수업을 갖는지의 여부를 판단하는 메소드")
	public boolean checkByKm_user(long idx, String id);
}
