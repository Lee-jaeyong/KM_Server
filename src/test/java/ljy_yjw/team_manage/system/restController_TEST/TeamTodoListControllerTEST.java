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
import ljy_yjw.team_manage.system.domain.dto.TeamDTO;
import ljy_yjw.team_manage.system.domain.dto.TodoListDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamTodoListControllerTEST extends CommonTestConfig {

	@Test
	@Ignore
	public void test_10() throws Exception {
		createUser("dlwodyd202", "a", "이재용", "wodyd202@naver.com");
	}

	@Test
	@Ignore
	@Memo("팀을 등록하는 테스트")
	public void test_11() throws Exception {
		super.login("dlwodyd202", "a");
		TeamDTO team = TeamDTO.builder().name("<script>alert('xss attack!!')</script>").description("")
			.startDate(LocalDate.of(2020, 2, 11)).endDate(LocalDate.of(2020, 12, 12)).build();
		mvc.perform(post("/api/teamManage").header(AUTHRIZATION, auth).contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(team))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("일정을 등록하는 테스트")
	public void test_12() throws Exception {
		super.login("dlwodyd202", "a");
		PlanByUserDTO plan = PlanByUserDTO.builder().tag("태그").start(LocalDate.of(2020, 1, 1)).end(LocalDate.of(2020, 10, 10))
			.build();
		mvc.perform(post("/api/teamManage/plan/{code}", "C81E728D").header(AUTHRIZATION, auth)
			.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(plan))).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Memo("해당 일정의 TodoList를 가져오는 테스트")
	public void test_13() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(get("/api/teamManage/todoList/{seq}", "5").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("TodoList를 등록하는 테스트")
	public void test_14() throws Exception {
		super.login("dlwodyd202", "a");
		TodoListDTO todoList = new TodoListDTO();
		todoList.setTitle("테스트 todolist");
		mvc.perform(post("/api/teamManage/todoList/{seq}", "5").contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(todoList)).header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("TodoList를 변경하는 테스트")
	public void test_15() throws Exception {
		super.login("dlwodyd202", "a");
		TodoListDTO todoList = new TodoListDTO();
		todoList.setTitle("테스트 todolistaaaaaa");
		mvc.perform(put("/api/teamManage/todoList/{seq}", "12").contentType(MediaType.APPLICATION_JSON)
			.content(objMapper.writeValueAsString(todoList)).header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("TodoList의 진행 상태를 변경하는 테스트")
	public void test_16() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(put("/api/teamManage/todoList/{seq}/success", "12").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("TodoList를 삭제하는 테스트")
	public void test_17() throws Exception {
		super.login("dlwodyd202", "a");
		mvc.perform(delete("/api/teamManage/todoList/{seq}", "12").header(AUTHRIZATION, auth)).andDo(print())
			.andExpect(status().isOk());
	}
}
