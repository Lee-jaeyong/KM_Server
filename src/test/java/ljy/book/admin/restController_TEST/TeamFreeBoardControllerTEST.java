package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.service.impl.TeamJoinRequestService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamFreeBoardControllerTEST extends CommonTestConfig {

	@Autowired
	TeamJoinRequestService teamJoinRequestService;

	@Test
	@Memo("자유 게시판을 등록하는 테스트")
	public void test_1() throws Exception {
		this.init();
		super.login("dlwodyd202", "dlwodyd");
		FreeBoardDTO freeBoard = FreeBoardDTO.builder().title("자유 게시판").content("자유게시판 입니다.").build();
		String json = objMapper.writeValueAsString(freeBoard);
		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/api/teamManage/freeBoard/{code}", "C81E728D2")
				.header(super.AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("create freeBoard",
				requestFields(fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").ignored()),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("자유 게시판을 수정하는 테스트")
	public void test_2() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		FreeBoardDTO freeBoard = FreeBoardDTO.builder().title("자유 게시판aaa").content("자유게시판 입니다aaaaa.").build();
		String json = objMapper.writeValueAsString(freeBoard);
		this.mvc
			.perform(RestDocumentationRequestBuilders.put("/api/teamManage/freeBoard/{seq}", 5).header(super.AUTHRIZATION, auth)
				.contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("update freeBoard",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").ignored()),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("자유 게시판을 삭제하는 테스트")
	public void test_3() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(
				RestDocumentationRequestBuilders.delete("/api/teamManage/freeBoard/{seq}", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("delete freeBoard",
				responseFields(
					fieldWithPath("content").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("")
			)));
	}

	public void init() throws Exception {
		Users user = super.createUser("dbswldnjs202", "dbswldnjs", "윤지원", "dbswldnjs202@naver.com");
		Team team = super.createTeam("자바 프로젝트 팀", "2020-04-01", "2020-10-10", "자바 프로젝트 완성", user);
		Users joinRequestUser = super.createUser("dlwodyd202", "dlwodyd", "이재용", "wodyd202@naver.com");
		JoinTeam request = teamJoinRequestService.saveJoinTeamReq(team, joinRequestUser);
		teamJoinRequestService.signUpSuccessJoinTeam(request.getSeq());
	}
}
