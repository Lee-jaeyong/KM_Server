package ljy.book.admin;

import java.util.HashSet;
import java.util.Set;

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
import ljy.book.admin.common.repository.CommonRepositoryimpl;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_fileAndImgOfReport;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_Report_Professor_Service;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.security.KM_UserService;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryImplementationPostfix = "impl", repositoryBaseClass = CommonRepositoryimpl.class)
@EnableConfigurationProperties({ CustomFileUpload.class })
@MapperScan("ljy.book.admin.customRepository")
public class KmReportAdminApplication implements ApplicationRunner {

	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_Class_Professor_Service km_classService;

	@Autowired
	KM_Report_Professor_Service km_reportService;

	@Autowired
	CustomCodeCreator customCodeCreator;

	public static void main(String[] args) {
		SpringApplication.run(KmReportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		// 학생 임의 테스트
		this.createUser("dlwodyd202", "dlwodyd", "이재용", UserRule.PROFESSER).createUser("dbswldnjs202", "dbswldnjs", "윤지원",
			UserRule.PROFESSER);
		// 수업 임의 테스트
//		this.createClass("C언어", "dlwodyd202").createClass("자바", "dlwodyd202");
//		// 과제 임의 테스트
//		this.createReport("C언어 레포트는...", "2020-10-01", "2020-12-12", 3).createReport("자바 레포트는...", "2020-01-01", "2020-10-10", 3)
//			.createReport("시스템 레포트는...", "2020-01-01", "2020-10-10", 3);
//		this.createReportFile("C언어 파일", FileType.FILE, 5).createReportFile("자바 책", FileType.IMG, 5);
	}

	protected KmReportAdminApplication createReportFile(String fileName, FileType fileType, long reportIdx) {
		KM_fileAndImgOfReport km_fileAndImg = new KM_fileAndImgOfReport();
		km_reportService.uploadFile(fileName, fileType, reportIdx);
		return this;
	}

	protected KmReportAdminApplication createClass(String className, String id) {
		KM_user user = km_userService.findByUserId(id);
		KM_class km_class = new KM_class();
		km_class.setName(className);
		km_class.setPlannerDocName(null);
		km_class.setContent("");
		user.addKmClass(km_class);
		km_class = km_classService.save(km_class, user.getId());
		km_class.setClassCode(customCodeCreator.createCode(km_class.getSeq().toString()));
		return this;
	}

	protected KmReportAdminApplication createUser(String id, String pass, String name, UserRule userRule) {
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

	protected KmReportAdminApplication createReport(String name, String startDate, String endDate, long classIdx) {
		KM_reportVO km_reportVO = new KM_reportVO();
		km_reportVO.setName(name);
		km_reportVO.setStartDate(startDate);
		km_reportVO.setEndDate(endDate);
		km_reportVO.setUseSubmitDates(BooleanState.YSE);
		km_reportVO.setClassIdx(classIdx);
		km_reportService.save(km_reportVO);
		return this;
	}
}
