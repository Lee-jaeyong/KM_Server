package ljy_yjw.team_manage.system.restController_TEST.plan;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.TodoListDTO;
import ljy_yjw.team_manage.system.domain.dto.plan.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.var;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamPlanControllerTEST extends CommonTestConfig {

	@Before
	public void init() throws Exception {
		super.login("wodyd2", "a");
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_1[유효성 에러]")
	public void test_01() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 가", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setEnd(LocalDate.of(2020, 06, 20));
		planByUser.setStart(LocalDate.of(2020, 06, 04));
		planByUser.setTag("falidTest");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_2[날짜 시작일 프로젝트 전]")
	public void test_02() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 나", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 5, 20));
		planByUser.setEnd(LocalDate.of(2020, 06, 04));
		planByUser.setTag("실패 테스트");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_3[유효성 에러]")
	public void test_03() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 라", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 5, 20));
		planByUser.setEnd(LocalDate.of(2020, 06, 04));
		planByUser.setTag("실패 테스트2");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_4[유효성 에러]")
	public void test_04() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 라", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 5, 20));
		planByUser.setEnd(LocalDate.of(2020, 06, 04));
		planByUser.setTag("실");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_5[마감된 팀]")
	public void test_05() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 마", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 12, 20));
		planByUser.setEnd(LocalDate.of(2020, 12, 21));
		planByUser.setTag("실");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_성공")
	public void test_06() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 바", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 6, 20));
		planByUser.setEnd(LocalDate.of(2020, 12, 10));
		planByUser.setTag("성공 테스트");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		successTest(action);
	}

	@Test
	@Memo("일정을 등록하는 테스트_실패_5[잘못된 일정 입력]")
	public void test_07() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 마", LocalDate.of(2020, 06, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUserDTO planByUser = new PlanByUserDTO();
		planByUser.setStart(LocalDate.of(2020, 9, 20));
		planByUser.setEnd(LocalDate.of(2020, 8, 21));
		planByUser.setTag("실");
		planByUser.setTeamPlan(BooleanState.NO);
		var action = post("/api/teamManage/plan/{code}", team.getCode()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, planByUser);
		badRequestTest(action);
	}

	@Test
	@Memo("해당 팀의 몇월에 해야하는 일정 가져오는 테스트")
	public void test_08() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 바", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		for (int month = 1; month < 7; month++) {
			for (int date = 1; date < 20; date++) {
				savePlan(LocalDate.of(2020, month, date), LocalDate.of(2020, 13 - month, date),
					"태그 입니다." + month + " /// " + date, team, user);
			}
		}
		var action = get("/api/teamManage/plan/{code}/search/all", team.getCode()).param("start", "2020-01-01")
			.param("end", "2020-03-03").param("page", "0").param("size", "10").header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("단건 일정을 등록하는 테스트 실패 [길이]")
	public void test_09() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 사", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUser plan = savePlan(LocalDate.of(2020, 03, 03), LocalDate.of(2020, 04, 20), "단건 일정", team, user);
		TodoListDTO todo = new TodoListDTO();
		todo.setIng(BooleanState.YES);
		todo.setTitle("");
		var action = post("/api/teamManage/todoList/{seq}", plan.getSeq()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, todo);
		badRequestTest(action);
	}

	@Test
	@Memo("단건 일정을 등록하는 테스트 실패 [길이]")
	public void test_10() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 아", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUser plan = savePlan(LocalDate.of(2020, 03, 03), LocalDate.of(2020, 04, 20), "단건 일정", team, user);
		TodoListDTO todo = new TodoListDTO();
		todo.setIng(BooleanState.YES);
		todo.setTitle(null);
		var action = post("/api/teamManage/todoList/{seq}", plan.getSeq()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, todo);
		badRequestTest(action);
	}

	@Test
	@Memo("단건 일정 등록하는 테스트 성공")
	public void test_11() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 아", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUser plan = savePlan(LocalDate.of(2020, 03, 03), LocalDate.of(2020, 04, 20), "단건 일정", team, user);
		TodoListDTO todo = new TodoListDTO();
		todo.setIng(BooleanState.YES);
		todo.setTitle("<>라어노러낭ㄹㄹㄹㄹ");
		var action = post("/api/teamManage/todoList/{seq}", plan.getSeq()).header(AUTHRIZATION, auth);
		getMockHttpServletRequest(action, todo);
		successTest(action);
	}

	@Test
	@Memo("단건 일정 등록 후 해당 일정을 보는 테스트")
	public void test_12() throws Exception {
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 아", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		PlanByUser plan = savePlan(LocalDate.of(2020, 03, 03), LocalDate.of(2020, 04, 20), "단건 일정", team, user);
		saveTodo("투두 리스트 테스트1", plan, user);
		saveTodo("투두 리스트 테스트2", plan, user);
		saveTodo("투두 리스트 테스트3", plan, user);
		saveTodo("투두 리스트 테스트4", plan, user);
		saveTodo("투두 리스트 테스트5", plan, user);
		var action = get("/api/teamManage/plan/{seq}", plan.getSeq()).header(AUTHRIZATION, auth);
		successTest(action);
	}

	@Test
	@Memo("일정 등록 후 투두리스트 대량 등록 후 검색")
	public void test_13() throws Exception {
		Random random = new Random();
		Users user = new Users();
		user.setSeq(1L);
		Team team = saveTeam("스프링 아", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 12, 20), "목표 없음", user);
		for (int month = 1; month < 7; month++) {
			PlanByUser plan = null;
			for (int date = 1; date < 20; date++) {
				plan = savePlan(LocalDate.of(2020, month, date), LocalDate.of(2020, 13 - month, date),
					"태그 입니다." + month + " /// " + date, team, user);
			}
			for (int i = 0; i < random.nextInt(5); i++) {
				saveTodo("투두 리스트 테스트" + i, plan, user);
			}
		}
		var action = get("/api/teamManage/plan/{code}/finishedAll", team.getCode()).param("page", "0").param("size", "10")
			.header(AUTHRIZATION, auth);
		successTest(action);
	}
}
