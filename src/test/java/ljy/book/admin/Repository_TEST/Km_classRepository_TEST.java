package ljy.book.admin.Repository_TEST;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.entity.KM_user;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.repository.projection.Km_classProjection;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Km_classRepository_TEST {

	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	ObjectMapper objectMapper;

	KM_user km_user;

	@Before
	public void initialize() {
		km_user = new KM_user();
		//km_user.setId("윤지원");
	}

	@Test
	public void findByClassIdx() {
	}
	
	@Test
	@Ignore
	public void findOneByUser_Id() throws JsonProcessingException {
		//List<Km_classProjection> list = km_classAPI.findByKmUser_id("윤지원");
		//System.out.println(list.size());
	}
}
