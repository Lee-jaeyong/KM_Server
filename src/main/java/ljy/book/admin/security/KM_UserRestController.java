package ljy.book.admin.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KM_UserRestController {

	@Autowired
	TokenStore tokenStore;

	@GetMapping("/oauth/revoke-token")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String tokenValue = authHeader.replace("Bearer", "")
				.trim();
			OAuth2AccessToken accessToken = tokenStore
				.readAccessToken(tokenValue);
			tokenStore.removeAccessToken(accessToken);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
