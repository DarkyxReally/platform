package com.platform.system.gate.zuul.filter.post;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;
import com.platform.system.gate.zuul.filter.BaseFilter;
/**
 * 基本post过滤器
 * @version: 1.0
 */
public abstract class BasePostFilter extends BaseFilter {

    /**
     * 后置过滤器构造方法
     * @param filterOrder 过滤器顺序
     */
    public BasePostFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    public final String filterType(){
        return FilterConstants.POST_TYPE;
    }
    
    /**
     * 禁止默认的响应数据处理器进行处理
     */
    protected void disableDefaultSendResponseFilter(){
        RequestContext context = RequestContext.getCurrentContext();
        //参照{@code}org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter中的shouldFilter
        context.getZuulResponseHeaders().clear();
        context.remove("responseDataStream");
        context.remove("responseDataStream");
        context.remove("responseBody");
    }
}
