package com.platform.system.gate.zuul;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


/**
 * 网关filter处理器
 * @version: 1.0
 */
public class GateFilterProcessor extends FilterProcessor{
    
    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (ZuulException e) {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.set(FilterConstant.FAILED_FILTER, filter);
            throw e;
        }
    }
}
