package ljy.book.admin.restAPI_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import ljy.book.admin.dto.Km_ReportDTO;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class Km_ReportRestController_TEST {

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@Before
	public void initialize() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	public void getReportList() throws Exception {
		mvc.perform(get("/report/1/list").param("page", "0").param("size", "2")).andExpect(status().isOk())
				.andDo(print());
	}
}