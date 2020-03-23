package ljy.book.admin;

/**
 * @author 이재용
 * 1. TODO customPageBlockTEST	// 커스텀 페이징 테스트
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ljy.book.admin.service.KM_ReportService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Km_ReportServiceTest {

	@Autowired
	KM_ReportService km_reportSerivce;

	@Test
	public void customPageBlockTEST() {
	}
}
