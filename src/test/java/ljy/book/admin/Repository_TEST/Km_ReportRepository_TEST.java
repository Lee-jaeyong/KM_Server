package ljy.book.admin.Repository_TEST;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.jpaAPI.KM_ReportAPI;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Km_ReportRepository_TEST {

	@Autowired
	KM_ReportAPI km_reportAPI;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void findByReportIdx() throws Exception {
		//System.out.println(objectMapper.writeValueAsString(km_reportAPI.findByReportIdx(4L)));
	}
}
