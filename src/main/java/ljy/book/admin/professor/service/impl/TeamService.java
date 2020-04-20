package ljy.book.admin.professor.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.custom.anotation.Memo;
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
	@Memo("팀 코드를 통해 해당 유저가 접근 권한이 있는가를 판단함")
	public boolean checkTeamAuth(Users user, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("code", code);
		if (teamDAO.checkTeamAuth(map).size() != 0)
			return true;
		return false;
	}

	@Transactional
	@Memo("만료된 팀을 가져옴")
	public Page<Team> getTeamsFinished(Users user) {
		List<Team> result = teamDAO.getTeamsFinished(user.getId());
		return new PageImpl<Team>(result);
	}

	@Transactional
	@Memo("해당 유저가 해당 팀에 대한 접근 권한이 있는지를 확인")
	public TeamDTO checkAuthSuccessThenGetTeam(long seq, Users user) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("id", user.getId());
		return teamDAO.getTeam(map);
	}

	@Transactional
	@Memo("기간이 만료되지 않은 팀을 모두 가져옴")
	public Page<Team> getTeamsUnfinished(Users user) {
		List<Team> result = teamDAO.getTeamsUnfinished(user.getId());
		return new PageImpl<Team>(result);
	}

	@Transactional
	@Memo("코드를 통해 팀의 정보를 가져옴")
	public Team getTeamByCode(String code) {
		return teamAPI.findByCode(code);
	}

	@Transactional
	@Memo("그 팀의 리더가 맞는지 코드를 통해 확인하고 맞다면 그 팀을 리턴")
	public Team checkTeamByUserAndCode(String code, Users user) {
		return teamAPI.findByCodeAndTeamLeader_Id(code, user.getId());
	}

	@Transactional
	@Memo("그 팀의 리더가 맞는지 고유번호를 통해 확인")
	public boolean checkTeamByUser(TeamDTO team, Users user) {
		if (teamAPI.findBySeqAndTeamLeader_Id(team.getSeq(), user.getId()) == null) {
			return false;
		}
		return true;
	}

	@Transactional
	@Memo("팀을 수정")
	public boolean update(TeamDTO team) {
		teamDAO.update(team);
		return true;
	}

	@Transactional
	@Memo("팀을 추가")
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
	@Memo("팀을 삭제")
	public boolean delete(TeamDTO team) {
		teamDAO.delete(team);
		return true;
	}

	@Transactional
	@Memo("팀의 전체 진척도를 변경")
	public boolean updateProgress(TeamDTO team, TeamDTO teamContainProgess) {
		team.setProgress(teamContainProgess.getProgress());
		teamDAO.updateProgress(team);
		return true;
	}

}