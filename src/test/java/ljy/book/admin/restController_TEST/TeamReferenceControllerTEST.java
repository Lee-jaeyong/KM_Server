package ljy.book.admin.restController_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.Rollback;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.NoticeDTO;
import ljy.book.admin.professor.service.impl.TeamJoinRequestService;
import ljy.book.admin.professor.service.impl.TeamService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamReferenceControllerTEST extends CommonTestConfig {

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
	@Memo("팀장이 팀의 공지사항을 등록하는 메소드")
	public void test_1() throws Exception {
		this.init();
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
	@Memo("해당 팀의 공지사항을 모두 가져오는 메소드")
	public void test_4() throws Exception {
		this.login("dbswldnjs202", "dbswldnjs");
		this.mvc
			.perform(
				get("/api/teamManage/notice/{code}", "C81E728D2").param("page", "0").param("size", "10").header("Authorization", auth))
			.andDo(print()).andExpect(status().isOk());
	}
}
