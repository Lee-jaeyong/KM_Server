package ljy.book.admin.restAPI_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.dto.Km_userLoginDTO;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
public class Km_userRestController_TEST {

	@Autowired
	WebApplicationContext wac;

	@PersistenceContext
	EntityManager em;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ObjectMapper objMapper;

	MockMvc mvc;

	Km_userLoginDTO user;

	@Before
	public void initialize() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	@Ignore
	@Memo("로그인 성공 케이스")
	public void login_Success_TEST() throws Exception {
		user = new Km_userLoginDTO();
		user.setId("윤지원");
		user.setPass("root");
		this.web_running();
	}

	@Test
	@Ignore
	@Memo("유효성 검사 실패 케이스")
	public void login_valid_falid_TEST() throws Exception {
		user = new Km_userLoginDTO();
		web_running();
	}

	@Test
	@Memo("로그인 실패 케이스")
	@Ignore
	public void login_falid_TEST() throws Exception {
		user = new Km_userLoginDTO();
		user.setId("윤지원");
		user.setPass("");
		web_running();
	}

	private void web_running() throws Exception {
		mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaTypes.HAL_JSON)
				.content(objMapper.writeValueAsString(user))).andDo(print()).andExpect(status().isOk());
	}
}
