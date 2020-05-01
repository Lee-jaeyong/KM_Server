package ljy.book.admin.professor.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(key = "#user.id.toString() + #code.toString()", value = "teamAuthByCode")
	public boolean checkTeamAuth(Users user, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("code", code);
		if (teamDAO.checkTeamAuth(map).size() != 0)
			return true;
		return false;
	}

	@Transactional
	@Memo("팀 코드를 통해 해당 유저가 접근 권한이 있는가를 판단 후 있다면 팀 정보를 가져옴")
	public TeamDTO checkAuthByCodeSuccessThenGet(String code, Users user) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("code", code);
		List<TeamDTO> team = teamDAO.checkTeamAuth(map);
		if (team.size() == 0) {
			return null;
		}
		return team.get(0);
	}

	@Transactional
	@Memo("해당 유저가 해당 팀에 대한 접근 권한이 있는지를 확인")
	@Cacheable(key = "#user.id.toString() + #code.toString()", value = "teamAuthByUserAndCode")
	public TeamDTO checkAuthSuccessThenGetTeam(String code, Users user) {
		System.out.println("asanksga");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("id", user.getId());
		return teamDAO.getTeam(map);
	}

	@Transactional
	@Memo("만료된 팀을 가져옴")
	public Page<Team> getTeamsFinished(Users user) {
		List<Team> result = teamDAO.getTeamsFinished(user.getId());
		return new PageImpl<Team>(result);
	}

	@Transactional
	@Memo("기간이 만료되지 않은 팀을 모두 가져옴")
	@Cacheable(key = "#user.id", value = "team")
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
	@Cacheable(key = "#user.id.toString() + #code.toString()", value = "teamLeaderCheck")
	public Team checkTeamByUser(String code, Users user) {
		return teamAPI.findByCodeAndTeamLeader_Id(code, user.getId());
	}

	@Transactional
	@Memo("팀을 수정")
	@CacheEvict(value = "teamInfo", key = "#team.code")
	public boolean update(TeamDTO team) {
		teamDAO.update(team);
		return true;
	}

	@Transactional
	@Memo("팀을 추가")
	@CacheEvict(value = "team", allEntries = true)
	public Team save(TeamDTO team, Users user) {
		Team saveTeam = new Team();
		saveTeam.setName(team.getName());
		saveTeam.setDescription(team.getDescription());
		saveTeam.setStartDate(team.getStartDate());
		saveTeam.setEndDate(team.getEndDate());
		saveTeam.setProgress(team.getProgress());
		saveTeam.setFlag(BooleanState.YES);
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
	@CacheEvict(value = "team", allEntries = true)
	public boolean delete(Team team) {
		TeamDTO deleteTeam = new TeamDTO();
		deleteTeam.setSeq(team.getSeq());
		teamDAO.delete(deleteTeam);
		return true;
	}

	@Transactional
	@Memo("팀의 전체 진척도를 변경")
	@CacheEvict(value = "teamInfo", key = "#team.code")
	public boolean updateProgress(String code, TeamDTO teamContainProgess) {
		teamContainProgess.setCode(code);
		teamDAO.updateProgress(teamContainProgess);
		return true;
	}

}