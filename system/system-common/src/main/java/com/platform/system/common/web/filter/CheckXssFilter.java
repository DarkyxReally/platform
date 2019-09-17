package com.platform.system.common.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.system.common.config.XssConfig;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.context.XssContextHandler;

/**
 * XSS校验过滤器
 * @version: 1.0
 */
public class CheckXssFilter extends OncePerRequestFilter {

	@Autowired
	private XssConfig xssConfig;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (xssConfig.isEnabled()) {
			boolean hasXss = hasXss(request);
			boolean needsFilter = needsFilter(request);

			XssContextHandler.setHasXss(hasXss);
			XssContextHandler.setRequireFilter(needsFilter);
		}
		super.doFilter(request, response, filterChain);
	}

	/**
	 * 是否需要过滤
	 * @param request
	 * @return
	 */
	private boolean needsFilter(HttpServletRequest request) {
		return !xssConfig.getExempts().contains(request.getRequestURI());
	}


	/**
	 * 是否含有XSS嫌疑
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private boolean hasXss(HttpServletRequest request) throws IOException {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String param = new String(((ResettableStreamHttpServletRequest)request).getRequestBody(), CommonConstants.UTF_8);
			if (StringUtils.isNotEmpty(param)) {
				return checkXSSAndSql(param);
			}
		}else {
			Map<String, String[]> submitParams = new HashMap<String, String[]>(request.getParameterMap());
			Set<String> submitNames = submitParams.keySet();
			for (String submitName : submitNames) {
				Object submitValues = submitParams.get(submitName);
				if ((submitValues != null)) {
					for (String submitValue : (String[])submitValues){
						if (checkXSSAndSql(submitValue)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkXSSAndSql(String value) {
		boolean flag = false;
		if (value != null) {
			value = value.replaceAll("", "");
			Pattern scriptPattern = Pattern.compile(
					"<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid anything in a
			// src="http://www.yihaomen.com/article/java/..." type of
			// e-xpression
			scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid e-xpression(...) expressions
			scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			flag = scriptPattern.matcher(value).find();
			if (flag) {
				return flag;
			}
		}
		return flag;
	}
}
