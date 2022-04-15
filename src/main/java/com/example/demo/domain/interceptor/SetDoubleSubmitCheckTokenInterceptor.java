package com.example.demo.domain.interceptor;

import com.example.demo.config.DoubleSubmitCheckToken;
import com.example.demo.domain.dto.DoubleSubmitCheckTokenHolder;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 二重送信防止チェックのトークンをセッションに設定する https://github.com/miyabayt/spring-boot-doma2-sample.gitから
 */
@Slf4j
@Component
public class SetDoubleSubmitCheckTokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// コントローラーの動作前
		val expected = DoubleSubmitCheckToken.getExpectedToken(request);
		val actual = DoubleSubmitCheckToken.getActualToken(request);
		DoubleSubmitCheckTokenHolder.set(expected, actual);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// コントローラーの動作後
		if (StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			// POSTされたときにトークンが一致していれば新たなトークンを発行する
			val expected = DoubleSubmitCheckToken.getExpectedToken(request);
			val actual = DoubleSubmitCheckToken.getActualToken(request);

			log.debug("expected : " + expected + " actual : " + actual);
			if (expected != null && actual != null && Objects.equals(expected, actual)) {
				DoubleSubmitCheckToken.renewToken(request);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 処理完了後
		DoubleSubmitCheckTokenHolder.clear();
	}
}
