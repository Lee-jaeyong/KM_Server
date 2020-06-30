package ljy_yjw.team_manage.system.service.read.joinTeam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
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
	public JoinTeam getJoinTeam(long seq) throws NotFoundException {
		JoinTeam joinTeam = teamJoinRequestAPI.findBySeq(seq);
		if (joinTeam == null) {
			throw new NotFoundException("해당 승인 요청 번호가 존재하지 않습니다.");
		}
		return joinTeam;
	}

	@Memo("팀의 승인요청 명단을 가져오는 메소드")
	public List<JoinTeam> getJoinTeamList(String code) {
		return teamJoinRequestAPI.findByStateAndTeam_CodeAndResonIsNull(BooleanState.NO, code);
	}

	@Memo("자신이 신청한 팀 정보 가져오기")
	public List<MyJoinTeam> getMyJoinTeam(String id) {
		return teamJoinRequestAPI.findByUser_Id(id);
	}

	@Memo("삭제 되지 않은 승인 신청 명단 가져오기")
	public List<JoinTeam> getRealJoinPerson(List<JoinTeam> joinPerson) {
		List<JoinTeam> returnList = new ArrayList<JoinTeam>();
		for (int i = 0; i < joinPerson.size(); i++) {
			JoinTeam person = joinPerson.get(i);
			if (person.getState() == BooleanState.YES) {
				returnList.add(person);
			}
		}
		return returnList;
	}
}
