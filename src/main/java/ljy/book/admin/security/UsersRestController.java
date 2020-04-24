package ljy.book.admin.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.UserDTO;

@RestController
@RequestMapping(value = "/api/users")
public class UsersRestController {

	@Autowired
	UsersService userService;

	@Autowired
	TokenStore tokenStore;

	@DeleteMapping
	public ResponseEntity<?> delete(@Current_User Users user) {
		userService.delete(user);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid UserDTO user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		userService.update(user);
		user.setPass(null);
		EntityModel<UserDTO> model = new EntityModel<UserDTO>(user);
		model.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(model);
	}

	@GetMapping("/dupId")
	public boolean checkDupId(String id) {
		return userService.checkDupId(id);
	}

	@PostMapping
	public ResponseEntity<?> join(@RequestBody @Valid UserDTO user, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		userService.save(user);
		user.setPass(null);
		EntityModel<UserDTO> model = new EntityModel<UserDTO>(user);
		model.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		model.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/update").withRel("update"));
		model.add(ControllerLinkBuilder.linkTo(this.getClass()).slash("/delete").withRel("delete"));
		return ResponseEntity.ok(model);
	}

	@GetMapping("/oauth/revoke-token")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		try {
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null) {
				String tokenValue = authHeader.replace("Bearer", "").trim();
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				tokenStore.removeAccessToken(accessToken);
			}
		}catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
