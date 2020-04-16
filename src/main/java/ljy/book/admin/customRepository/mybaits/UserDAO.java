package ljy.book.admin.customRepository.mybaits;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.UserDTO;

@Mapper
public interface UserDAO {

	@Memo("회원 정보를 수정하는 메소드")
	void update(UserDTO user);

	@Memo("회원을 탈퇴하는 메소드")
	void delete(Users user);
}
