package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.entity.Users;

@Mapper
public interface UserDAO {

	@Memo("회원 정보를 수정하는 메소드")
	void update(UserDTO user);

	@Memo("회원을 탈퇴하는 메소드")
	void delete(Users user);

	@Memo("자신의 사진을 등록하는 메소드")
	void insertImage(HashMap<String, Object> map);

	@Memo("자신의 사진을 삭제하는 메소드")
	void fileDelete(HashMap<String, Object> map);
}
