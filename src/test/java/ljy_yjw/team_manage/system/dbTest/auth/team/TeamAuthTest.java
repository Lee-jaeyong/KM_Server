package ljy_yjw.team_manage.system.dbTest.auth.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamAuthTest {

	@Autowired
	TeamAuthService teamAuthService;

	@Test
	public void teamAuthCheckTest() {
		Users user = new Users();
		user.setId("dlwodyd202");
	}

	@Test
	public void checkTeamLeader_TEST() {
		Users user = new Users();
		user.setId("dbswldnjs");
	}

}
