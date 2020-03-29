package ljy.book.admin.securityConfigTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.security.KM_UserService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthServerConfigTest {

	@Autowired
	KM_UserService km_userService;

	@Autowired
	MockMvc mvc;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Before
	public void init() {
	}

	@Test
	public void getAuthToken() throws Exception {
		KM_user user = new KM_user();
		user.setId("dlwodyd202");
		user.setPass("dlwodyd");
		user.setName("이재용");
		Set<UserRule> rules = new HashSet<UserRule>();
		rules.add(UserRule.PROFESSER);
		user.setUserRule(rules);
		user.setEmail("wodyd202@naver.com");

		km_userService.save(user);

		String clientId = "KMAPP";
		String clientPass = "pass";
		this.mvc.perform(post("/oauth/token").with(httpBasic(clientId, clientPass)).param("username", "dlwodyd202")
				.param("password", "dlwodyd").param("grant_type", "password")).andExpect(status().isOk()).andDo(print());
	}

}
