package com.platform.api.gate.filter.pre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.system.gate.zuul.filter.pre.BasePreFilter;

/**
 * 验证token过滤器
 * @version: 1.0
 */
@Component
public class ClientTokenFilter extends BasePreFilter {

    public ClientTokenFilter() {
        super(999);
    }
    
    public ClientTokenFilter(int filterOrder) {
        super(filterOrder);
    }

    @Autowired
    private AuthClientConfig clientConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public Object run(){
        // 请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 服务间的认证头
        ctx.addZuulRequestHeader(clientConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        return null;
    }
}
