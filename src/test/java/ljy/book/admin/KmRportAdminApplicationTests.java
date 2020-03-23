package ljy.book.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.jpaAPI.KM_UserAPI;
import ljy.book.admin.service.KM_ClassService;
import ljy.book.admin.service.KM_ReportService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class KmRportAdminApplicationTests {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_UserAPI km_userAPI;

	@Autowired
	KM_ClassAPI km_classAPI;

	@PersistenceContext
	EntityManager em;

	@Autowired
	KM_ClassService km_ClassService;

	@Autowired
	KM_ReportService km_reportService;

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	public void mvcTEST() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("UTF-8", true))
				.build();
		System.out.println(mvc.perform(get("/professor/class").param("id", "윤지원")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString());
	}

//	public void pageTest() {
//		CustomPageable pageable = new CustomPageable();
//		pageable.setPage(0);
//		pageable.setSize(3);
//		pageable.setSort("className");
//		Km_user user = new Km_user();
//		user.setId("윤지원");
//		List<Km_class> list = km_classAPI.findByUserId(user);
//		for (Km_class c : list) {
//			System.out.println(c.getClassName());
//		}
//	}

	public void subjectInKm_user() {
		KM_class classes = new KM_class();
		//classes.setClassIdx(22L);
		//Optional<KM_class> a = km_classAPI.findById(classes.getClassIdx());
		//System.out.println(a.isPresent());
	}
}
