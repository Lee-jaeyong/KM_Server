package ljy_yjw.team_manage.system.service.update.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.security.UsersService;

@Service
public class UserSettingService {

	@Autowired
	UsersService userService;

	public void imgSetting(Users user) throws IOException {
		user.setMyImg(user.getImageByte(userService));
	}
}
