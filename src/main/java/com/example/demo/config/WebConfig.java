package com.example.demo.config;

import com.example.demo.application.filter.DemoFilter;
import com.example.demo.application.interceptor.DemoInterceptor;
import com.example.demo.application.interceptor.SetDoubleSubmitCheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  Interceptorの登録　複数登録、順序指定も可能
 */
@Configuration 
public class WebConfig implements WebMvcConfigurer { 
	@Autowired 
	private DemoInterceptor demoInterceptor; 

	@Autowired
	private SetDoubleSubmitCheckTokenInterceptor setDoubleSubmitCheckTokenInterceptor;
	
	/*
	 *  インターセプタ登録
	 */
	@Override 
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(demoInterceptor);
		registry.addInterceptor(setDoubleSubmitCheckTokenInterceptor);
		//.addPathPatterns("/input");　パスの指定可能。　
	} 
	

	/**
	 *  Filter登録 
	 *  @return bean フィルター登録Bean
	 */
	@Bean
	public FilterRegistrationBean<DemoFilter> demoWebFilter() {

		FilterRegistrationBean<DemoFilter> bean = 
				new FilterRegistrationBean<>(new DemoFilter());
		bean.addUrlPatterns("/*");
		// bean.setOrder(1); 複数Filterの場合順序指定可能
		return bean;
	}
	
}