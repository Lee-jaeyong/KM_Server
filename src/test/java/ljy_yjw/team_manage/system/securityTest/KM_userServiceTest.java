package ljy_yjw.team_manage.system.securityTest;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.security.UsersService;

public class KM_userServiceTest extends CommonTestConfig {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	UsersService km_userService;

//	@Test
//	@Ignore
//	public void findByUserId() {
//		KM_user user = new KM_user();
//		user.setId("dlwodyd202");
//		user.setPass("dlwodyd");
//		user.setName("이재용");
//		Set<UserRule> rules = new HashSet<UserRule>();
//		rules.add(UserRule.PROFESSER);
//		user.setUserRule(rules);
//		user.setEmail("wodyd202@naver.com");
//
//		km_userService.save(user);
//
//		UserDetailsService userDetailesService = km_userService;
//		UserDetails userDetailes = userDetailesService.loadUserByUsername(user.getId());
//
//		assertThat(userDetailes.getPassword()).isEqualTo(user.getPass());
//	}
//
//	@Test
//	public void findByUserId_Fail() {
//		// Expected
//		String userId = "dbswldnjs";
//		expectedException.expect(UsernameNotFoundException.class);
//		expectedException.expectMessage(Matchers.containsString(userId));
//
//		// When
//		km_userService.loadUserByUsername(userId);
//	}
}
