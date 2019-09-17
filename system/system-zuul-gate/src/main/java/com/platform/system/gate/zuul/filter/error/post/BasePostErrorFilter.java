package com.platform.system.gate.zuul.filter.error.post;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.platform.system.gate.zuul.filter.error.BaseErrorFilter;


/**
 * POST类型异常处理Filter基类
 */
public abstract class BasePostErrorFilter extends BaseErrorFilter {

    public BasePostErrorFilter(int filterOrder) {
        super(filterOrder);
    }
    
    @Override
    protected final String failedFilterTypeScope() {
        return FilterConstants.POST_TYPE;
    }
}
