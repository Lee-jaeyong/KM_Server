package ljy_yjw.team_manage.system.service.read.team;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.TeamAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;

@Service
public class TeamReadService {

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	TeamAPI teamAPI;

	@Memo("자신이 팀장인 모든 팀 리스트 가져오기(만료되지 않은)")
	public List<Team> getMyTeamList(String id) {
		return teamAPI.findByTeamLeader_IdAndEndDateGreaterThanEqualAndFlag(id, new Date(), BooleanState.YES);
	}

	@Memo("자신이 이미 등록한 팀인지 확인하는 테스트")
	public Team getTeamByNameAndId(String name, String id) throws TeamCodeNotFountException {
		Team team = teamAPI.findByNameAndTeamLeader_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndFlag(name, id,
			new Date(), new Date(), BooleanState.YES);
		return team;
	}

	@Memo("코드를 통해 팀의 정보를 가져옴")
	public Team getTeamByCode(String code) throws TeamCodeNotFountException {
		Team team = teamAPI.findByCode(code);
		if (team == null) {
			throw new TeamCodeNotFountException("해당 코드의 팀이 존재하지 않습니다.");
		}
		return team;
	}

	@Memo("만료된 팀을 가져옴")
	public List<Team> getTeamListFinished(String id) {
		return teamDAO.getTeamsFinished(id);
	}

	@Memo("만료되지 않은(진행중인) 팀을 가져옴")
	public List<Team> getTeamListUnFinished(String id) {
		return teamDAO.getTeamsUnfinished(id);
	}
	
	@Memo("오늘이 마감일인 팀 모두 가져옴")
	public List<Team> getTeamFinishToday(String id){
		return teamDAO.getTeamsFinishedToday(id);
	}
}
