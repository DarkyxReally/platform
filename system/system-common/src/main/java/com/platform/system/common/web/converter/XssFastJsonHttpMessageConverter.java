package com.platform.system.common.web.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.platform.system.common.context.XssContextHandler;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @version: 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XssFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

	public XssFastJsonHttpMessageConverter() {
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

		Object tempObj = super.read(type, contextClass, inputMessage);

		return this.process(tempObj, type);
	}

	protected Object process(Object obj, Type type) {
		if (this.isNeedProcess()) {
			return this.readAfter(obj, type);
		} else {
			return obj;
		}
	}

	private Object readAfter(Object obj, Type type) {
		try {
			//type实际上就是我们需要convert的model，我们通过反射来完成根据NeedXss注解对String
			//的字段进行xss过滤
			Class clazz = Class.forName(JSON.toJSONString(type).replace("\"", ""));

			if (clazz == null) {
				return obj;
			}

			Field[] fields = clazz.getDeclaredFields();

			if (fields != null && fields.length > 0) {
				// string类型字段名称列表
				List<String> strList = new ArrayList<String>(fields.length);

				// 1. 将需要xss处理的string类型的字段放入strlist
				for (int i = 0; i < fields.length; i++) {


					String mod = Modifier.toString(fields[i].getModifiers());
					if (mod.contains("static")) {
						continue;
					}
					// 得到属性的类名
					String className = fields[i].getType().getSimpleName();
					// 得到属性字段名
					if (className.equalsIgnoreCase("String")) {

						strList.add(fields[i].getName());
					}

				}

				// 2.将strlist中的字段进行xss处理
				if (strList.size() > 0) {

					Object temp = JSON.toJavaObject((JSON) JSON.toJSON(obj), clazz);

					for (int i = 0; i < strList.size(); i++) {
						Method set = clazz.getMethod("set" + strList.get(i).substring(0, 1).toUpperCase()
								+ strList.get(i).substring(1), String.class);
						Method get = clazz.getMethod("get" + strList.get(i).substring(0, 1).toUpperCase()
								+ strList.get(i).substring(1));

						Object tempObj = get.invoke(temp);

						if (tempObj == null) {
							break;
						}

						String content = tempObj.toString();

						set.invoke(temp, HtmlUtils.htmlEscape(content));
					}

					return temp;
				}

			}

		} catch (Exception e) {
			return obj;
		}
		return obj;
	}

	/**
	 * 是否需要过滤XSS
	 * @return
	 */
	private boolean isNeedProcess() {
		return Boolean.TRUE.equals(XssContextHandler.getHasXss()) && Boolean.TRUE.equals(XssContextHandler.getRequireFilter());
	}
}
