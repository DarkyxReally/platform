package com.platform.system.gate.zuul.filter.error.pre;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.platform.system.gate.zuul.filter.error.BaseErrorFilter;


/**
 * PRE异常处理Filte基类
 */
public abstract class BasePreErrorFilter extends BaseErrorFilter {

    public BasePreErrorFilter(int filterOrder) {
        super(filterOrder);
    }
    
    @Override
    protected final String failedFilterTypeScope() {
        return FilterConstants.PRE_TYPE;
    }
}
