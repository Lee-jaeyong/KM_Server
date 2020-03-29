package ljy.book.admin.restDoc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.service.KM_ClassService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(TestCommons.class)
public class TestClass {

	@Autowired
	KM_ClassService km_classService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void a() throws Exception {
		KM_class km_class = new KM_class();
		km_class.setName("C언어");
		km_class.setStartDate("2020-03-01");
		km_class.setEndDate("2020-10-10");
		km_class.setSelectMenu("");

		km_classService.save(km_class);

		this.mockMvc.perform(get("/professor/class/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(document("index"));
	}
}
