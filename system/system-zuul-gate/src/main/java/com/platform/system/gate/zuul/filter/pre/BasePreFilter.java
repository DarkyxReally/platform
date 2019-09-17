package com.platform.system.gate.zuul.filter.pre;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.platform.system.gate.zuul.filter.BaseFilter;


/**
 * 基本pre过滤器
 * @version: 1.0
 */
public abstract class BasePreFilter extends BaseFilter {

    public BasePreFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    public final String filterType(){
        return FilterConstants.PRE_TYPE;
    }
}
