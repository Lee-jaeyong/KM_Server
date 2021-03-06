package ljy_yjw.team_manage.system.dbConn.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ljy_yjw.team_manage.system.domain.entity.Team;

@Mapper
public interface TeamDAO {

	void update(Team team);

	void finished(Team team);
	
	void unFinished(Team team);
	
	void delete(String code);

	void outTeam(HashMap<String, Object> map);

	void signUpSuccess(long seq);

	void signUpFaild(HashMap<String, Object> map);

	Team getTeam(HashMap<String, Object> map);

	List<Team> getTeamsUnfinished(String id);

	List<Team> getTeamsFinished(String id);

	List<Team> getTeamsFinishedToday(String id);
	
	List<Team> checkTeamAuth(HashMap<String, Object> map);

	List<Team> getMySignUpList(String id);

	boolean checkTeamAuthBool(HashMap<String, Object> map);
}
