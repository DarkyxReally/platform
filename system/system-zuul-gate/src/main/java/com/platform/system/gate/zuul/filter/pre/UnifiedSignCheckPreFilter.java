package com.platform.system.gate.zuul.filter.pre;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.util.PlatformSignature;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.exception.DuplicateRequestException;
import com.platform.system.gate.exception.InvalidSignException;
import com.platform.system.gate.exception.IpLimitException;
import com.platform.system.gate.exception.ParamsErrorException;
import com.platform.system.gate.exception.ParamsMissException;
import com.platform.system.gate.exception.ReplayAttackstException;
import com.platform.system.gate.service.IApiSignRequestParamService;
import com.platform.system.gate.zuul.properties.UnifiedSignCheckProperties;
import com.platform.system.gate.zuul.properties.UnifiedSignCheckProperties.Policy;


/**
 * 统一签名校验过滤器
 * @version: 1.0
 */
@Slf4j
public class UnifiedSignCheckPreFilter extends BasePreFilter {
    /**
     * 随机字符串最大长度
     */
    private int maxNonceStrLength = 64;
    private final UnifiedSignCheckProperties properties;
    private final RouteLocator routeLocator;
    private final StringRedisTemplate stringRedisTemplate;

    private final IApiSignRequestParamService apiSignRequestParamService;

    public UnifiedSignCheckPreFilter(int filterOrder, UnifiedSignCheckProperties properties, RouteLocator routeLocator,
            StringRedisTemplate stringRedisTemplate, IApiSignRequestParamService apiSignRequestParamService) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
        this.stringRedisTemplate = stringRedisTemplate;
        this.apiSignRequestParamService = apiSignRequestParamService;
    }

    @Override
    public boolean shouldFilter(){
        if (!properties.isEnabled()){
            if (log.isDebugEnabled()){
                log.debug("未开启统一签名验证功能");
            }
            return false;
        }
        if (properties.isEnableAll()){
            // 所有接口都校验
            return true;
        }
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Route route = route(request);
        Optional<UnifiedSignCheckProperties.Policy> policy = policy(route);
        if (policy.isPresent()){
            boolean enabled = policy.get().isEnabled();
            if (!enabled){
                if (log.isDebugEnabled()){
                    log.debug("路由:{},未开启统一签名验证功能", route.getId());
                }
            }else{
                return true;
            }
        }
        return false;
    }
    
    private Route route(HttpServletRequest request){
        return routeLocator.getMatchingRoute(getRequestUri(request));
    }

    private Optional<UnifiedSignCheckProperties.Policy> policy(final Route route){
        if(route != null){
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }

    /**
     * 校验逻辑:
     * - 1:路由配置了签名校验
     * - 2:接口KEY是否配置了IP限制
     * - 1和2同时校验通过时允许访问
     * 
     * @return
     */
    @Override
    public Object run(){
        if (log.isDebugEnabled()){
            log.debug("开始进行统一签名校验");
        }
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        final Route route = route(request);
        String ipAddress = WebUtil.getIpAddress(request);
        Map<String, String> params = apiSignRequestParamService.getParams(request);
        policy(route).ifPresent(policy -> {
            try{
                // 密钥对
                Map<String, String> keyMap = policy.getKeyMap();
                // KEY ip限制
                Map<String, List<String>> keyIpMap = policy.getKeyIpMap();
                // 开始校验
                check(keyMap, keyIpMap, params, ipAddress, policy);
                log.info("签名校验通过");
            }catch(ParamsMissException e){
                throw new ZuulRuntimeException(new ZuulException(e, "参数有误", HttpStatus.OK.value(), "缺少参数"));
            }catch(ParamsErrorException e){
                throw new ZuulRuntimeException(new ZuulException(e, "参数有误", HttpStatus.OK.value(), "参数格式不正确"));
            }catch(InvalidSignException e){
                throw new ZuulRuntimeException(new ZuulException(e, "无效请求", HttpStatus.OK.value(), "签名簿正确"));
            }catch(IpLimitException e){
                throw new ZuulRuntimeException(new ZuulException(e, "无效请求", HttpStatus.OK.value(), "IP地址无效"));
            }catch(DuplicateRequestException e){
                throw new ZuulRuntimeException(new ZuulException(e, "无效请求", HttpStatus.OK.value(), "请求重复"));
            }catch(ReplayAttackstException e){
                throw new ZuulRuntimeException(new ZuulException(e, "无效请求", HttpStatus.OK.value(), "请检测系统时间差"));
            }
        });
        return null;
    }

    /**
     * 开始校验
     * 
     * @param ctx
     * @param params
     * @param ipAddress
     * @param policy
     * @return 本次请求使用的apiKey
     */
    private String check(Map<String, String> keyMap, Map<String, List<String>> keyIpMap, Map<String, String> params, String ipAddress,
            Policy policy){
        if(null == params || params.isEmpty()){
            log.info("没有请求参数");
            throw new ParamsMissException();
        }
        // 时间戳
        String timestamp = params.get("timestamp");
        if(StringUtils.isBlank(timestamp)){
            log.info("请求参数缺少时间戳");
            throw new ParamsMissException();
        }
        long reqTimeStamp = 0;
        try{
            reqTimeStamp = Long.parseLong(timestamp);
        }catch(Exception e){
            log.info("请求参数时间戳格式不正确");
            throw new ParamsErrorException();
        }
        // 随机字符串
        String nonceStr = params.get("nonceStr");
        if(StringUtils.isBlank(nonceStr)){
            log.info("请求参数缺少接口随机字符串");
            throw new ParamsMissException();
        }
        if(nonceStr.length() > maxNonceStrLength){
            log.info("随机字符串太长");
            throw new ParamsErrorException();
        }

        // 接口KEY
        String apiKey = params.get("apiKey");
        if(StringUtils.isBlank(apiKey)){
            log.info("请求参数缺少接口Key");
            throw new ParamsMissException();
        }

        if(!keyMap.containsKey(apiKey)){
            log.warn("接口key:{}无效", apiKey);
            // apiKey与配置的不一致,直接报签名无效
            throw new InvalidSignException();
        }
        String apiSecret = keyMap.get(apiKey);
        if(StringUtils.isBlank(apiSecret)){
            // 密钥为空,配置错误
            log.error("接口key密钥配置存在密钥配置为空的数据,请检查数据");
            throw new InvalidSignException();
        }
        List<String> list = keyIpMap.get(apiKey);
        if(CollectionUtils.isNotEmpty(list)){
            if(!list.contains(ipAddress)){
                log.warn("接口KEY:{},对应的请求的IP地址:{}无效", apiKey, ipAddress);
                throw new IpLimitException();
            }
        }

        // 检查是否重放攻击
        checkReplayAttacks(reqTimeStamp);

        // 判断是否重复请求
        checkDuplicateRequest(apiKey, nonceStr);

        if(!PlatformSignature.checkSign(params, apiSecret)){
            log.info("签名校验不通过, 参数:{}", JsonUtil.toJSONString(params));
            // 签名无效
            throw new InvalidSignException();
        }

        return apiKey;
    }


    /**
     * 判断是否重复请求
     * 
     * @param apiKey
     * @param nonceStr
     * @return
     */
    private void checkDuplicateRequest(String apiKey, String nonceStr){
        long timestampExpired = getTimestampExpired();
        String cacheKey = "GATE:SIGN:SECRET:NONCESTR:" + apiKey + ":" + nonceStr;
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(cacheKey))){
            // 存在key, 即为重复请求
            throw new DuplicateRequestException();
        }

        BoundValueOperations<String, String> valueOps = stringRedisTemplate.boundValueOps(cacheKey);
        Boolean ifAbsent = valueOps.setIfAbsent("1");
        if(!Boolean.TRUE.equals(ifAbsent)){
            // 赋值失败,说明其他地方已赋值过, 即为重复请求
            throw new DuplicateRequestException();
        }

        if(valueOps.getExpire() <= 0){
            // 过期时间
            valueOps.expire(timestampExpired, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 获取时间戳过期时间长度
     * 
     * @return
     */
    private long getTimestampExpired(){
        return properties.getExpired();
    }

    /**
     * 检查是否重放攻击
     * 
     * @param timestamp
     */
    private void checkReplayAttacks(long timestamp){
        // 当前系统时间戳-请求上来的时间戳
        if((PlatformSignature.getTimeStamp() - timestamp) > getTimestampExpired()){
            throw new ReplayAttackstException();
        }
    }

    public void setMaxNonceStrLength(int maxNonceStrLength){
        this.maxNonceStrLength = maxNonceStrLength;
    }

}
