package ljy.book.admin.restAPI_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.restDoc.TestCommons;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(TestCommons.class)
public class Km_classRestController_TEST {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	ModelMapper modelMapper;

	@Test
	public void a() throws Exception {
		mvc.perform(get("/professor/class/listPage")
				.param("page", "0")
				.param("size", "10"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Ignore
	public void getClassList() throws Exception {
		mvc.perform(get("/professor/class")).andExpect(status().isOk()).andDo(print())
				.andDo(document("GET - classList",
						responseFields(fieldWithPath("[].seq").description("수업 번호"),
								fieldWithPath("[0].name").description("수업 번호"),
								fieldWithPath("[0].startDate").description("수업 번호"),
								fieldWithPath("[0].endDate").description("수업 번호"),
								fieldWithPath("[0].content").description("수업 번호"),
								fieldWithPath("[0].plannerDocName").description("수업 번호").optional(),
								fieldWithPath("[0].type").description("수업 번호"),
								fieldWithPath("[0].replyPermit_state").description("수업 번호"),
								fieldWithPath("[0].selectMenu").description("수업 번호"),
								fieldWithPath("[0].use_state").description("수업 번호"),
								fieldWithPath("[0].saveState").description("수업 번호"),
								fieldWithPath("[0].links[].rel").description("수업 번호"),
								fieldWithPath("[0].links[].href").description("수업 번호"))));
	}

}
