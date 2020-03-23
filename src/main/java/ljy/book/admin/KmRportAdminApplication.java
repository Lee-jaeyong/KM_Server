package ljy.book.admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.common.repository.CommonRepositoryimpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "impl", repositoryBaseClass = CommonRepositoryimpl.class)
@EnableConfigurationProperties({ CustomFileUpload.class })
public class KmRportAdminApplication implements ApplicationRunner {

	Logger log = LoggerFactory.getLogger(KmRportAdminApplication.class);

	@PersistenceContext
	EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(KmRportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
//		Km_subject subject = new Km_subject();
//		subject.setSubjectNM("융합소프트웨어");
//
//		Km_class km_class_save_1 = new Km_class();
//		km_class_save_1.setClassName("C언어");
//		km_class_save_1.setClassType("전공");
//		km_class_save_1.setStartClassDate("2020-01-01");
//		km_class_save_1.setEndClassDate("2020-10-07");
//		km_class_save_1.setClassContent("C언어 참고 자료는....");
//		km_class_save_1.setClassCode("1");
//
//		Km_class km_class_save_2 = new Km_class();
//		km_class_save_2.setClassName("자바");
//		km_class_save_2.setClassType("전공");
//		km_class_save_2.setStartClassDate("2020-01-01");
//		km_class_save_2.setEndClassDate("2020-11-07");
//		km_class_save_2.setClassContent("자바 참고 자료는....");
//		km_class_save_2.setClassCode("2");
//
//		Km_class km_class_save_3 = new Km_class();
//		km_class_save_3.setClassName("스프링 프레임워크");
//		km_class_save_3.setClassType("전공");
//		km_class_save_3.setStartClassDate("2020-01-01");
//		km_class_save_3.setEndClassDate("2020-03-05");
//		km_class_save_3.setClassContent("스프링 프레임워크 참고 자료는....");
//		km_class_save_3.setClassCode("3");
//
//		em.persist(subject);
//		em.persist(km_class_save_1);
//		em.persist(km_class_save_2);
//		em.persist(km_class_save_3);
//
//		Km_user user = new Km_user();
//		user.setId("윤asasa지원");
//		user.setRule("root");
//		user.setPass("root");
//		user.setEmail("wodyd202@naver.com");
//		user.setPhone("010-422-5921");
//		user.setBirth("950717");
//		user.setName("root");
//		user.setUser_Rule(User_Rule.PROFESSER);
//
//		user.addKm_class(km_class_save_1);
//		user.addKm_class(km_class_save_2);
//		user.addKm_class(km_class_save_3);
//		subject.addKm_user(user);
//		subject.addKm_class(km_class_save_1);
//		subject.addKm_class(km_class_save_2);
//		subject.addKm_class(km_class_save_3);
//
//		Km_Report km_report = new Km_Report();
//		km_report.setReportTitle("C언어 레포트는 ...");
//		Km_Report km_report1 = new Km_Report();
//		km_report1.setReportTitle("C언어 레포트는 ...");
//		Km_Report km_report2 = new Km_Report();
//		km_report2.setReportTitle("C언어 레포트는 ...");
//		Km_Report km_report3 = new Km_Report();
//		km_report3.setReportTitle("C언어 레포트는 ...");
//		Km_Report km_report4 = new Km_Report();
//		km_report4.setReportTitle("C언어 레포트는 ...");
//
//		km_class_save_1.addReportInThis(km_report);
//		km_class_save_1.addReportInThis(km_report1);
//		km_class_save_1.addReportInThis(km_report2);
//		km_class_save_1.addReportInThis(km_report3);
//		km_class_save_1.addReportInThis(km_report4);
//
//		em.persist(km_report);
//		em.persist(km_report1);
//		em.persist(km_report2);
//		em.persist(km_report3);
//		em.persist(km_report4);
//		em.persist(user);
	}
}
