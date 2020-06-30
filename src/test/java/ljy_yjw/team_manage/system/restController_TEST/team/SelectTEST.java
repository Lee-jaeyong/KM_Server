package ljy_yjw.team_manage.system.restController_TEST.team;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.TeamDAO;
import ljy_yjw.team_manage.system.domain.dto.team.TeamDTO;
import ljy_yjw.team_manage.system.domain.entity.JoinTeam;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import lombok.var;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectTEST extends CommonTestConfig {

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	TeamDAO teamDAO;

	@Before
	public void init() throws Exception {
		super.login("wodyd2", "a");
	}

	@Test
	@Memo("존재하지 않은 팀에게 요청을 보내는 테스트")
	public void test_06() throws Exception {
		var action = post("/api/teamManage/{code}/joinTeam", "없음").header(AUTHRIZATION, auth);
		badRequestTest(action);
	}

	@Test
	@Memo("이미 끝난 마감된 팀에게 요청을 보내는 테스트")
	public void test_07() throws Exception {
		Users user = saveUser("wodyd101", ".d!d1489314", "이재용", "wodyd202@naver.com");
		Team team = saveTeam("스프링 프레임워크 가", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 6, 27), "한글한글", user);
		var action = post("/api/teamManage/{code}/joinTeam", team.getCode()).header(AUTHRIZATION, auth);
		badRequestTest(action);
	}

	@Test
	@Memo("팀 승인 요청을 보내는 테스트")
	public void test_08() throws Exception {
		Users user = saveUser("wodyd101", ".d!d1489314", "이재용", "wodyd202@naver.com");
		Team team = saveTeam("스프링 프레임워크 나", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		var action = post("/api/teamManage/{code}/joinTeam", team.getCode()).header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("자신이 팀장인 팀에게 팀의 승인요청을 보내는 테스트")
	public void test_09() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 프레임워크 다", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		var action = post("/api/teamManage/{code}/joinTeam", team.getCode()).header(AUTHRIZATION, auth);
		badRequestTest(action);
	}

	@Test
	@Memo("해당 코드의 팀의 정보를 가져오는 테스트")
	public void test_10() throws Exception {
		TeamDTO team = createTeam("다함께 프레임워크를 배워봅시다", LocalDate.of(2020, 6, 26), LocalDate.of(2020, 6, 30), "한글한글");
		String teamCode = createTeamThenGet(team);
		MockHttpServletRequestBuilder action = get("/api/teamManage/{code}", teamCode).header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("자신이 팀장인 팀 리스트(진행중인)를 모두 가져오는 테스트")
	public void test_11() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		saveTeam("스프링 가", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		saveTeam("스프링 나", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		saveTeam("스프링 다", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		saveTeam("스프링 라", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		saveTeam("스프링 마", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 9, 20), "한글한글", user);
		saveTeam("스프링 바", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 7, 20), "한글한글", user);
		saveTeam("스프링 사", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 4, 20), "한글한글", user);
		saveTeam("스프링 아", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 5, 30), "한글한글", user);
		saveTeam("스프링 자", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 5, 20), "한글한글", user);
		var action = get("/api/teamManage/unFinished-mylist").header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("팀 승인을 승낙하는 테스트")
	public void test_11_1() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 12, 20), "한글한글", user);
		user.setSeq(2L);
		JoinTeam joinTeam = saveJoinTeam(team, user);
		var action = put("/api/teamManage/{seq}/joinTeam/success", joinTeam.getSeq()).header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("이미 마감된 팀 승인을 승낙하는 테스트")
	public void test_11_2() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 5, 20), "한글한글", user);
		user.setSeq(2L);
		JoinTeam joinTeam = saveJoinTeam(team, user);
		var action = put("/api/teamManage/{seq}/joinTeam/success", joinTeam.getSeq()).header(AUTHRIZATION, auth);
		badRequestTest(action);
	}

	@Test
	@Memo("자신이 소속된 팀 리스트를 모두 가져오는 테스트")
	public void test_12() throws Exception {
		Users user = new Users();
		user.setSeq(2L);
		Team team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 5, 20), "한글한글", user);
		user.setSeq(1L);
		JoinTeam joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		user.setSeq(2L);
		team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 10, 20), "한글한글", user);
		user.setSeq(1L);
		joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		user.setSeq(2L);
		team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 11, 20), "한글한글", user);
		user.setSeq(1L);
		joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		var action = get("/api/teamManage").header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("자신이 소속된 마감된 팀 리스트를 모두 가져오는 테스트")
	public void test_13() throws Exception {
		var action = get("/api/teamManage").param("flag", "finished").header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("오늘이 마감일인 팀 리스트 모두 가져오는 테스트")
	public void test_13_1() throws Exception {
		Users user = new Users();
		user.setSeq(2L);
		Team team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 6, 29), "한글한글", user);
		user.setSeq(1L);
		JoinTeam joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		user.setSeq(2L);
		team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 6, 29), "한글한글", user);
		user.setSeq(1L);
		joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		user.setSeq(2L);
		team = saveTeam("스프링 차", LocalDate.of(2020, 1, 26), LocalDate.of(2020, 6, 29), "한글한글", user);
		user.setSeq(1L);
		joinTeam = saveJoinTeam(team, user);
		teamDAO.signUpSuccess(joinTeam.getSeq());

		var action = get("/api/teamManage").param("flag", "son").header(AUTHRIZATION, auth);
		successTest(action);
	}
}
