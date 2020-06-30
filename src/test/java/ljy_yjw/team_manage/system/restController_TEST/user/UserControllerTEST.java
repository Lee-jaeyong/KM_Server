package ljy_yjw.team_manage.system.restController_TEST.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.security.UsersService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTEST extends CommonTestConfig {

	@Autowired
	UsersService service;

	@Before
	public void init() {
	}

	@Test
	@Memo("" + "아이디 : X ," + "비밀번호  : X," + "이름 : X," + "이메일 : X")
	public void test_10() throws Exception {
		UserDTO user = this.createUser("안녕하세요", "aa", "dsadsa", "aaa");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : X ," + "비밀번호  : X," + "이름 : X," + "이메일 : X")
	public void test_11() throws Exception {
		UserDTO user = this.createUser("dlwodyd202ㅇㄴ어놓ㅇ", "aa", "dsadsa", "aaa");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : X ," + "비밀번호  : X," + "이름 : X," + "이메일 : X")
	public void test_12() throws Exception {
		UserDTO user = this.createUser("dlwodyd20DSKAJUDSAJKDHJSKADHSAJKHfdklahfjskdhfjkdlskfjhskdlhfsdkjh2", "aa", "dsadsa",
			"aaa");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("중복되는 아이디가 존재하는 경우")
	public void test_13() throws Exception {
		UserDTO user = this.createUser("dlwodyd202", ".d!d1489314", "이재용", "wodyd202@naver.com");
		MockHttpServletRequestBuilder action = post("/api/users");
		action = super.getMockHttpServletRequest(action, user);
		successTest(action);
		action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : O ," + "비밀번호  : O," + "이름 : X," + "이메일 : X")
	public void test_14() throws Exception {
		UserDTO user = this.createUser("dbswldnjs202", ".d!d1489314", "dsadsa", "aaa");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : O ," + "비밀번호  : O," + "이름 : O," + "이메일 : X")
	public void test_15() throws Exception {
		UserDTO user = this.createUser("dbswldnjs202", ".d!d1489314", "윤지원", "aaa");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : O ," + "비밀번호  : O," + "이름 : O," + "이메일 : X")
	public void test_16() throws Exception {
		UserDTO user = this.createUser("dbswldnjs202", ".d!d1489314", "윤지원", "wodyd202");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		badRequestTest(action);
	}

	@Test
	@Memo("" + "아이디 : O ," + "비밀번호  : O," + "이름 : O," + "이메일 : O")
	public void test_17() throws Exception {
		UserDTO user = this.createUser("dbswldnjs202", ".d!d1489314", "윤지원", "wodyd202@naver.com");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		successTest(action);
	}

	@Test
	@Memo("회원가입 성공 테스트")
	public void test_18() throws Exception {
		UserDTO user = this.createUser("wodyd222", "d1489314aaaa", "이재용", "wodyd202@naver.com");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		successTest(action);
	}

	@Test
	@Memo("로그인 성공 테스트")
	public void test_19() throws Exception {
		UserDTO user = this.createUser("wodyd2002", ".d!d1489314", "이재용", "wodyd202@naver.com");
		MockHttpServletRequestBuilder action = post("/api/users");
		super.getMockHttpServletRequest(action, user);
		successTest(action);
		super.login(user.getId(), user.getPass());
	}

	@Test
	@Memo("회원 수정 테스트")
	public void test_20() throws Exception {
		UserDTO user = this.createUser("wodyd2002", ".d!d1489314", "이재용수정", "WODYD20002@naver.com");
		super.login(user.getId(), user.getPass());
		MockHttpServletRequestBuilder action = put("/api/users").header(AUTHRIZATION, auth);
		super.getMockHttpServletRequest(action, user);
		successTest(action);
	}
	
}
