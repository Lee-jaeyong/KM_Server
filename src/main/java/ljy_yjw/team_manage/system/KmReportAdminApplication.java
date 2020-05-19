package ljy_yjw.team_manage.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryImplementationPostfix = "impl")
@EnableConfigurationProperties({ CustomFileUpload.class })
@MapperScan("ljy_yjw.team_manage.system.dbConn.mybatis")
public class KmReportAdminApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(KmReportAdminApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}
}
