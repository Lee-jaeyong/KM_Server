package ljy_yjw.team_manage.system.etc;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EtcTEST {

	@Autowired
	TeamDAO teamDAO;

	@Test
	public void test() {
		teamDAO.checkTeamAuthBool(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "dlwodyd202");
				put("code", "dsadsa");
			}
		});
		teamDAO.checkTeamAuthBool(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "dlwodyd202");
				put("code", "dsadsa");
			}
		});
	}

}
