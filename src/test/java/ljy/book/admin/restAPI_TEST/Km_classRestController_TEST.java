package ljy.book.admin.restAPI_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_subject;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.ClassType;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.request.KM_classVO;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class Km_classRestController_TEST {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	KM_ClassAPI km_classAPI;

	MockMvc mvc;

	KM_user km_user;
	KM_class km_class;
	KM_subject km_subject;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	ModelMapper modelMapper;

	@Before
	public void initialize() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
		createUser();
		createClass();
		createSubject();
	}

	@Test
	public void save() throws Exception {
		KM_classVO km_classVO = modelMapper.map(km_class, KM_classVO.class);
		mvc.perform(post("/professor/class").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(km_classVO))).andExpect(status().isOk()).andDo(print());
	}

	public void createUser() {
		km_user = new KM_user();
		km_user.setId("윤지원");
		km_user.setSeq(1L);
	}

	public void createClass() {
		km_class = new KM_class();
		km_class.setName("JPA");
		km_class.setStartDate("2020-03-22");
		km_class.setEndDate("2020-10-10");
		km_class.setType(ClassType.MAJOR);
		km_class.setReplyPermit_state(BooleanState.YSE);
		km_class.setSelectMenu("REPORT,");
		km_class.setUse_state(BooleanState.YSE);
	}

	public void createSubject() {
		km_subject = new KM_subject();
		km_subject.setSeq(1L);
	}
}
