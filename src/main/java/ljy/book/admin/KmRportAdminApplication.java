package ljy.book.admin;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.common.repository.CommonRepositoryimpl;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.security.KM_UserService;
import ljy.book.admin.service.KM_ClassService;

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "impl", repositoryBaseClass = CommonRepositoryimpl.class)
@EnableConfigurationProperties({ CustomFileUpload.class })
public class KmRportAdminApplication implements ApplicationRunner {
	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_ClassService km_classService;

	public static void main(String[] args) {
		SpringApplication.run(KmRportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
//		//학생 임의 테스트
//		this.createUser("dlwodyd202", "dlwodyd", "이재용",
//			UserRule.PROFESSER).createUser("dbswldnjs202",
//				"dbswldnjs", "윤지원", UserRule.PROFESSER);
//		//수업 임의 테스트
//		this.createClass("C언어","dlwodyd202")
//			.createClass("자바", "dlwodyd202");
	}

	protected KmRportAdminApplication createClass(String className,
		String id) {
		KM_user user = km_userService.findByUserId(id);
		KM_class km_class = new KM_class();
		km_class.setName(className);
		km_class.setPlannerDocName(null);
		km_class.setStartDate("2020-03-03");
		km_class.setEndDate("2020-10-01");
		km_class.setContent("");
		km_class.setSelectMenu("REPORT,");
		user.addKmClass(km_class);
		km_classService.save(km_class,user.getId());
		return this;
	}

	protected KmRportAdminApplication createUser(String id,
		String pass, String name, UserRule userRule) {
		KM_user km_user = new KM_user();
		km_user.setId(id);
		km_user.setPass(pass);
		km_user.setEmail("wodyd202@naver.com");
		km_user.setName(name);
		Set<UserRule> rules = new HashSet<UserRule>();
		rules.add(userRule);
		km_user.setUserRule(rules);
		this.km_userService.save(km_user);
		return this;
	}
}
