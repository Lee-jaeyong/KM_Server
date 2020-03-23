package ljy.book.admin.Repository_TEST;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.entity.KM_user;
import ljy.book.admin.jpaAPI.KM_UserAPI;
import ljy.book.admin.repository.projection.Km_userProjection;

/**
 * 
 * @author LJY 1. findByIdAndPass() << 로그인 테스트
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class Km_userRepository_TEST {

	@Autowired
	KM_UserAPI km_userAPI;

	@Autowired
	ObjectMapper objMapper;

	KM_user km_user;

	@Before
	public void initialize() {
		km_user = new KM_user();
		//km_user.setId("윤지원");
		//km_user.setPass("root");
	}

	@Test
	public void findByIdAndPass() throws JsonProcessingException {
		System.out.println("--------------------------");
		System.out.println("--------------------------");
		System.out.println("--------------------------");
		//Km_userProjection result_user = km_userAPI.findByIdAndPass(km_user.getId(), km_user.getPass());
		//System.out.println(objMapper.writeValueAsString(result_user));
	}
}
