package com.ssafy.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.mvc.interceptor.AdminInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
//	@Autowired
//	AdminInterceptor adminInterceptor;
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(adminInterceptor).addPathPatterns("/users");
//	}
	
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        .allowedOriginPatterns("http://localhost:*") // 와일드카드 허용
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//        .allowedHeaders("*")
//        .allowCredentials(true);  // 인증 정보 허용
//    }
	
//	@Autowired
//	private JwtInterceptor jwtInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
//		.excludePathPatterns("/api-user/**", "/swagger-ui/**", "/v3/api-docs/**");
//	}
}
