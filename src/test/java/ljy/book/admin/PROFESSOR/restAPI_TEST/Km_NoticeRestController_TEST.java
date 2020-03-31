package ljy.book.admin.restAPI_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class Km_NoticeRestController_TEST {

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	@Before
	public void initialize() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	public void hatoseTest() throws Exception {
		System.out.println(mvc.perform(get("/notice").param("classIdx", "15").param("size", "3").param("page", "0"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
	}
}
