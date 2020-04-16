package ljy.book.admin.professor.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.jpaAPI.TeamAPI;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamService {

	@Autowired
	TeamAPI teamAPI;

	@Autowired
	CustomCodeCreator codeCreator;

	@Transactional
	public Team save(TeamDTO team, Users user) {
		Team saveTeam = new Team();
		saveTeam.setName(team.getName());
		saveTeam.setDescription(team.getDescription());
		saveTeam.setStartDate(team.getStartDate());
		saveTeam.setEndDate(team.getEndDate());
		saveTeam.setProgress((byte) 0);
		Users belongUser = new Users();
		belongUser.setSeq(user.getSeq());
		belongUser.addTeam(saveTeam);
		Team returnTeam = teamAPI.save(saveTeam);
		String code = codeCreator.createCode(Long.toString(returnTeam.getSeq())) + "" + returnTeam.getSeq();
		returnTeam.setCode(code);
		return returnTeam;
	}

}