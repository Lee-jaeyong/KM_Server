package ljy_yjw.team_manage.system.restController_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.PlanByUserDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamPlanControllerTEST extends CommonTestConfig {

	@Test
	@Ignore
	@Memo("개인별 일정의 개수를 가져오는 테스트")
	public void test_10() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/{code}/group-by-user", "C81E728D").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정을 등록하는 테스트")
	public void test_11() throws Exception {
		super.login("dlwodyd202", "a");
		PlanByUserDTO plan = PlanByUserDTO.builder().tag("태그").start(LocalDate.of(2020, 1, 1))
			.end(LocalDate.of(2020, 10, 10)).build();
		mvc.perform(post("/api/teamManage/plan/{code}", "ECCBC87E").header(AUTHRIZATION, auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(plan))).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정을 수정하는 테스트")
	public void test_12() throws Exception {
		super.login("dlwodyd202", "a");
		PlanByUserDTO plan = PlanByUserDTO.builder().tag("수정 테스트").start(LocalDate.of(2020, 1, 1))
			.end(LocalDate.of(2020, 10, 10)).build();
		mvc.perform(put("/api/teamManage/plan/{seq}", "8").header(AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(plan))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정의 진척도만 수정하는 테스트")
	public void test_13() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(put("/api/teamManage/plan/{seq}", "8").param("progress", "100").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정을 삭제하는 테스트")
	public void test_14() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(delete("/api/teamManage/plan/{seq}", "8").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Memo("해당 팀의 일정을 가져오는 테스트")
	public void test_15() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(
			get("/api/teamManage/plan/{code}/all", "C81E728D").param("size", "10").param("page", "0").header(AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 팀의 일정을 검색하는 테스트")
	public void test_16() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/{code}/search", "ECCBC87E").param("date", LocalDate.of(2020, 6, 20).toString())
			.param("size", "10").param("page", "0").header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 팀의 일정을 검색하는 테스트(특정 달)")
	public void test_17() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/{code}/search/all", "ECCBC87E").param("date", "2020-05-10").param("size", "10")
			.param("page", "0").header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 팀의 마감된 일정을 가져오는 테스트")
	public void test_18() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/{code}/search/finished", "ECCBC87E").param("size", "10").param("page", "0")
			.header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정 단건 가져오는 메소드")
	public void test_19() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/{seq}", "6").header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 사용자의 모든 일정 가져오기")
	public void test_20() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/all/unfinished").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("해당 사용자의 모든 일정 가져오기(특정 팀의)")
	public void test_21() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/all/unfinished").param("code", "ECCBC87E").header(AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("자신의 끝날 일정을 모두 가져오는 테스트(특정 팀의)")
	public void test_22() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/plan/all/finished").param("size", "10").param("page", "0").param("code", "ECCBC87E")
			.header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}
}
