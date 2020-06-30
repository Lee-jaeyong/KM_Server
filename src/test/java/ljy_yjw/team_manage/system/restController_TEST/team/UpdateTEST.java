package ljy_yjw.team_manage.system.restController_TEST.team;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.team.TeamDTO;
import ljy_yjw.team_manage.system.domain.dto.team.TeamInsertDTO;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.service.insert.team.TeamOneInsertService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateTEST extends CommonTestConfig {

	@Autowired
	TeamOneInsertService teamOneInsertService;

	@Test
	public void test_10() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("ㅇㅇ", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 5, 10), "목표 없음");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_11() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("스프링 프레임워크스프링 프레임워크스프링 프레임워크스프링 프레임워크스프링 프레임워크스프링 프레임워크스프링 프레임워크",
			LocalDate.of(2020, 6, 30), LocalDate.of(2020, 5, 10), "");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_12() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링 ㅇ나ㅓ로", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 5, 10), "");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_13() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 6, 30), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_14() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("312312412412", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 6, 30), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_15() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 3, 20), LocalDate.of(2020, 6, 27), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_16() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 10, 20), LocalDate.of(2020, 7, 27), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_17() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 7, 1), LocalDate.of(2020, 7, 27), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_18() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 6, 28), LocalDate.of(2020, 6, 28), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	public void test_19() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 12), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		successTest(action);
	}

	@Test
	public void test_20() throws Exception {
		super.login("wodyd2", "a");
		TeamInsertDTO team = createTeam("프레임워크스프링", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 6, 30), "한글한글");
		MockHttpServletRequestBuilder action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	@Memo("삭제한 팀을 다시 등록하는 테스트")
	public void test_21() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("스프링을 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "한글한글");
		String teamCode = createTeamThenGet(team);
		MockHttpServletRequestBuilder action = delete("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		successTest(action);
		action = post("/api/teamManage").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		successTest(action);
	}

	@Test
	@Memo("수정 테스트 성공")
	public void test_22() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("스프링 프레임워크를 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "한글한글");
		String teamCode = createTeamThenGet(team);
		team = createTeam("스프링 프레임워크를 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "수정 테스트");
		MockHttpServletRequestBuilder action = put("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		successTest(action);
	}

	@Test
	@Memo("수정 테스트 실패(권한 없음)")
	public void test_23() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("스프링 프레임워크를 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 6, 30), "한글한글");
		MockHttpServletRequestBuilder action = put("/api/teamManage/{code}", "VXCVXC").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		unAuthTest(action);
	}

	@Test
	@Memo("수정 테스트 실패(날짜 이상)")
	public void test_24() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("스프링 프레임워크를 배워봅시다잉쟁용", LocalDate.of(2020, 6, 1), LocalDate.of(2020, 12, 1), "한글한글");
		String teamCode = createTeamThenGet(team);
		team = createTeam("", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 6, 30), "한글한글");
		MockHttpServletRequestBuilder action = put("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	@Memo("수정 테스트 실패(날짜 이상)")
	public void test_25() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("스프링 프레임워크를 배워봅시다이재용", LocalDate.of(2020, 6, 20), LocalDate.of(2020, 7, 20), "한글한글");
		String teamCode = createTeamThenGet(team);
		team = createTeam("", LocalDate.of(2020, 6, 30), LocalDate.of(2020, 5, 20), "한글한글");
		MockHttpServletRequestBuilder action = put("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		badRequestTest(action);
	}

	@Test
	@Memo("수정 테스트 성공")
	public void test_26() throws Exception {
		super.login("wodyd2", "a");
		TeamDTO team = createTeam("다함께 프레임워크를 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "한글한글");
		String teamCode = createTeamThenGet(team);
		team = createTeam("", LocalDate.of(2020, 6, 1), LocalDate.of(2020, 7, 17), "수정 테스트");
		MockHttpServletRequestBuilder action = put("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, team);
		successTest(action);
	}

	@Test
	@Memo("팀을 마감하는 테스트")
	public void test_27() throws Exception {
		TeamDTO team = createTeam("팀 마감 테스트", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 30), "한글한글");
		String teamCode = createTeamThenGet(team);
		MockHttpServletRequestBuilder action = patch("/api/teamManage/finish/{code}", teamCode).header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("이미 마감된 팀(일자가 지나거나, 마감 체크 완료) 된 팀을 소생하는 테스트")
	public void test_28() throws Exception {
		super.login("wodyd2", "a");
		Users user = new Users();
		user.setSeq(1L);
		TeamDTO team = createTeam("팀 마감 소생 테스트", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 6, 28), "한글한글");
		Team saveTeam = team.parseThis2Team(user);
		saveTeam = teamOneInsertService.insertTeam(saveTeam);
		MockHttpServletRequestBuilder action = patch("/api/teamManage/unfinish/{code}", saveTeam.getCode()).header(AUTHRIZATION,
			auth);
		successTest(action);
	}

	@Test
	@Memo("이미 진행중인 팀을 소생시키는 테스트")
	public void test_29() throws Exception {
		super.login("wodyd2", "a");
		Users user = new Users();
		user.setSeq(1L);
		TeamDTO team = createTeam("팀 마감 소생 테스트 에러", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 12, 10), "한글한글");
		Team saveTeam = team.parseThis2Team(user);
		saveTeam = teamOneInsertService.insertTeam(saveTeam);
		MockHttpServletRequestBuilder action = patch("/api/teamManage/unfinish/{code}", saveTeam.getCode()).header(AUTHRIZATION,
			auth);
		badRequestTest(action);
	}
}
