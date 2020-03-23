package ljy.book.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class Km_Report_RestAPI_TEST {

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	@Test
	public void getReport_TEST() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("UTF-8", true))
				.build();
		System.out.println(
				mvc.perform(get("/report/class").param("page", "5").param("size", "2").param("classIdx", "22"))
						.andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
	}
}
