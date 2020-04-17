package ljy.book.admin.professor.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.customRepository.mybaits.TeamDAO;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.TeamAPI;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamService {

	@Autowired
	TeamAPI teamAPI;

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	CustomCodeCreator codeCreator;

	@Transactional
	public Page<Team> getTeamsFinished(Users user) {
		List<Team> result = teamDAO.getTeamsFinished(user.getId());
		return new PageImpl<Team>(result);
	}

	@Transactional
	public Page<Team> getTeamsUnfinished(Users user) {
		List<Team> result = teamDAO.getTeamsUnfinished(user.getId());
		return new PageImpl<Team>(result);
	}

	@Transactional
	public Team getTeamByCode(String code) {
		return teamAPI.findByCode(code);
	}

	@Transactional
	public boolean checkTeamByUserAndCode(String code, Users user) {
		if (teamAPI.findByCodeAndTeamLeader_Id(code, user.getId()) == null)
			return false;
		return true;
	}

	@Transactional
	public boolean checkTeamByUser(TeamDTO team, Users user) {
		if (teamAPI.findBySeqAndTeamLeader_Id(team.getSeq(), user.getId()) == null) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean update(TeamDTO team) {
		teamDAO.update(team);
		return true;
	}

	@Transactional
	public Team save(TeamDTO team, Users user) {
		Team saveTeam = new Team();
		saveTeam.setName(team.getName());
		saveTeam.setDescription(team.getDescription());
		saveTeam.setStartDate(team.getStartDate());
		saveTeam.setEndDate(team.getEndDate());
		saveTeam.setProgress((byte) 0);
		saveTeam.setFlag(BooleanState.YSE);
		Users belongUser = new Users();
		belongUser.setSeq(user.getSeq());
		belongUser.addTeam(saveTeam);
		Team returnTeam = teamAPI.save(saveTeam);
		String code = codeCreator.createCode(Long.toString(returnTeam.getSeq())) + "" + returnTeam.getSeq();
		returnTeam.setCode(code);
		return returnTeam;
	}

	@Transactional
	public boolean delete(TeamDTO team) {
		teamDAO.delete(team);
		return true;
	}

	@Transactional
	public boolean updateProgress(TeamDTO team, TeamDTO teamContainProgess) {
		team.setProgress(teamContainProgess.getProgress());
		teamDAO.updateProgress(team);
		return true;
	}

}