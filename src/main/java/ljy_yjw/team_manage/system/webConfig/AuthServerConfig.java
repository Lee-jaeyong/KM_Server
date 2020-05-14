package ljy_yjw.team_manage.system.webConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ljy_yjw.team_manage.system.security.UsersService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	UsersService km_userService;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder).checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.jdbc(dataSource).withClient("KMapp").authorizedGrantTypes("password", "refresh_token").scopes("read", "write")
//			.secret(this.passwordEncoder.encode("pass")).accessTokenValiditySeconds(30 * 60)
//			.refreshTokenValiditySeconds(10 * 60 * 6);
		clients.inMemory().withClient("KMapp").authorizedGrantTypes("password", "refresh_token").scopes("read", "write")
			.secret(this.passwordEncoder.encode("pass")).accessTokenValiditySeconds(30 * 60)
			.refreshTokenValiditySeconds(10 * 60 * 6);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore).userDetailsService(km_userService);
	}

}
