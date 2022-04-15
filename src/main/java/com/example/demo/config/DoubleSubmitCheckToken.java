package com.example.demo.config;

import com.example.demo.util.SessionUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.val;
import org.apache.commons.collections.map.LRUMap;
import org.apache.flink.util.XORShiftRandom;

/**
 * 2重送信チェックのトークン管理　https://github.com/miyabayt/spring-boot-doma2-sample.gitから
 */
public class DoubleSubmitCheckToken {

	public static final String DOUBLE_SUBMIT_CHECK_PARAMETER = "_double";

	private static final String DOUBLE_SUBMIT_CHECK_CONTEXT = DoubleSubmitCheckToken.class.getName() + ".CONTEXT";

	// 乱数生成器
	private static final XORShiftRandom random = new XORShiftRandom();

	/**
	 * 画面から渡ってきたトークンを返します。
	 *
	 * @param request HttpServletRequest
	 * @return actual token
	 */
	public static String getActualToken(HttpServletRequest request) {
		return request.getParameter(DOUBLE_SUBMIT_CHECK_PARAMETER);
	}

	/**
	 * 指定のキーをもとにセッションに保存されているトークンを返します。
	 *
	 * @param request HttpServletRequest
	 * @param key キー
	 * @return expected token
	 */
	public static String getExpectedToken(HttpServletRequest request, String key) {
		String token = null;

		if (key == null) {
			key = request.getRequestURI();
		}

		Object mutex = SessionUtils.getMutex(request);
		if (mutex != null) {
			synchronized (mutex) {
				token = getToken(request, key);
			}
		}

		return token;
	}

	/**
	 * セッションに保存されているトークンを返します。
	 *
	 * @param request HttpServletRequest
	 * @return expected token トークン
	 */
	public static String getExpectedToken(HttpServletRequest request) {
		return getExpectedToken(request, null);
	}

	/**
	 * 指定のキーをもとにセッションにトークンを設定します。
	 *
	 * @param request HttpServletRequest
	 * @param key キー
	 * @return token
	 */
	public static String renewToken(HttpServletRequest request, String key) {
		if (key == null) {
			key = request.getRequestURI();
		}
		val token = generateToken();

		Object mutex = SessionUtils.getMutex(request);
		if (mutex != null) {
			synchronized (mutex) {
				setToken(request, key, token);
			}
		}

		return token;
	}

	/**
	 * セッションにトークンを設定します。
	 *
	 * @param request HttpServletRequest
	 * @return token
	 */
	public static String renewToken(HttpServletRequest request) {
		return renewToken(request, null);
	}

	/**
	 * トークンを生成します。
	 *
	 * @return token
	 */
	public static String generateToken() {
		return String.valueOf(random.nextInt(Integer.MAX_VALUE));
	}

	/**
	 * セッションに格納されたLRUMapを取り出す。存在しない場合は作成して返す。
	 *
	 * @param request HttpServletRequest
	 * @return map LRUMap
	 */
	protected static LRUMap getLRUMap(HttpServletRequest request) {
		LRUMap map = SessionUtils.getAttribute(request, DOUBLE_SUBMIT_CHECK_CONTEXT);

		if (map == null) {
			map = new LRUMap(10);
		}

		return map;
	}

	/**
	 * トークンを取得する。
	 *
	 * @param request HttpServletRequest
	 * @param key キー
	 * @return LRUMap マップ
	 */
	protected static String getToken(HttpServletRequest request, String key) {
		LRUMap map = getLRUMap(request);
		val token = (String) map.get(key);
		return token;
	}

	/**
	 * トークンを保存する。
	 *
	 * @param request HttpServletRequest
	 * @param key キー
	 * @param token トークン
	 */
	protected static void setToken(HttpServletRequest request, String key, String token) {
		LRUMap map = getLRUMap(request);
		map.put(key, token);
		SessionUtils.setAttribute(request, DOUBLE_SUBMIT_CHECK_CONTEXT, map);
	}
}
