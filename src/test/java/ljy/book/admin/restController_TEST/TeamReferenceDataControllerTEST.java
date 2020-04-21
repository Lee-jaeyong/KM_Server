package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;
import ljy.book.admin.professor.service.impl.TeamJoinRequestService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamReferenceDataControllerTEST extends CommonTestConfig {

	@Autowired
	TeamJoinRequestService teamJoinRequestService;

	@Test
	@Memo("참고자료를 등록하는 메소드")
	public void test_1() throws Exception {
		this.init();
		super.login("dlwodyd202", "dlwodyd");
		ReferenceDataDTO referenceData = ReferenceDataDTO.builder().title("자유 게시판").content("자유게시판 입니다.").build();
		String json = objMapper.writeValueAsString(referenceData);
		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/api/teamManage/referenceData/{code}", "C81E728D2")
				.header(super.AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("create referenceData",
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
	@Memo("참고자료를 수정하는 테스트")
	public void test_2() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		ReferenceDataDTO freeBoard = ReferenceDataDTO.builder().title("참고자료 입니당").content("참고자료 수정").build();
		String json = objMapper.writeValueAsString(freeBoard);
		this.mvc
			.perform(RestDocumentationRequestBuilders.put("/api/teamManage/referenceData/{seq}", 5)
				.header(super.AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("update referenceData",
				requestFields(fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호").ignored()),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("참고자료를 삭제하는 메소드")
	public void test_3() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(RestDocumentationRequestBuilders
				.delete("/api/teamManage/referenceData/{seq}", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("delete referenceData",
				responseFields(fieldWithPath("content").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("특정 팀의 참고자료를 모두 가져오는 테스트")
	public void test_4() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(RestDocumentationRequestBuilders.get("/api/teamManage/referenceData/{code}/all", "C81E728D2").param("page", "0")
				.param("size", "10").header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getList referenceData",
				responseFields(
					fieldWithPath("_embedded.referenceDataList[].seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("_embedded.referenceDataList[].title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("_embedded.referenceDataList[].content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_embedded.referenceDataList[].date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("_embedded.referenceDataList[].state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("상태"),
					fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("상태"),
					fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("상태"),
					fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("상태"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("특정 팀의 자유게시판 단건 조회")
	public void test_5() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(RestDocumentationRequestBuilders.get("/api/teamManage/referenceData/{seq}", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getOne referenceData",
				responseFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}
	
	public void init() throws Exception {
		Users user = super.createUser("dbswldnjs202", "dbswldnjs", "윤지원", "dbswldnjs202@naver.com");
		Team team = super.createTeam("자바 프로젝트 팀", "2020-04-01", "2020-10-10", "자바 프로젝트 완성", user);
		Users joinRequestUser = super.createUser("dlwodyd202", "dlwodyd", "이재용", "wodyd202@naver.com");
		JoinTeam request = teamJoinRequestService.saveJoinTeamReq(team, joinRequestUser);
		teamJoinRequestService.signUpSuccessJoinTeam(request.getSeq());
	}
}
