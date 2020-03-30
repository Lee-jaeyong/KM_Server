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
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.security.KM_UserService;
import ljy.book.admin.service.KM_ClassService;
import ljy.book.admin.service.KM_ReportService;

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "impl", repositoryBaseClass = CommonRepositoryimpl.class)
@EnableConfigurationProperties({ CustomFileUpload.class })
public class KmRportAdminApplication implements ApplicationRunner {
	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_ClassService km_classService;

	@Autowired
	KM_ReportService km_reportService;

	public static void main(String[] args) {
		SpringApplication.run(KmRportAdminApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		// 학생 임의 테스트
		this.createUser("dlwodyd202", "dlwodyd", "이재용", UserRule.PROFESSER).createUser("dbswldnjs202", "dbswldnjs", "윤지원",
			UserRule.PROFESSER);
		// 수업 임의 테스트
		this.createClass("C언어", "dlwodyd202").createClass("자바", "dlwodyd202");
		// 과제 임의 테스트
		this.createReport("C언어 레포트는...", "2020-10-01", "2020-12-12", 3);
//		.createReport("자바 레포트는...", "2020-01-01", "2020-10-10", 3)
//		.createReport("시스템 레포트는...", "2020-01-01", "2020-10-10", 3);
	}

	protected KmRportAdminApplication createClass(String className, String id) {
		KM_user user = km_userService.findByUserId(id);
		KM_class km_class = new KM_class();
		km_class.setName(className);
		km_class.setPlannerDocName(null);
		km_class.setStartDate("2020-03-03");
		km_class.setEndDate("2020-10-01");
		km_class.setContent("");
		km_class.setSelectMenu("REPORT,");
		user.addKmClass(km_class);
		km_classService.save(km_class, user.getId());
		return this;
	}

	protected KmRportAdminApplication createUser(String id, String pass, String name, UserRule userRule) {
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

	protected KmRportAdminApplication createReport(String name, String startDate, String endDate, long classIdx) {
		KM_reportVO km_reportVO = new KM_reportVO();
		km_reportVO.setName(name);
		km_reportVO.setStartDate(startDate);
		km_reportVO.setEndDate(endDate);
		km_reportVO.setSubmitOverDue_state(BooleanState.YSE);
		km_reportVO.setShowOtherReportOfStu_state(BooleanState.NO);
		km_reportVO.setClassIdx(classIdx);
		km_reportService.save(km_reportVO);
		return this;
	}
}
