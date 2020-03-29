package ljy.book.admin;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.common.repository.CommonRepositoryimpl;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.security.KM_UserService;

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "impl", repositoryBaseClass = CommonRepositoryimpl.class)
@EnableConfigurationProperties({ CustomFileUpload.class })
public class KmRportAdminApplication implements ApplicationRunner {
	@Autowired
	KM_UserService km_userService;
	Logger log = LoggerFactory.getLogger(KmRportAdminApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KmRportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
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
}
