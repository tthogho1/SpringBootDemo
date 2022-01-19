package com.example.demo.application.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
 
/**
 * Filterクラス
 */
@Slf4j
public class DemoFilter implements Filter {
 
	@Override
    public void init(FilterConfig filterConfig) throws ServletException { }
 
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		log.info("do Filter");
		chain.doFilter(request, response);
	}
 
	@Override
    public void destroy() { }
}