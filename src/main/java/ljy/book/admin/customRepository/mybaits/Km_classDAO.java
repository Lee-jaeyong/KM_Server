package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.custom.anotation.Memo;

@Mapper
public interface Km_classDAO {

	@Memo("수업을 수정하는 메소드")
	void update(HashMap<String, String> map);

	@Memo("수업 승인 요청을 승낙하는 메소드")
	void changeStateToSignUpState(HashMap<String, Object> map);
}
