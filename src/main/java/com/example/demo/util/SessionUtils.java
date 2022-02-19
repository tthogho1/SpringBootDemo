package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.val;
import org.springframework.web.util.WebUtils;


/**
 * セッション情報の管理　https://github.com/miyabayt/spring-boot-doma2-sample.gitから
 */
public class SessionUtils {

	/**
	 * 指定した属性名で値を取得します。
	 *
	 * @param request HttpServletRequest
	 * @param attributeName 属性名
	 * @param <T> オブジェクト
	 * @return ret　オブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpServletRequest request, String attributeName) {
		T ret = null;
		val session = getSession(request);
		val mutex = getMutex(request);
		if (mutex != null) {
			synchronized (mutex) {
				ret = (T) session.getAttribute(attributeName);
			}
		}
		return ret;
	}

	/**
	 * 指定した属性名で値を設定します。
	 *
	 * @param request HttpServletRequest
	 * @param attributeName 属性名
	 * @param value 格納するオブジェクト
	 */
	public static void setAttribute(HttpServletRequest request, String attributeName, Object value) {
		val session = getSession(request);
		val mutex = getMutex(request);
		if (mutex != null) {
			synchronized (mutex) {
				session.setAttribute(attributeName, value);
			}
		}
	}

	/**
	 * mutexを取得します。
	 *
	 * @param request HttpServletRequest
	 * @return mutex
	 */
	public static Object getMutex(HttpServletRequest request) {
		val session = getSession(request);

		if (session != null) {
			val mutex = WebUtils.getSessionMutex(session);
			return mutex;
		}

		return null;
	}

	/**
	 * 存在するセッションを取得します。
	 *
	 * @param request HttpServletRequest
	 * @return HttpSession　セッション
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession(false);
	}
}
