package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.professor.requestDTO.UserDTO;
import net.bytebuddy.implementation.bind.annotation.Super;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamManageContollerTEST extends CommonTestConfig {

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
		super.login("dlwodyd202","dlwodyd");
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
				fieldWithPath("code").type(JsonFieldType.STRING).description("팀 코드"),
				fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
				fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작일"),
				fieldWithPath("endDate").type(JsonFieldType.STRING).description("최종일"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("목표").optional(),
				fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("팀 탈퇴"),
				fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("팀 수정"),
				fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("팀을 수정하는 메소드")
	public void test_3() throws Exception {
		super.login("dlwodyd202","dlwodyd");
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
		long teamCode = Long.parseLong(contentStringMap.get("seq").toString());

		// When
		TeamDTO team = new TeamDTO();
		team.setSeq(teamCode);
		team.setName("자바 프로젝트");
		team.setStartDate("2020-04-14");
		team.setEndDate("2020-10-10");
		team.setDescription("목표");
		json = objMapper.writeValueAsString(team);

		// Then
		this.mvc
			.perform(RestDocumentationRequestBuilders.put("/api/teamManage/{seq}", teamCode).header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Update Team", pathParameters(parameterWithName("seq").description("팀 번호")),
				requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("팀번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("팀명"),
					fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("최종일"),
					fieldWithPath("description").type(JsonFieldType.STRING).description("목표")),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("팀명"),
					fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("최종일"),
					fieldWithPath("description").type(JsonFieldType.STRING).description("목표").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("팀 수정"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("팀을 삭제하는 테스트")
	public void test_4() throws Exception {
		super.login("dlwodyd202","dlwodyd");
		super.createTeam();
		this.mvc
			.perform(
				RestDocumentationRequestBuilders.delete("/api/teamManage/{seq}", super.teamSeq).header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Delete Team", pathParameters(parameterWithName("seq").description("팀 번호")),
				responseFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("팀 번호"),
					fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.insert.href").type(JsonFieldType.STRING).description("팀 추가")
				)));
	}

	@Test
	@Memo("팀의 진척도를 변경하는 테스트")
	public void test_5() throws Exception {
		super.login("dlwodyd202","dlwodyd");
		super.createTeam();
		TeamDTO team = new TeamDTO();
		team.setProgress((byte) 100);
		String json = objMapper.writeValueAsString(team);
		this.mvc
			.perform(RestDocumentationRequestBuilders.patch("/api/teamManage/{seq}", super.teamSeq).header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Update Progress of Team",
				pathParameters(
					parameterWithName("seq").description("팀 번호")
				),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("팀 번호"),
					fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도")
				),
				responseFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("팀 번호"),
					fieldWithPath("progress").type(JsonFieldType.NUMBER).description("전체 진척도"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("")
				)));
	}
}
