package ljy_yjw.team_manage.system.restController_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.NoticeDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamNoticeControllerTEST extends CommonTestConfig {

	@Test
	public void test_15() throws Exception {
		super.login("dlwodyd202", "a");
		this.mvc.perform(get("/api/teamManage/notice/{seq}/downloadFile/all", "533").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("공지사항을 등록하는 테스트")
	public void test_10() throws Exception {
		super.login("dlwodyd202", "a");
		NoticeDTO notice = NoticeDTO.builder().title("제목 테스트1").content("내용 테스트1").build();
		this.mvc
			.perform(post("/api/teamManage/notice/{code}", "ECCBC87E").header(AUTHRIZATION, auth)
				.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(notice)))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("공지사항을 수정하는 테스트")
	public void test_11() throws Exception {
		super.login("dlwodyd202", "a");
		NoticeDTO notice = NoticeDTO.builder().title("제목 테스트1 수정").content("내용 테스트1 수정").build();
		this.mvc.perform(put("/api/teamManage/notice/{seq}", "82").header(AUTHRIZATION, auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(notice))).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("공지사항을 삭제하는 테스트")
	public void test_12() throws Exception {
		super.login("dlwodyd202", "a");
		this.mvc.perform(delete("/api/teamManage/notice/{seq}", "82").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 팀의 모든 공지사항을 가져오는 테스트")
	public void test_13() throws Exception {
		super.login("dlwodyd202", "a");
		this.mvc.perform(get("/api/teamManage/notice/{code}/all", "ECCBC87E").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 번호의 공지사항을 가져오는 테스트")
	public void test_14() throws Exception {
		super.login("dlwodyd202", "a");
		this.mvc.perform(get("/api/teamManage/notice/{seq}", "83").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}
}
