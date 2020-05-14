package ljy_yjw.team_manage.system.dbConn.mybatis;

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
}
