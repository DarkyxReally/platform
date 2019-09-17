package com.platform.system.common.web;


import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 请求包装类
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper{

    private Map<?, ?> params;

    public ParameterRequestWrapper(HttpServletRequest request, Map<?, ?> newParams) {
        super(request);
        this.params = newParams;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map getParameterMap() {
        return params;
    }

    /**
     * 获取参数名
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Enumeration getParameterNames() {
        Vector l = new Vector(params.keySet());
        return l.elements();
    }

    /**
     * 获取参数值
     */
    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[] { (String) v };
        } else {
            return new String[] { v.toString() };
        }
    }

    /**
     * 获取参数
     */
    @Override
    public String getParameter(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else if (v instanceof String) {
            return (String) v;
        } else {
            return v.toString();
        }
    }
}
