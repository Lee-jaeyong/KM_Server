package ljy_yjw.team_manage.system.restController_TEST.doc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.dto.plan.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.dto.team.TeamDTO;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRestDocs extends CommonTestConfig {

	@Test
	@Ignore
	@Memo("로그인")
	public void test_10() throws Exception {
		mvc.perform(RestDocumentationRequestBuilders.post("/oauth/token").with(httpBasic(clientId, clientPass))
			.param("username", "dlwodyd202").param("password", ".d!d1489314").param("grant_type", "password"))
			.andDo(document("login",
				requestParameters(parameterWithName("username").description("아이디"),
					parameterWithName("password").description("비밀번호"), parameterWithName("grant_type").description("인증타입")),
				responseFields(fieldWithPath("access_token").type(JsonFieldType.STRING).description("발급 토큰"),
					fieldWithPath("token_type").type(JsonFieldType.STRING).description("토큰 타입"),
					fieldWithPath("refresh_token").type(JsonFieldType.STRING).description(""),
					fieldWithPath("expires_in").type(JsonFieldType.NUMBER).description("토큰 만료 시간"),
					fieldWithPath("scope").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("회원가입")
	public void test_11() throws Exception {
		UserDTO user = this.createUser("wodyd33310", "asdfghjklqwert", "이재용", "wodyd202@naver.com");
		mvc.perform(RestDocumentationRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(user)))
			.andDo(document("create user",
				requestFields(fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
					fieldWithPath("pass").type(JsonFieldType.STRING).description("비밀번호"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("img").type(JsonFieldType.STRING).description("").optional()),
				responseFields(fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("팀을 생성")
	public void test_12() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		TeamDTO team = createTeam("스프링 프레임워크를 배워봅시다람쥐입니당람쥥", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "한글한글");
		mvc.perform(RestDocumentationRequestBuilders.post("/api/teamManage").header(AUTHRIZATION, auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(team)))
			.andDo(document("create team",
				requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("팀 명"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("팀 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("팀 마감일"),
					fieldWithPath("description").type(JsonFieldType.STRING).description("팀 목표")),
				responseFields(fieldWithPath("code").type(JsonFieldType.STRING).description("팀 코드"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("팀 명"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("팀 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("팀 마감일"),
					fieldWithPath("description").type(JsonFieldType.STRING).description("팀 목표"),
					fieldWithPath("flag").type(JsonFieldType.STRING).description("팀 상태"),
					fieldWithPath("teamLeader.id").type(JsonFieldType.STRING).description("팀 리더 아이디"),
					fieldWithPath("teamLeader.name").type(JsonFieldType.STRING).description("팀 리더 이름"),
					fieldWithPath("teamLeader.email").type(JsonFieldType.STRING).description("팀 리더 이메일"),
					fieldWithPath("teamLeader.img").type(JsonFieldType.STRING).description("팀 리더 이미지"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("팀 승인 신청")
	public void test_13() throws Exception {
		super.login("wodyd33310", "asdfghjklqwert");
		mvc.perform(
			RestDocumentationRequestBuilders.post("/api/teamManage/{code}/joinTeam", "C9F0F895").header(AUTHRIZATION, auth))
			.andDo(document("join team", pathParameters(parameterWithName("code").description("팀 코드"))));
	}

	@Test
	@Ignore
	@Memo("특정 팀 승인신청 명단 가져오기")
	public void test_14() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		mvc.perform(
			RestDocumentationRequestBuilders.get("/api/teamManage/{code}/signUpList", "C81E728D").header(AUTHRIZATION, auth))
			.andDo(document("get signUp list", pathParameters(parameterWithName("code").description("팀 코드")),
				responseFields(fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고"))));
	}

	@Test
	@Memo("해당 팀 정보 가져오기")
	public void test_15() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		mvc.perform(RestDocumentationRequestBuilders.get("/api/teamManage/{code}", "C81E728D").header(AUTHRIZATION, auth))
			.andDo(document("team Info", pathParameters(parameterWithName("code").description("팀 코드"))));
	}

	@Test
	@Memo("자신이 소속된 팀 정보 가져오기")
	public void test_16() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		mvc.perform(RestDocumentationRequestBuilders.get("/api/teamManage").header(AUTHRIZATION, auth))
			.andDo(document("team List Info"));
	}

	@Test
	@Memo("일정 등록")
	public void test_17() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 6, 20));
		planByUser.setEnd(LocalDate.of(2020, 12, 10));
		planByUser.setTag("성공 테스트");
		planByUser.setTeamPlan(BooleanState.NO);
		mvc.perform(RestDocumentationRequestBuilders.post("/api/teamManage/plan/{code}", "C81E728D").header(AUTHRIZATION, auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(planByUser)))
			.andDo(document("create plan", pathParameters(parameterWithName("code").description("팀 코드")),
				requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("start").type(JsonFieldType.STRING).description("일정 시작일"),
					fieldWithPath("end").type(JsonFieldType.STRING).description("일정 마감일"),
					fieldWithPath("tag").type(JsonFieldType.STRING).description("일정 타이틀"),
					fieldWithPath("teamPlan").type(JsonFieldType.STRING).description("팀 일정 여부"))));
	}

	@Test
	@Memo("해당 팀의 일정 가져오기")
	public void test_18() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		mvc.perform(
			RestDocumentationRequestBuilders.get("/api/teamManage/plan/{code}/all", "C81E728D")
			.param("page", "0")
			.param("size", "2")
			.param("start", "2020-01-01")
			.param("end", "2020-12-20")
			.param("title", "")
			.param("todo", "")
			.header(AUTHRIZATION, auth))
			.andDo(document("plan List",
				requestParameters(
					parameterWithName("page").description(""),
					parameterWithName("size").description(""),
					parameterWithName("start").description("시작일"),
					parameterWithName("end").description("마감일"), 
					parameterWithName("title").description("일정 타이틀 검색"),
					parameterWithName("todo").description("단건일정 검색")
				),
				pathParameters(parameterWithName("code").description("팀 코드"))));
	}

	@Test
	@Memo("해당 일정 가져오기")
	public void test_19() throws Exception {
		super.login("dlwodyd202", ".d!d1489314");
		mvc.perform(RestDocumentationRequestBuilders.get("/api/teamManage/plan/{seq}","3")).andDo(
			document("plan Info", 
				pathParameters(parameterWithName("seq").description("일정 번호"))
			)
		);
	}
}
