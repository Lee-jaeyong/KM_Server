package ljy_yjw.team_manage.system.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;
import ljy_yjw.team_manage.system.custom.util.impl.ObjectEntityFactory;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.FileUploadException;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.user.DupIdException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import lombok.var;

@RestController
@RequestMapping(value = "/api/users")
public class UsersRestController {

	@Resource
	ObjectEntityFactory userEntityFactory;

	@Autowired
	UsersService userService;

	@Autowired
	TokenStore tokenStore;

	@Memo("유저 정보 가져오기")
	@GetMapping
	public ResponseEntity<?> getUser(@Current_User Users user) throws IOException {
		byte[] img = null;
		if (user.getImg() != null) {
			InputStream in = userService.fileDownload(user.getSeq(), user.getImg()).getInputStream();
			img = IOUtils.toByteArray(in);
		}
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		var result = new CustomEntityModel<>(user, this, user.getId(), Link.NOT_INCLUDE);
		jsonResult.put("data", result);
		jsonResult.put("image", img);
		return ResponseEntity.ok(jsonResult);
	}

	@Memo("자신의 사진을 지우는 메소드")
	@DeleteMapping("image")
	public ResponseEntity<?> deleteImage(@Current_User Users user) throws CanNotPerformException, IOException {
		if (user.getImg() == null) {
			throw new CanNotPerformException("사진이 존재하지 않습니다.");
		}
		userService.fileDelete(user.getImg(), user.getSeq());
		user.setImg(null);
		return this.getUser(user);
	}

	@Memo("자신의 사진을 등록하는 메소드")
	@PostMapping("image")
	public ResponseEntity<?> insertImage(MultipartFile file, @Current_User Users user)
		throws CanNotPerformException, IOException, FileUploadException {
		if (user.getImg() != null) {
			throw new CanNotPerformException("이미 사진이 존재합니다.");
		}
		if (!CustomFileUpload.fileUploadFilter(file.getOriginalFilename(), FileType.IMG))
			throw new FileUploadException("코드 형식의 파일은 등록할 수 없습니다.");
		user.setImg(userService.fileUpload(file, user.getSeq(), user.getId()));
		return this.getUser(user);
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@Current_User Users user) {
		userService.delete(user);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid UserDTO user, Errors error, @Current_User Users loginUser)
		throws InputValidException, CanNotPerformException {
		if (!loginUser.getId().equals(user.getId())) {
			throw new CanNotPerformException("자신의 정보만 수정할 수 있습니다.");
		}
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		userService.update(user);
		EntityModel<UserDTO> model = new EntityModel<>(user);
		model.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("").withSelfRel());
		return ResponseEntity.ok(model);
	}

	@GetMapping("/dupId")
	public boolean checkDupId(String id) {
		return userService.checkDupId(id);
	}

	@PostMapping
	public CustomEntityModel<?> join(@RequestBody @Valid UserDTO user, Errors error) throws InputValidException, DupIdException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		if (this.checkDupId(user.getId())) {
			throw new DupIdException("중복되는 아이디가 존재합니다.");
		}
		Users saveUser = userService.save(user);
		return userEntityFactory.get(saveUser, saveUser.getId());
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
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
