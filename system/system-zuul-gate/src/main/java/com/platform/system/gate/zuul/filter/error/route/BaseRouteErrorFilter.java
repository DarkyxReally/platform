package com.platform.system.gate.zuul.filter.error.route;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.platform.system.gate.zuul.filter.error.BaseErrorFilter;


/**
 * PRE异常处理Filte基类
 * @version: 1.0
 */
public abstract class BaseRouteErrorFilter extends BaseErrorFilter {

    public BaseRouteErrorFilter(int filterOrder) {
        super(filterOrder);
    }
    
    @Override
    protected final String failedFilterTypeScope() {
        return FilterConstants.ROUTE_TYPE;
    }
}
