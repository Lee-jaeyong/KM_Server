package ljy_yjw.team_manage.system.service.update.joinTeam;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;

@Service
public class JoinTeamOneUpdateService {

	@Autowired
	TeamDAO teamDAO;

	@Memo("팀 승인 요청을 승낙하는 메소드")
	public JoinTeam signUpSuccess(long seq) {
		JoinTeam successJoinTeam = JoinTeam.builder().seq(seq).build();
		teamDAO.signUpSuccess(seq);
		return successJoinTeam;
	}

	@Memo("팀 승인 요청을 거절하는 메소드")
	public JoinTeam signUpFaild(String reson, long seq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("reson", reson);
		map.put("seq", seq);
		teamDAO.signUpFaild(map);
		return JoinTeam.builder().seq(seq).reson(reson).build();
	}
}
