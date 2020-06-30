package ljy_yjw.team_manage.system;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.dto.team.TeamDTO;
import ljy_yjw.team_manage.system.domain.dto.team.TeamInsertDTO;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.security.UsersService;
import ljy_yjw.team_manage.system.service.insert.joinTeam.JoinTeamOneInsertService;
import ljy_yjw.team_manage.system.service.insert.plan.PlanByUserOneInsertService;
import ljy_yjw.team_manage.system.service.insert.team.TeamOneInsertService;
import ljy_yjw.team_manage.system.service.insert.todoList.TodoListOneInsertService;

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
	UsersService userService;

	@Autowired
	TeamOneInsertService teamInsertService;

	@Autowired
	JoinTeamOneInsertService joinTeamInsertService;

	@Autowired
	PlanByUserOneInsertService planByUserOneInsertService;

	@Autowired
	TodoListOneInsertService todoListOneInsertService;

	protected String token;
	protected String bearer;
	protected String auth;
	protected long teamSeq;
	protected String teamCode = "";
	protected final String AUTHRIZATION = "Authorization";

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

	protected UserDTO createUser(String id, String pass, String name, String email) {
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setPass(pass);
		user.setEmail(email);
		user.setName(name);
		return user;
	}

	protected Users saveUser(String id, String pass, String name, String email) {
		return userService.save(this.createUser(id, pass, name, email));
	}

	protected TeamInsertDTO createTeam(String name, LocalDate start, LocalDate end, String description) {
		TeamInsertDTO team = new TeamInsertDTO();
		team.setName(name);
		team.setStartDate(start);
		team.setEndDate(end);
		team.setDescription(description);
		return team;
	}

	protected Team saveTeam(String name, LocalDate start, LocalDate end, String description, Users user) {
		TeamInsertDTO team = this.createTeam(name, start, end, description);
		return teamInsertService.insertTeam(team.parseThis2Team(user));
	}

	protected PlanByUser savePlan(LocalDate start, LocalDate end, String tag, Team team, Users user) {
		PlanByUser planByUser = new PlanByUser();
		planByUser.setEnd(CustomDate.LocalDate2Date(end));
		planByUser.setStart(CustomDate.LocalDate2Date(start));
		planByUser.setState(BooleanState.YES);
		planByUser.setTag(tag);
		planByUser.setTeam(team);
		planByUser.setTeamPlan(BooleanState.NO);
		planByUser.setTodoList(null);
		planByUser.setUser(user);
		return planByUserOneInsertService.insertPlanByUser(planByUser);
	}

	protected JoinTeam saveJoinTeam(Team team, Users user) {
		return joinTeamInsertService.insertJoinTeam(team, user);
	}

	protected TodoList saveTodo(String title, PlanByUser plan, Users user) {
		TodoList todoList = new TodoList();
		todoList.setPlanByUser(plan);
		todoList.setTitle(title);
		todoList.setState(BooleanState.YES);
		todoList.setIng(BooleanState.YES);
		todoList.setUser(user);
		return todoListOneInsertService.insertTodoList(todoList);
	}

	protected String createTeamThenGet(TeamDTO team) throws Exception {
		login("wodyd2", "a");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, team);
		Map<String, Object> map = successTest(action);
		return map.get("code").toString();
	}

	protected void badRequestTest(MockHttpServletRequestBuilder action) throws Exception {
		this.mvc.perform(action).andDo(print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"));
	}

	protected void unAuthTest(MockHttpServletRequestBuilder action) throws Exception {
		this.mvc.perform(action).andDo(print()).andExpect(status().isUnauthorized()).andExpect(jsonPath("$.status").value("401"));
	}

	protected Map<String, Object> successTest(MockHttpServletRequestBuilder action) throws Exception {
		Jackson2JsonParser jackson = new Jackson2JsonParser();
		MockHttpServletResponse response = this.mvc.perform(action).andDo(print()).andExpect(status().isOk()).andReturn()
			.getResponse();
		return jackson.parseMap(response.getContentAsString());
	}

	protected MockHttpServletRequestBuilder getMockHttpServletRequest(MockHttpServletRequestBuilder action, Object obj)
		throws JsonProcessingException {
		return action.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(obj));
	}
}
