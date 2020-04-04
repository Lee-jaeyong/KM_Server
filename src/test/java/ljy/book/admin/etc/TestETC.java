package ljy.book.admin.etc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;

public class TestETC extends CommonTestConfig {

	@Autowired
	KM_Class_Professor_Service km_class_professor_service;

	@Test
	public void a() {
		km_class_professor_service.getClassListPage("dlwodyd202", PageRequest.of(0, 10));
		km_class_professor_service.getClassListPage("dlwodyd202", PageRequest.of(0, 10));
		km_class_professor_service.getClassListPage("dlwodyd202", PageRequest.of(0, 10));
	}
}
