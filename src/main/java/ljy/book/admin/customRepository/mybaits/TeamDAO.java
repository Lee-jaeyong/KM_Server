package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.entity.Team;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Mapper
public interface TeamDAO {
	void update(TeamDTO team);

	void delete(TeamDTO team);

	void updateProgress(TeamDTO team);

	void signUpSuccess(long seq);

	void signUpFaild(HashMap<String, Object> map);

	TeamDTO getTeam(HashMap<String, Object> map);

	List<Team> getTeamsUnfinished(String id);

	List<Team> getTeamsFinished(String id);

	List<Team> checkTeamAuth(HashMap<String, Object> map);
}
