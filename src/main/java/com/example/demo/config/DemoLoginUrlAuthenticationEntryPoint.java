package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Timeout用の設定。Timeout時にログイン画面に遷移してしまうため、sessionTimeout時の遷移先を指定。
 *
 */
public class DemoLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public DemoLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {

		String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);
		if (isRequestedSessionInvalid(request)) {
			redirectUrl = "/sessionTimeout";
		}
		return redirectUrl;
	}

	private boolean isRequestedSessionInvalid(HttpServletRequest request) {
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	}
}
