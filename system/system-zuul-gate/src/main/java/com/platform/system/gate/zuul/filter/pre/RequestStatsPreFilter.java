package com.platform.system.gate.zuul.filter.pre;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.zuul.FilterConstant;
import com.platform.system.gate.zuul.biz.RequestStatsRecorder;
import com.platform.system.gate.zuul.properties.RequestStatsProperties;
import com.platform.system.gate.zuul.properties.RequestStatsProperties.Policy.Type;

/**
 * 请求统计过滤器
 * @version: 1.0
 */
public class RequestStatsPreFilter extends BasePreFilter {

	private final RequestStatsRecorder recorder;

	private final RequestStatsProperties properties;

	private final RouteLocator routeLocator;

	private static final String ANONYMOUS_USER = "anonymous";

	public RequestStatsPreFilter(int filterOrder, RequestStatsProperties properties, RouteLocator routeLocator, RequestStatsRecorder recorder) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
        this.recorder = recorder;
    }
	
	@Override
	public boolean shouldFilter() {
		boolean isEnable = properties.isEnabled();
		if (isEnable){
		    Route route = route();
		    Optional<RequestStatsProperties.Policy> policy = policy(route);
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
			final List<Type> types = policy.getType();
			if (!types.isEmpty()) {
				if (types.contains(Type.URL)) {
					recorder.recordUrl(path);
				}
				if (types.contains(Type.ORIGIN)) {
					recorder.recordIp(path, WebUtil.getIpAddress(request));
				}
				if (types.contains(Type.USER)) {
				    UserDetail userInfo = (UserDetail)ctx.get(FilterConstant.REQUEST_USER_INFO);
					String idUser;
					if (null == userInfo || StringUtils.isBlank(userInfo.getIdUser())){
						idUser = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : ANONYMOUS_USER;
					}else{
						idUser = userInfo.getIdUser();
					}
					recorder.recordUser(path, idUser);
				}
			}
		});
		return null;
	}

	private Route route() {
	    String requestURI = getRequestUri(RequestContext.getCurrentContext().getRequest());
		return routeLocator.getMatchingRoute(requestURI);
	}

	private Optional<RequestStatsProperties.Policy> policy(final Route route) {
		if (route != null) {
			return properties.getPolicy(route.getId());
		}
		return Optional.empty();
	}
}
