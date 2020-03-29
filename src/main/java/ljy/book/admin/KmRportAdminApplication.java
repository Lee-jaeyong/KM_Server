package ljy.book.admin;

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
	}
}
