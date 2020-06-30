package ljy_yjw.team_manage.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.security.UsersService;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryImplementationPostfix = "impl")
@EnableConfigurationProperties({ CustomFileUpload.class })
@MapperScan("ljy_yjw.team_manage.system.dbConn.mybatis")
public class KmReportAdminApplication implements ApplicationRunner {

	@Autowired
	UsersService service;

	public static void main(String[] args) {
		SpringApplication.run(KmReportAdminApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		UserDTO user = new UserDTO();
		user.setId("wodyd2");
		user.setPass("a");
		user.setEmail("wodyd202@naver.com");
		user.setName("이재용");
		service.save(user);
	}
}
