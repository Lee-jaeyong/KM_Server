package ljy.book.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy.book.admin.common.object.CustomFileUpload;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryImplementationPostfix = "impl")
@EnableConfigurationProperties({ CustomFileUpload.class })
@MapperScan("ljy.book.admin.customRepository")
public class KmReportAdminApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(KmReportAdminApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}

}
