package ljy_yjw.team_manage.system.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.TeamDTO;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.security.UsersService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JoinTeamControllerTEST extends CommonTestConfig {

	@Autowired
	UsersService userService;

	Team otherTeam;

	@Transactional
	public void teamOfOtherUser() {
		Users user = new Users();
		user.setId("dbswldnjs202");
		user.setPass("dbswldnjs");

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail("dbswldnjs@naver.com");
		userDTO.setName("윤지원");
		userDTO.setPass(user.getPass());
		user = userService.save(userDTO);

		TeamDTO saveTeam = new TeamDTO();
		saveTeam.setName("자바 프로젝트");
		saveTeam.setDescription("aaaaa목표");

	}

	@Test
	@Memo("회원가입 테스트")
	public void test_1() throws Exception {
		UserDTO user = new UserDTO();
		user.setId("dlwodyd202");
		user.setPass("dlwodyd");
		user.setName("이재용");
		user.setEmail("wodyd202@naver.com");
		String json = objMapper.writeValueAsString(user);
		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.pass").doesNotExist())
			.andDo(document("Join User",
				requestFields(fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("pass").type(JsonFieldType.STRING).description("비밀번호"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("img").type(JsonFieldType.STRING).description("유저 이미지").optional()),
				responseFields(fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("img").type(JsonFieldType.STRING).description("유저 이미지").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("회원 탈퇴"),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("회원 수정"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("팀을 등록하는 테스트")
	public void test_2() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		TeamDTO team = new TeamDTO();
		team.setName("자바 프로젝트");
//		team.setStartDate("2020-04-14");
//		team.setEndDate("2020-10-10");
		team.setDescription("목표");
		String json = objMapper.writeValueAsString(team);
		Jackson2JsonParser jsonParser = new Jackson2JsonParser();

		ResultActions result = this.mvc
			.perform(post("/api/teamManage").header("Authorization", auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andExpect(status().isOk()).andDo(print());
		Map<String, Object> contentStringMap = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
		result.andDo(document("Create Team",
			requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").optional(),
				fieldWithPath("name").type(JsonFieldType.STRING).description("팀명"),
				fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작일"),
				fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
				fieldWithPath("endDate").type(JsonFieldType.STRING).description("최종일"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("목표")),
			responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").optional(),
				fieldWithPath("name").type(JsonFieldType.STRING).description("팀명"),
				fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
				fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작일"),
				fieldWithPath("code").type(JsonFieldType.STRING).description("팀 코드"),
				fieldWithPath("endDate").type(JsonFieldType.STRING).description("최종일"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("목표").optional(),
				fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("팀 탈퇴"),
				fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("팀 수정"),
				fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("팀에게 승인 요청을 보내는 경우")
	public void test_3() throws Exception {
		this.teamOfOtherUser();
		super.login("dlwodyd202", "dlwodyd");
		super.createTeam();
		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/api/teamManage/{code}/joinTeam", otherTeam.getCode())
				.header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("JoinTeamRequest", pathParameters(parameterWithName("code").description("팀 코드"))));
	}

	@Test
	@Memo("팀 승인 요청을 수락하는 경우")
	public void test_4() throws Exception {
		super.login("dbswldnjs202", "dbswldnjs");
		this.mvc
			.perform(RestDocumentationRequestBuilders.patch("/api/teamManage/{seq}/joinTeam", 6).header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("SignUpSuccess JoinTeam", pathParameters(parameterWithName("seq").description("요청 고유 번호")),
				responseFields(fieldWithPath("content").type(JsonFieldType.NUMBER).description("승인 요청 코드"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("팀 요청을 반려하는 경우")
	public void test_5() throws Exception {
		super.login("dbswldnjs202", "dbswldnjs");
		this.mvc
			.perform(RestDocumentationRequestBuilders
				.patch("/api/teamManage/{seq}/joinTeam/faild", 6).param("reson", "안됨").header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("SignUpFaild JoinTeam", pathParameters(parameterWithName("seq").description("반려 고유 번호")),
				responseFields(fieldWithPath("content").type(JsonFieldType.NUMBER).description("승인 반려 코드"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("자신이 소속되어있는 모든 팀 정보 가져오기")
	public void test_6() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc.perform(get("/api/teamManage").header("Authorization", auth)).andDo(print()).andExpect(status().isOk())
			.andDo(document("Get JoinTeamUnFinished",
				responseFields(
					fieldWithPath("_embedded.content").type(JsonFieldType.ARRAY).description(""),
					fieldWithPath("_embedded.content[].end_date").type(JsonFieldType.STRING).description("팀 마감일").optional(),
					fieldWithPath("_embedded.content[].code").type(JsonFieldType.STRING).description("팀 코드").optional(),
					fieldWithPath("_embedded.content[].flag").type(JsonFieldType.STRING).description("팀 상태").optional(),
					fieldWithPath("_embedded.content[].team_leader_seq").type(JsonFieldType.NUMBER).description("팀 리더 번호").optional(),
					fieldWithPath("_embedded.content[].name").type(JsonFieldType.STRING).description("팀 이름").optional(),
					fieldWithPath("_embedded.content[].description").type(JsonFieldType.STRING).description("팀 목표").optional(),
					fieldWithPath("_embedded.content[].progress").type(JsonFieldType.NUMBER).description("팀 진척도").optional(),
					fieldWithPath("_embedded.content[].seq").type(JsonFieldType.NUMBER).description("팀 고유 번호").optional(),
					fieldWithPath("_embedded.content[].start_date").type(JsonFieldType.STRING).description("팀 시작일").optional(),
					fieldWithPath("_embedded.pageable").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_embedded.totalElements").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.totalPages").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.number").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.size").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.numberOfElements").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.first").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.empty").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.last").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_links.rel").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("profile.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("profile.rel").type(JsonFieldType.STRING).description("")
				)));
	}
	
	@Test
	@Memo("자신이 소속되어있는 지난 모든 팀 정보 가져오기")
	public void test_7() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc.perform(get("/api/teamManage/finished").header("Authorization", auth)).andDo(print()).andExpect(status().isOk())
			.andDo(document("Get JoinTeamFinished",
				responseFields(
					fieldWithPath("_embedded.content").type(JsonFieldType.ARRAY).description(""),
					fieldWithPath("_embedded.content[].end_date").type(JsonFieldType.STRING).description("팀 마감일").optional(),
					fieldWithPath("_embedded.content[].code").type(JsonFieldType.STRING).description("팀 코드").optional(),
					fieldWithPath("_embedded.content[].flag").type(JsonFieldType.STRING).description("팀 상태").optional(),
					fieldWithPath("_embedded.content[].team_leader_seq").type(JsonFieldType.NUMBER).description("팀 리더 번호").optional(),
					fieldWithPath("_embedded.content[].name").type(JsonFieldType.STRING).description("팀 이름").optional(),
					fieldWithPath("_embedded.content[].description").type(JsonFieldType.STRING).description("팀 목표").optional(),
					fieldWithPath("_embedded.content[].progress").type(JsonFieldType.NUMBER).description("팀 진척도").optional(),
					fieldWithPath("_embedded.content[].seq").type(JsonFieldType.NUMBER).description("팀 고유 번호").optional(),
					fieldWithPath("_embedded.content[].start_date").type(JsonFieldType.STRING).description("팀 시작일").optional(),
					fieldWithPath("_embedded.pageable").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_embedded.totalElements").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.totalPages").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.number").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.size").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.numberOfElements").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("_embedded.first").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.empty").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_embedded.last").type(JsonFieldType.BOOLEAN).description(""),
					fieldWithPath("_links.rel").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("profile.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("profile.rel").type(JsonFieldType.STRING).description("")
				)));
	}
}
