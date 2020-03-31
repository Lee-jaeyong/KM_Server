package ljy.book.admin.restAPI_TEST;

import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ljy.book.admin.dto.Km_userLoginDTO;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
public class Km_userRestController_TEST {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	MockMvc mvc;

	Km_userLoginDTO user;
}
