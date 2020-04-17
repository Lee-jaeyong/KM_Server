package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.requestDTO.UserDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamPlanControllerTEST extends CommonTestConfig {

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
		team.setStartDate("2020-04-14");
		team.setEndDate("2020-10-10");
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
	@Memo("일정을 등록하는 테스트")
	public void test_3() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		PlanByUserDTO planByUser = PlanByUserDTO.builder().tag("코딩 관련").content("뭐 해야됨").start("2020-04-17").end("2020-04-18")
			.build();
		this.mvc.perform(post("/api/teamManage/plan/{seq}", 6).header("Authorization", auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(planByUser))).andDo(print())
			.andExpect(status().isOk());
	}
}
