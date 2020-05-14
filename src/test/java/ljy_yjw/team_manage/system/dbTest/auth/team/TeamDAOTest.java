package ljy_yjw.team_manage.system.dbTest.auth.team;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.entity.Team;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamDAOTest {

	@Autowired
	TeamDAO teamDAO;

	@Test
	public void getTeamListFinished() {
		List<Team> list = teamDAO.getTeamsFinished("dlwodyd202");
		list.forEach(System.out::println);
	}
}
