package com.example.demo.config;

import com.example.demo.application.interceptor.DemoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterCeptorの登録　複数登録、順序指定も可能
 */
@Configuration 
public class WebConfig implements WebMvcConfigurer { 
	@Autowired 
	private DemoInterceptor demoInterceptor; 
  
	@Override 
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(demoInterceptor);
		//.addPathPatterns("/input");　パスの指定可能。　
	} 
}