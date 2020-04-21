package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import ljy.book.admin.professor.requestDTO.NoticeDTO;
import ljy.book.admin.professor.service.impl.TeamJoinRequestService;
import ljy.book.admin.professor.service.impl.TeamService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamNoticeControllerTEST extends CommonTestConfig {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamJoinRequestService teamJoinRequestService;

	public void init() throws Exception {
		Users user = super.createUser("dbswldnjs202", "dbswldnjs", "윤지원", "dbswldnjs202@naver.com");
		Team team = super.createTeam("자바 프로젝트 팀", "2020-04-01", "2020-10-10", "자바 프로젝트 완성", user);
		Users joinRequestUser = super.createUser("dlwodyd202", "dlwodyd", "이재용", "wodyd202@naver.com");
		JoinTeam request = teamJoinRequestService.saveJoinTeamReq(team, joinRequestUser);
		teamJoinRequestService.signUpSuccessJoinTeam(request.getSeq());
	}

	@Test
	@Ignore
	@Memo("팀장이 팀의 공지사항을 등록하는 메소드")
	public void test_1() throws Exception {
		//this.init();
		this.login("dbswldnjs202", "dbswldnjs");
		NoticeDTO noticeDTO = NoticeDTO.builder().title("공지사항 입니다.").content("공지사항은 ...").build();
		String json = objMapper.writeValueAsString(noticeDTO);
		this.mvc
			.perform(post("/api/teamManage/notice/{code}", "C81E728D2")
				.header("Authorization", auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("create notice",
				requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유 번호").ignored(),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용")),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유 번호").ignored(),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("팀장이 팀의 공지사항을 수정하는 테스트")
	public void test_2() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		NoticeDTO noticeDTO = NoticeDTO.builder().title("공지사항 입니다.(수정)").content("공지사항은 ...(수정)").build();
		String json = objMapper.writeValueAsString(noticeDTO);
		this.mvc
			.perform(put("/api/teamManage/notice/{seq}", "5")
				.header("Authorization", auth).contentType(MediaType.APPLICATION_JSON).content(json))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("update notice",
				requestFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유 번호").ignored(),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용")),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("고유 번호").ignored(),
					fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("공지사항을 삭제하는 테스트")
	public void test_3() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc.perform(delete("/api/teamManage/notice/{seq}", "5").header("Authorization", auth)).andDo(print())
			.andExpect(status().isOk())
			.andDo(document("delete notice",
				responseFields(fieldWithPath("content").type(JsonFieldType.NUMBER).description("고유 번호").ignored(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("해당 팀의 공지사항을 모두 가져오는 메소드")
	public void test_4() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc
			.perform(RestDocumentationRequestBuilders.get("/api/teamManage/notice/{code}/all", "C81E728D2").param("page", "0")
				.param("size", "10").header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getAll notice", pathParameters(parameterWithName("code").description("팀 코드")),
				responseFields(
					fieldWithPath("_embedded.noticeList[].seq").type(JsonFieldType.NUMBER).description("고유번호").optional(),
					fieldWithPath("_embedded.noticeList[].title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("_embedded.noticeList[].content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("_embedded.noticeList[].date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("_embedded.noticeList[].state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("_embedded.noticeList[].noticeFileAndImg").type(JsonFieldType.ARRAY).description(""),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("page.size").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description(""),
					fieldWithPath("page.number").type(JsonFieldType.NUMBER).description(""))));
	}

	@Test
	@Memo("해당 공지사항을 가져오는 메소드")
	public void test_5() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/teamManage/notice/{seq}", 5).header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("getOne notice",
				responseFields(
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("고유번호").optional(),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
					fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
					fieldWithPath("data.date").type(JsonFieldType.STRING).description("등록일"),
					fieldWithPath("data.state").type(JsonFieldType.STRING).description("상태"),
					fieldWithPath("data.noticeFileAndImg").type(JsonFieldType.ARRAY).description(""),
					fieldWithPath("image").type(JsonFieldType.ARRAY).description(""),
					fieldWithPath("data.links[].href").type(JsonFieldType.STRING).description(""),
					fieldWithPath("data.links[].rel").type(JsonFieldType.STRING).description(""),
					fieldWithPath("data.links[].rel").type(JsonFieldType.STRING).description(""),
					fieldWithPath("data.links[].href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("파일 업로드 테스트")
	public void test_6() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		MockMultipartFile file = new MockMultipartFile("files", "test.txt", "text/plain", "hello file".getBytes());
		MockMultipartFile file1 = new MockMultipartFile("files", "test.txt", "text/plain", "hello file".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "test.txt", "text/plain", "hello file".getBytes());
		this.mvc.perform(multipart("/api/teamManage/notice/{seq}/fileUpload/{type}", 5, FileType.FILE).file(file).file(file2)
			.file(file1).header(super.AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("파일 삭제 테스트")
	public void test_7() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc.perform(post("/api/teamManage/notice/{seq}/fileUpload/test.txt/delete", 5).header(super.AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("파일을 가져오는 테스트")
	public void test_8() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc.perform(get("/api/teamManage/notice/{seq}/downloadFile/6d82ac51-9238-44f7-9c7a-eceb0d9ebc88_test.txt", 5)
			.header(super.AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}
}
