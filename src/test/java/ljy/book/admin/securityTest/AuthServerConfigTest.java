package ljy.book.admin.securityTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.security.KM_UserService;

public class AuthServerConfigTest extends CommonTestConfig {

	@Autowired
	KM_UserService km_userService;

	@Autowired
	MockMvc mvc;

	@Test
	public void getAuthToken() throws Exception {
		KM_user km_user = new KM_user();
		km_user.setId("dlwodyd202");
		km_user.setPass("dlwodyd");
		km_user.setEmail("wodyd202@naver.com");
		km_user.setName("이재용");
		Set<UserRule> rules = new HashSet<UserRule>();
		rules.add(UserRule.PROFESSER);
		rules.add(UserRule.STUDENT);
		km_user.setUserRule(rules);

		this.km_userService.save(km_user);

		String clientId = "KMapp";
		String clientPass = "pass";

		this.mvc.perform(post("/oauth/token").with(httpBasic(clientId, clientPass)).param("username", "dlwodyd202")
				.param("password", "dlwodyd").param("grant_type", "password")).andDo(print())
				.andExpect(status().isOk());
	}
}
