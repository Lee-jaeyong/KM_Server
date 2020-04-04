package ljy.book.admin.jpaAPI;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_signUpClassForStu;
import ljy.book.admin.entity.enums.BooleanState;

public interface KM_signUpClassForStuAPI extends CommonRepository<KM_signUpClassForStu, Long> {

	@Memo("교수가 자신의 수업을 신청한 학생의 명단을 가져오는 메소드")
	List<KM_signUpClassForStu> findBySignUpStateAndKmClass_Seq(BooleanState booleanState, long idx, Pageable pageable);
}
