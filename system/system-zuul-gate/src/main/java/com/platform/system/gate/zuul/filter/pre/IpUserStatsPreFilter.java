package com.platform.system.gate.zuul.filter.pre;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.zuul.FilterConstant;
import com.platform.system.gate.zuul.biz.IpUserStatsRecorder;
import com.platform.system.gate.zuul.properties.IpUserStatsProperties;
import com.platform.system.gate.zuul.properties.IpUserStatsProperties.Policy;

/**
 * IP用户统计
 * @version: 1.0
 */
public class IpUserStatsPreFilter extends BasePreFilter {

    private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();
    
    private static final String ANONYMOUS_USER = "anonymous";

    private final IpUserStatsProperties properties;

    private final RouteLocator routeLocator;

    private final IpUserStatsRecorder recorder;
    
    public IpUserStatsPreFilter(int filterOrder, IpUserStatsProperties properties, RouteLocator routeLocator, IpUserStatsRecorder recorder) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
        this.recorder = recorder;
    }

    private Optional<IpUserStatsProperties.Policy> policy(final Route route) {
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }
    
    @Override
    public boolean shouldFilter() {
        boolean isEnable = properties.isEnabled();
        if (isEnable){
            Route route = route();
            Optional<Policy> policy = policy(route);
            if (policy.isPresent()){
                isEnable = policy.get().isEnabled();
            }
        }

        return isEnable;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        final String path = request.getRequestURI();
        final Route route = route();
        policy(route).ifPresent(policy -> {
            UserDetail userInfo = (UserDetail)ctx.get(FilterConstant.REQUEST_USER_INFO);
            String idUser;
            if (null == userInfo || StringUtils.isBlank(userInfo.getIdUser())){
                idUser = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : ANONYMOUS_USER;
            }else{
                idUser = userInfo.getIdUser();
            }
            recorder.record(path, WebUtil.getIpAddress(request), idUser);
        });
        return null;
    }

    private Route route() {
        String requestURI = URL_PATH_HELPER.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
        return routeLocator.getMatchingRoute(requestURI);
    }


}
