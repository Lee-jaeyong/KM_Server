package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.FileType;
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
	@Memo("자유 게시판을 삭제하는 테스트")
	public void test_3() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(
				RestDocumentationRequestBuilders.delete("/api/teamManage/freeBoard/{seq}", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("delete freeBoard",
				responseFields(fieldWithPath("content").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("특정 팀의 자유게시판을 모두 가져오는 테스트")
	public void test_4() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(RestDocumentationRequestBuilders.get("/api/teamManage/freeBoard/{code}/all", "C81E728D2").param("page", "0")
				.param("size", "10").header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getList freeBoard",
				responseFields(fieldWithPath("_embedded.freeBoardList[].seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("_embedded.freeBoardList[].title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("_embedded.freeBoardList[].content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_embedded.freeBoardList[].date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("_embedded.freeBoardList[].state").type(JsonFieldType.STRING).description("상태"),
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
			.perform(RestDocumentationRequestBuilders.get("/api/teamManage/freeBoard/{seq}", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getOne freeBoard",
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유번호"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Memo("자유게시판 파일 업로드")
	public void test_6() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		MockMultipartFile file = new MockMultipartFile("files", "test.txt", "text/plain", "hello file".getBytes());
		this.mvc.perform(multipart("/api/teamManage/freeBoard/{seq}/fileUpload/{type}", 5, FileType.IMG).file(file)
			.header(super.AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Memo("자유게시판 파일 삭제")
	public void test_7() throws Exception {
		super.login("dlwodyd202", "dlwodyd");
		this.mvc
			.perform(post("/api/teamManage/freeBoard/{seq}/fileUpload/54de5386-5b14-4d9a-a438-8ecdce0a7214_test.txt/delete", 5)
				.header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Memo("파일을 가져오는 테스트")
	public void test_8() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc.perform(get("/api/teamManage/freeBoard/{seq}/downloadFile/54de5386-5b14-4d9a-a438-8ecdce0a7214_test.txt", 5)
			.header(super.AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	public void init() throws Exception {
		Users user = super.createUser("dbswldnjs202", "dbswldnjs", "윤지원", "dbswldnjs202@naver.com");
		Team team = super.createTeam("자바 프로젝트 팀", "2020-04-01", "2020-10-10", "자바 프로젝트 완성", user);
		Users joinRequestUser = super.createUser("dlwodyd202", "dlwodyd", "이재용", "wodyd202@naver.com");
		JoinTeam request = teamJoinRequestService.saveJoinTeamReq(team, joinRequestUser);
		teamJoinRequestService.signUpSuccessJoinTeam(request.getSeq());
	}

}
