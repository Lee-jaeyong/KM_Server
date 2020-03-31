package ljy.book.admin;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.restDoc.TestCommons;
import ljy.book.admin.security.KM_UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestCommons.class)
public class CommonTestConfig {

	protected String clientId = "KMapp";

	protected String clientPass = "pass";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	public MockMvc mvc;

	@Autowired
	public ObjectMapper objMapper;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_Class_Professor_Service km_classService;

	protected void createClass(String className, String id) {
		KM_class km_class = new KM_class();
		km_class.setName(className);
		km_class.setPlannerDocName(null);
		km_class.setStartDate("2020-03-03");
		km_class.setEndDate("2020-10-01");
		km_class.setContent("");
		km_class.setSelectMenu("REPORT,");
		km_classService.save(km_class, id);
	}

	protected void createUser() {
		KM_user km_user = new KM_user();
		km_user.setId("dlwodyd202");
		km_user.setPass("dlwodyd");
		km_user.setEmail("wodyd202@naver.com");
		km_user.setName("이재용");
		Set<UserRule> rules = new HashSet<UserRule>();
		rules.add(UserRule.PROFESSER);
		km_user.setUserRule(rules);

		this.km_userService.save(km_user);
	}

	protected String requestBodyPlus(String rowData, String plusData) {
		StringBuilder resultStr = new StringBuilder();
		resultStr.append(rowData);
		resultStr.append(plusData);
		return resultStr.toString();
	}
}
