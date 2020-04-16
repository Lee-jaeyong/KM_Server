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
			.authorizeRequests()
			//.antMatchers(HttpMethod.OPTIONS).permitAll()
			.mvcMatchers(HttpMethod.PUT,"/api/users/**").authenticated()
			.mvcMatchers(HttpMethod.DELETE,"/api/users/**").authenticated()
			.mvcMatchers(HttpMethod.PATCH,"/api/user/**").authenticated()
			.mvcMatchers(HttpMethod.GET,"/api/teamManage/**").authenticated()
			.mvcMatchers(HttpMethod.POST,"/api/teamManage/**").authenticated()
			.mvcMatchers(HttpMethod.PUT,"/api/teamManage/**").authenticated()
			.mvcMatchers(HttpMethod.DELETE,"/api/teamManage/**").authenticated()
			.mvcMatchers(HttpMethod.PATCH,"/api/teamManage/**").authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}
