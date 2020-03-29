package ljy.book.admin.restAPI;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.dto.Km_userLoginDTO;
import ljy.book.admin.dto.validate.Km_userLoginValidator;
import ljy.book.admin.entity.resource.Km_userLoginResource;
import ljy.book.admin.security.KM_UserService;

@RestController
@RequestMapping(value = "/user", produces = MediaTypes.HAL_JSON_VALUE)
public class KM_UserRestController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_UserService userSerivce;

	@Autowired
	Km_userLoginValidator km_userLoginValidator;

	@PostMapping
	public ResponseEntity<?> login(String id, String pass, String grant_type) {
		return ResponseEntity.ok(null);
	}

//	@PostMapping(value = "login")
//	public ResponseEntity login(@RequestBody @Valid Km_userLoginDTO user, Errors error, HttpSession session)
//			throws JsonProcessingException {
//		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
//		if (error.hasErrors()) {
//			return loginError(user, error);
//		}
//		km_userLoginValidator.validate(user, error);
//		if (error.hasErrors()) {
//			return loginError(user, error);
//		}
//		session.setAttribute("id", user.getId());
////		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8)
////				.body(userSerivce.login(user));
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}

	private ResponseEntity loginError(Km_userLoginDTO user, Errors error) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_userLoginResource km_userResource = new Km_userLoginResource(user);
		HashMap<String, Object> result = new HashMap<String, Object>();
		km_userResource.add(linkBuilder.slash(user.getId()).withRel("find_the_id_link"));
		km_userResource.add(linkBuilder.withRel("join_link"));
		km_userResource.add(linkBuilder.slash("login").withRel("login_link"));
		result.put("error", error.getAllErrors());
		result.put("href", km_userResource);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
}
