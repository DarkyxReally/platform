package com.platform.system.common.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Charsets;
import com.platform.system.common.constant.RequestAttributeConst;

/**
 * 可重复读取requestBody的过滤器
 * Request filter that intercepts the request body, forwards it to the controller
 * as a request attribute and resets the stream.
 */
public class ResettableRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        request.removeAttribute(RequestAttributeConst.REQUEST_BODY_KEY);
        ResettableStreamHttpServletRequest wrapperRequest = new ResettableStreamHttpServletRequest(request);
        byte[] body = wrapperRequest.getRequestBody();
        if (body != null) {
            request.setAttribute(RequestAttributeConst.REQUEST_BODY_KEY, new String(body, Charsets.UTF_8));
        }
        super.doFilter(wrapperRequest, response, filterChain);
    }

}