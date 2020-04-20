package ljy.book.admin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.requestDTO.UserDTO;
import ljy.book.admin.professor.service.impl.TeamService;
import ljy.book.admin.security.UsersService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestCommons.class)

public class CommonTestConfig {
	protected String clientId = "KMapp";

	protected String clientPass = "pass";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	public MockMvc mvc;

	@Autowired
	public ObjectMapper objMapper;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	TeamService teamService;

	@Autowired
	UsersService userService;

	protected String token;
	protected String bearer;
	protected String auth;
	protected long teamSeq;
	protected String teamCode = "";
	protected final String AUTHRIZATION = "Authorization";

	public Users createUser(String id, String pass, String name, String email) {
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setPass(pass);
		user.setName(name);
		user.setEmail(email);
		return userService.save(user);
	}

	public Team createTeam(String name, String start, String end, String description, Users user) {
		TeamDTO team = TeamDTO.builder().name(name).startDate(start).endDate(end).description(description).build();
		return teamService.save(team, user);
	}

	protected void login(String id, String pass) throws Exception {
		Jackson2JsonParser jackson = new Jackson2JsonParser();
		MockHttpServletResponse response = this.mvc.perform(RestDocumentationRequestBuilders.post("/oauth/token")
			.with(httpBasic(clientId, clientPass)).param("username", id).param("password", pass).param("grant_type", "password"))
			.andReturn().getResponse();

		Map<String, Object> jacksonMap = jackson.parseMap(response.getContentAsString());

		token = jacksonMap.get("access_token").toString();
		bearer = jacksonMap.get("token_type").toString();
		auth = bearer + " " + token;
	}

	protected void createTeam() throws Exception {
		TeamDTO saveTeam = new TeamDTO();
		saveTeam.setName("자바 프로젝트");
		saveTeam.setStartDate("2020-04-14");
		saveTeam.setEndDate("2020-10-10");
		saveTeam.setDescription("목표");
		String json = objMapper.writeValueAsString(saveTeam);
		Jackson2JsonParser jsonParser = new Jackson2JsonParser();
		ResultActions result = this.mvc
			.perform(post("/api/teamManage").header("Authorization", auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andExpect(status().isOk()).andDo(print());
		Map<String, Object> contentStringMap = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
		teamSeq = Long.parseLong(contentStringMap.get("seq").toString());
		System.out.println(result.andReturn().getResponse().getContentAsString());
		teamCode = contentStringMap.get("code").toString();
	}
}
