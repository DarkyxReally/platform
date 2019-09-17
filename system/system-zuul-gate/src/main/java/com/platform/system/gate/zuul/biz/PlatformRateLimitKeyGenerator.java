package com.platform.system.gate.zuul.biz;

import java.util.List;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;

import com.google.common.net.HttpHeaders;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties.Policy.Type;
import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.context.user.IUserDetail;
import com.platform.system.gate.zuul.FilterConstant;

/**
 * 请求频率限制KEY生成器
 * @version: 1.0
 */
public class PlatformRateLimitKeyGenerator implements RateLimitKeyGenerator {

    private static final String ANONYMOUS_USER = "anonymous";

    private final RateLimitProperties properties;
    
    public PlatformRateLimitKeyGenerator(RateLimitProperties properties){
        this.properties = properties;
    }

    @Override
    public String key(final HttpServletRequest request, final Route route, final RateLimitProperties.Policy policy) {
        final List<Type> types = policy.getType();
        final StringJoiner joiner = new StringJoiner(":");
        joiner.add(properties.getKeyPrefix());
        joiner.add(route.getId());
        if (!types.isEmpty()) {
            if (types.contains(Type.URL)) {
                joiner.add(route.getPath());
            }
            if (types.contains(Type.ORIGIN)) {
                joiner.add(getRemoteAddr(request));
            }
            if (types.contains(Type.USER)) {
                RequestContext ctx = RequestContext.getCurrentContext();
                IUserDetail user = (IUserDetail)ctx.get(FilterConstant.REQUEST_USER_INFO);
                String idUser;
                if (null == user || StringUtils.isBlank(user.userId())){
                    idUser = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : ANONYMOUS_USER;
                }else{
                    idUser = user.userId();
                }
                joiner.add(idUser);
            }
        }
        return joiner.toString();
    }

    private String getRemoteAddr(final HttpServletRequest request) {
        if (properties.isBehindProxy() && request.getHeader(HttpHeaders.X_FORWARDED_FOR) != null) {
            return request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        }
        return request.getRemoteAddr();
    }
}
