package com.platform.api.gate.filter.pre;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.api.gate.exception.InvalidTokenException;
import com.platform.api.gate.filter.FilterConstant;
import com.platform.api.gate.service.TokenServiceImpl;
import com.platform.auth.common.exception.JwtIllegalArgumentException;
import com.platform.auth.common.exception.JwtSignatureException;
import com.platform.auth.common.exception.JwtTokenExpiredException;
import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.common.web.filter.UserRequestTraceFilter;
import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.gate.zuul.filter.pre.BasePreFilter;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证token过滤器
 * @version: 1.0
 */
@Slf4j
@Component
public class ValidateUserTokenFilter extends BasePreFilter {

    public ValidateUserTokenFilter() {
        super(FilterConstant.USER_CHECK);
    }

    public ValidateUserTokenFilter(int filterOrder) {
        super(filterOrder);
    }

    /**
     * token请求头
     */
    @Value("${gate.jwt.header:token}")
    private String tokenHeader;

    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public Object run(){
        // 请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 清除日志用户id
        MDC.remove(UserRequestTraceFilter.USER_ID);

        // 带了token,判断token是否过期
        String accessToken = ctx.getRequest().getHeader(this.tokenHeader);
        UserDetail userInfo = null;
        if(StringUtils.isNotBlank(accessToken)){
            userInfo = parseTokenUserInfo(accessToken);
        }

        if(null != userInfo){
            // 日志用户id
            MDC.put(UserRequestTraceFilter.USER_ID, userInfo.getIdUser());
            String authorization = userDetailService.encodeUser(userInfo);
            // 设置头部校验信息
            ctx.addZuulRequestHeader(RequestAttributeConst.AUTHORIZATION_HEADER, authorization);
            ctx.addZuulRequestHeader("client-version", "1.0");
            // 保存用户信息, 供后续过滤器使用
            ctx.set(FilterConstant.REQUEST_USER_INFO, userInfo);
        }else{
            // 是否开放的地址
            boolean isOpenUrl = Boolean.TRUE.equals(ctx.get(FilterConstant.REQUEST_IS_OPEN_URL));
            if(!isOpenUrl){
                log.info("token无效");
                // 抛出token无效异常
                throw new ZuulRuntimeException(new ZuulException(new InvalidTokenException(), "TOKEN 无效", HttpStatus.FORBIDDEN.value(), "TOKEN 无效"));
            }else{
                if(log.isDebugEnabled()){
                    log.debug("请求地址为开放地址,允许访问");
                }
            }
        }

        return null;
    }

    /**
     * 解析token
     * @version: 1.0
     * @param accessToken
     * @return
     */
    private UserDetail parseTokenUserInfo(String accessToken){
        UserDetail userInfo = null;
        try{
            userInfo = tokenService.parseTokenUserInfo(accessToken);
        }catch(JwtTokenExpiredException e){
            if(log.isDebugEnabled()){
                log.debug("token已过期");
            }
        }catch(JwtSignatureException e){
            if(log.isDebugEnabled()){
                log.debug("token已签名错误:{}");
            }
        }catch(JwtIllegalArgumentException e){
            if(log.isDebugEnabled()){
                log.debug("token参数有误:{}", e.getMessage());
            }
        }catch(JwtException e){
            if(log.isDebugEnabled()){
                log.debug("token异常:{}", e.getMessage());
            }
        }catch(Exception e){
            log.error("验证token异常:" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return userInfo;
    }

}
