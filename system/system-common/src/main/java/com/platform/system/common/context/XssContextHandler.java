package com.platform.system.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @version: 1.0
 */
public class XssContextHandler {

	/**
	 * 当前线程上下文线程变量,子线程不能获取该线程变量数据
	 */
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

	/**
	 * 请求上下文中的请求是否含有XSS嫌疑
	 */
	public static final String CONTEXT_KEY_HAS_XSS = "hasXss";

	/**
	 * 请求上下文中的请求路径
	 */
	public static final String CONTEXT_KEY_REQUIRE_FILTER = "requireFilter";

	/**
	 * 获取上下文数据中是否含有XSS嫌疑
	 * @return
	 */
	public static Boolean getHasXss() {
		return (Boolean) get(CONTEXT_KEY_HAS_XSS);
	}

	/**
	 * 设置上下文数据中是否含有XSS嫌疑
	 * @return
	 */
	public static void setHasXss(boolean hasXss) {
		set(CONTEXT_KEY_HAS_XSS, hasXss);
	}

	/**
	 * 获取上下文请求路径
	 * @return
	 */
	public static Boolean getRequireFilter() {
		return (Boolean) get(CONTEXT_KEY_REQUIRE_FILTER);
	}

	/**
	 * 设置上下文请求路径
	 * @return
	 */
	public static void setRequireFilter(boolean requireFilter) {
		set(CONTEXT_KEY_REQUIRE_FILTER, requireFilter);
	}

	/**
	 * 设置上下文数据
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value){
		Map<String, Object> map = threadLocal.get();
		if(map == null){
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		map.put(key, value);
	}

	/**
	 * 获取上下文数据
	 * @param key
	 */
	public static Object get(String key){
		Map<String, Object> map = threadLocal.get();
		if(map == null){
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		return map.get(key);
	}

	/**
	 * 移除线程变量
	 */
	public static void remove(){
		threadLocal.remove();
	}

	/**
	 * 根据key移除上下文数据
	 * @param key
	 */
	public static void remove(String key){
		Map<String, Object> map = threadLocal.get();
		if(map != null){
			map.remove(key);
		}
	}
}
