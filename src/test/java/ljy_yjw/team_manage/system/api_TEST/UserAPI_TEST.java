package ljy_yjw.team_manage.system.api_TEST;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.dbConn.jpa.UsersAPI;
import ljy_yjw.team_manage.system.security.UsersService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAPI_TEST extends CommonTestConfig {
	
	@Autowired
	UsersAPI userAPI;

	@Autowired
	UsersService service;

	@Test
	public void test_10() throws Exception {
		super.login("wodyd2002", "d1489314aaaa");
	}
}
