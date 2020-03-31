package ljy.book.admin.securityTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.security.KM_UserService;

public class AuthServerConfigTest extends CommonTestConfig {

	@Autowired
	KM_UserService km_userService;

	@Autowired
	MockMvc mvc;

	@Before
	public void init() {
		this.createUser();
	}

	@Test
	@Ignore
	public void getAuthToken() throws Exception {

		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/oauth/token").with(httpBasic(clientId, clientPass))
				.param("username", "dlwodyd202").param("password", "dlwodyd").param("grant_type", "password"))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("km_auth", requestParameters(parameterWithName("username").description("아이디"),
				parameterWithName("password").description("비밀번호"), parameterWithName("grant_type").description("인증 타입")),
//						requestFields(
//						),
				responseFields(fieldWithPath("access_token").type(JsonFieldType.STRING).description("인증 토큰"),
					fieldWithPath("token_type").type(JsonFieldType.STRING).description("토큰 타입"),
					fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("만료 후 토큰"),
					fieldWithPath("expires_in").type(JsonFieldType.NUMBER).description("토큰 만료 시간"),
					fieldWithPath("scope").type(JsonFieldType.STRING).description("토큰 스콥"))));
	}
}
