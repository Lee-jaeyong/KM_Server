package ljy.book.admin.securityTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.security.KM_UserService;

public class KM_userServiceTest extends CommonTestConfig {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	KM_UserService km_userService;

	@Test
	@Ignore
	public void findByUserId() {
		KM_user user = new KM_user();
		user.setId("dlwodyd202");
		user.setPass("dlwodyd");
		user.setName("이재용");
		Set<UserRule> rules = new HashSet<UserRule>();
		rules.add(UserRule.PROFESSER);
		user.setUserRule(rules);
		user.setEmail("wodyd202@naver.com");

		km_userService.save(user);

		UserDetailsService userDetailesService = km_userService;
		UserDetails userDetailes = userDetailesService.loadUserByUsername(user.getId());

		assertThat(userDetailes.getPassword()).isEqualTo(user.getPass());
	}

	@Test
	public void findByUserId_Fail() {
		// Expected
		String userId = "dbswldnjs";
		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(userId));

		// When
		km_userService.loadUserByUsername(userId);
	}
}