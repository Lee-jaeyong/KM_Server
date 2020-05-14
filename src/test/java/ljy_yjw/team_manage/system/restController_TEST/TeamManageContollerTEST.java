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
import ljy_yjw.team_manage.system.domain.dto.TeamDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamManageContollerTEST extends CommonTestConfig {

	@Test
	@Ignore
	@Memo("팀을 등록하는 테스트")
	public void test_10() throws Exception {
		createUser("dlwodyd202", "a", "이재용", "wodyd202@naver.com");
		createUser("dbswldnjs", "a", "윤지원", "wodyd202@naver.com");
		super.login("dlwodyd202", "a");
		TeamDTO team = TeamDTO.builder().name("<script>alert('xss attack!!')</script>").description("").startDate(LocalDate.of(2020, 2, 11))
			.endDate(LocalDate.of(2020, 12, 12)).build();
		mvc.perform(post("/api/teamManage").header(AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(team))).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("팀의 정보를 가져오는 테스트")
	public void test_11() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/{code}", "ECCBC87E").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("팀의 승인신청 리스트를 가져오는 테스트")
	public void test_12() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/{code}/signUpList", "ECCBC87E").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("기간이 만료된 팀 리스트 가져오는 테스트")
	public void test_13() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage").param("flag", "finished").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("기간이 만료되지 않은 팀 리스트 가져오는 테스트")
	public void test_14() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage").header(AUTHRIZATION, auth)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("팀을 수정하는 테스트")
	public void test_16() throws Exception {
		super.login("dlwodyd202", "a");
		TeamDTO team = TeamDTO.builder().name("테스트1").description("업데이트 테스트").startDate(LocalDate.of(2020, 2, 11))
			.endDate(LocalDate.of(2020, 12, 12)).build();
		mvc.perform(put("/api/teamManage/{code}", "ECCBC87E").header(AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(team))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("팀을 삭제하는 테스트")
	public void test_17() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(delete("/api/teamManage/{code}", "ECCBC87E").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Memo("팀의 진척도를 변경하는 테스트")
	public void test_18() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(put("/api/teamManage/{code}", "ECCBC87E").param("progress", "100").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("팀에게 승인요청을 보내는 테스트")
	public void test_19() throws Exception {
		super.login("dbswldnjs", "a");
		mvc.perform(post("/api/teamManage/{code}/joinTeam", "ECCBC87E").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("승인 신청을 승낙하는 테스트")
	public void test_20() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(put("/api/teamManage/{seq}/joinTeam/success", "4").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("승인 신청을 거절하는 테스트")
	public void test_21() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(put("/api/teamManage/{seq}/joinTeam/faild", "4").param("reson", "그냥").header(AUTHRIZATION, auth))
			.andDo(print()).andExpect(status().isOk());
	}
}
