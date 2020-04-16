package ljy.book.admin.professor.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.TeamJoinRequestAPI;

@Service
public class TeamJoinRequestService {

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
		joinTeam.setState(BooleanState.YSE);
		joinTeam.setDate("000");
		joinTeam.setUser(user);
		team.addJoinPerson(joinTeam);
		return teamJoinRequestAPI.save(joinTeam);
	}
}
