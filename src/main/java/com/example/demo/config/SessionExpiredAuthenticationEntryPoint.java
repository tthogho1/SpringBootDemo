package com.example.demo.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


/**
 * Spring-Securityの設定を行うクラスSessionが無効状態になっているとき、
 * デフォルトのリダイレクトURLの後ろにリクエストパラメータ"timeout"を付与する。
 * https://www.greptips.com/posts/847/ から
 * exceptionHandling().authenticationEntryPointに登録する。
 */
public class SessionExpiredAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	public SessionExpiredAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
			throws IOException, ServletException {

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		super.commence(request, response, authException);
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {

		String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);
		if (isRequestedSessionInvalid(request)) {
			redirectUrl += redirectUrl.contains("?") ? "&" : "?";
			redirectUrl += "timeout";
		}
		return redirectUrl;
	}

	private boolean isRequestedSessionInvalid(HttpServletRequest request) {
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	}
	
}
