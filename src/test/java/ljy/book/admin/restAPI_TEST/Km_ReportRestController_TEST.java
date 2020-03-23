package ljy.book.admin.restAPI_TEST;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
	public void delete_TEST() throws Exception {
		Km_ReportDTO km_reportDTO = new Km_ReportDTO();
		km_reportDTO.setReportIdx(83l);
		mvc.perform(delete("/report").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(km_reportDTO))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	public void addReport_TEST() throws Exception {
		Km_ReportDTO km_reportDTO = new Km_ReportDTO();
		km_reportDTO.setClassIdx(1l);
		km_reportDTO.setReportContent("fdsfd");
		km_reportDTO.setReportEndDate("2020-10-10");
		km_reportDTO.setReportStartDate("2020-01-01");
		km_reportDTO.setReportTitle("fsdfsdfsd");
		mvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(km_reportDTO))).andDo(print()).andExpect(status().isOk());
	}
}
