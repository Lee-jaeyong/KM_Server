package ljy.book.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class Km_class_RestAPI_TEST {

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	@PersistenceContext
	EntityManager em;

	public void findByUserId() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("UTF-8", true))
				.build();
		System.out.println(mvc.perform(get("/professor/class").param("id", "윤지원")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString());
	}

}
