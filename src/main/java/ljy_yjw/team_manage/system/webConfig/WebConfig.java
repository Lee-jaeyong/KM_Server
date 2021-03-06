package ljy_yjw.team_manage.system.webConfig;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private static final String[] RESOURCE_LOCATIONS = { "classpath:/static/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(RESOURCE_LOCATIONS).setCachePeriod(3600).resourceChain(true)
			.addResolver(new PathResourceResolver());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new ResourceHttpMessageConverter(true));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("*")
			.allowedHeaders("Origin", "Accept", "Content-Type", "Authorization").allowCredentials(true);
	}

	@Bean
	public FilterRegistrationBean<CorsFilterConfig> loggingFilter() {
		FilterRegistrationBean<CorsFilterConfig> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CorsFilterConfig());
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.addUrlPatterns("/oauth/*");

		return registrationBean;
	}

	@Bean
	public WebMvcConfigurerAdapter controlTowerWebConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				super.configureMessageConverters(converters);
				// 5. WebMvcConfigurerAdapter에 MessageConverter 추가
				converters.add(htmlEscapingConveter());
			}

			private HttpMessageConverter<?> htmlEscapingConveter() {
				ObjectMapper objectMapper = new ObjectMapper();
				// 3. ObjectMapper에 특수 문자 처리 기능 적용
				objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

				// 4. MessageConverter에 ObjectMapper 설정
				MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
				htmlEscapingConverter.setObjectMapper(objectMapper);

				return htmlEscapingConverter;
			}
		};
	}
}
