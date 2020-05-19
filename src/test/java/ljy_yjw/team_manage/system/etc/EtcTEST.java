package ljy_yjw.team_manage.system.etc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.service.insert.team.TeamOneInsertService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EtcTEST {

	@MockBean
	TeamOneInsertService teamOneInsertService;

	@Test
	public void test() {
		Team team = mock(Team.class);
		when(team.getName()).thenReturn("bb");
		assertThat("bb".equals(team.getName())).isTrue();
		List<Team> teamList = new ArrayList<Team>();
	}

}
