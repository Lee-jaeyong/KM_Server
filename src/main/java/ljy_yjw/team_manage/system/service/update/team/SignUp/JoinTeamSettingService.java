package ljy_yjw.team_manage.system.service.update.team.SignUp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.security.UsersService;

@Service
public class JoinTeamSettingService {

	@Autowired
	UsersService userService;

	@Memo("승인 신청한 사람들의 이미지를 세팅")
	public void imgSetting(List<JoinTeam> joinPerson) throws IOException {
		for (JoinTeam c : joinPerson) {
			c.getUser().setMyImg(c.getUser().getImageByte(userService));
		}
	}

	@Memo("리스트 값 중 승인이 완료되지 않은 사람들의 명단을 가져옴")
	public void filterStateNo(List<JoinTeam> joinTeamList) {
		joinTeamList = joinTeamList.stream().filter(c -> c.getState() == BooleanState.NO && c.getReson() == null)
			.collect(Collectors.toList());
	}
}
