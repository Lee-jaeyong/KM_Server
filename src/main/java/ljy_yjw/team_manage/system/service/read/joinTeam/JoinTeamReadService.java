package ljy_yjw.team_manage.system.service.read.joinTeam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.TeamJoinRequestAPI;
import ljy_yjw.team_manage.system.dbConn.jpa.projections.MyJoinTeam;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Service
public class JoinTeamReadService {

	@Autowired
	TeamJoinRequestAPI teamJoinRequestAPI;

	@Autowired
	TeamDAO teamDAO;

	@Memo("해당 시퀀스의 승인요청을 가져오는 메소드")
	public JoinTeam getJoinTeam(long seq) {
		return teamJoinRequestAPI.findBySeq(seq);
	}

	@Memo("팀의 승인요청 명단을 가져오는 메소드")
	public List<JoinTeam> getJoinTeamList(String code) {
		return teamJoinRequestAPI.findByStateAndTeam_CodeAndResonIsNull(BooleanState.NO, code);
	}

	@Memo("자신이 신청한 팀 정보 가져오기")
	public List<MyJoinTeam> getMyJoinTeam(String id) {
		return teamJoinRequestAPI.findByUser_Id(id);
	}

}
