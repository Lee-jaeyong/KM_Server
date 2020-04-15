package ljy.book.admin;

import javax.transaction.Transactional;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.common.object.CustomFileUpload;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryImplementationPostfix = "impl")
@EnableConfigurationProperties({ CustomFileUpload.class })
@MapperScan("ljy.book.admin.customRepository")
public class KmReportAdminApplication implements ApplicationRunner {

	@Autowired
	CustomCodeCreator customCodeCreator;

	public static void main(String[] args) {
		SpringApplication.run(KmReportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
	}

}
