package ljy.book.admin.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import ljy.book.admin.entity.enums.UserRule;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("km_App");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http
//			.anonymous()
//			.and()
//			.authorizeRequests()
//			.anyRequest()
//			.anonymous()
//			.and()
//			.exceptionHandling()
//			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
		http
			.anonymous()
			.and()
			.authorizeRequests()
			.mvcMatchers(HttpMethod.GET,"/professor/**").hasRole(UserRule.PROFESSER.toString())
			.mvcMatchers(HttpMethod.POST,"/professor/**").hasRole(UserRule.PROFESSER.toString())
			.mvcMatchers(HttpMethod.PUT,"/professor/**").hasRole(UserRule.PROFESSER.toString())
			.mvcMatchers(HttpMethod.DELETE,"/professor/**").hasRole(UserRule.PROFESSER.toString())
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}
