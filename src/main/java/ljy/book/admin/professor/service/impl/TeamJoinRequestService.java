package ljy.book.admin.professor.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.customRepository.mybaits.TeamDAO;
import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.TeamJoinRequestAPI;

@Service
public class TeamJoinRequestService {

	@Autowired
	TeamDAO teamDAO;
	
	@Autowired
	TeamJoinRequestAPI teamJoinRequestAPI;

	@Transactional
	public boolean checkExistRequest(String code, Users user) {
		if (teamJoinRequestAPI.findByTeam_CodeAndUser_Id(code, user.getId()) != null)
			return true;
		return false;
	}

	@Transactional
	public JoinTeam saveJoinTeamReq(Team team, Users user) {
		JoinTeam joinTeam = new JoinTeam();
		joinTeam.setState(BooleanState.NO);
		joinTeam.setDate(CustomDate.getNowDate());
		joinTeam.setUser(user);
		team.addJoinPerson(joinTeam);
		return teamJoinRequestAPI.save(joinTeam);
	}

	@Transactional
	public JoinTeam checkJoinTeamAuth(long seq, Users user) {
		return teamJoinRequestAPI.findBySeqAndTeam_TeamLeader_Id(seq, user.getId());
	}

	@Transactional
	public boolean signUpSuccessJoinTeam(long seq) {
		teamDAO.signUpSuccess(seq);
		return true;
	}
	
	@Transactional
	public boolean signUpFaildJoinTeam(long seq,String reson) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("reson", reson);
		map.put("seq", seq);
		teamDAO.signUpFaild(map);
		return true;
	}
}
