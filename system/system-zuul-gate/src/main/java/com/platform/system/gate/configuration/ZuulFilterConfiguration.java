package com.platform.system.gate.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.platform.system.gate.service.IApiSignRequestParamService;
import com.platform.system.gate.service.impl.ApiSignRequestParamServiceImpl;
import com.platform.system.gate.zuul.FilterOrderConstant;
import com.platform.system.gate.zuul.biz.IpUserStatsRecorder;
import com.platform.system.gate.zuul.biz.PlatformRateLimitKeyGenerator;
import com.platform.system.gate.zuul.biz.RequestStatsRecorder;
import com.platform.system.gate.zuul.filter.error.post.DefaultPostErrorFilter;
import com.platform.system.gate.zuul.filter.error.post.ResponseDataEncryptPostErrorFilter;
import com.platform.system.gate.zuul.filter.error.post.ResponseDataSignPostErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.DefaultPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.HeaderValueCheckPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.InvalidSignPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.IpLimitPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.MissParamsPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.ParamsPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.RateLimitPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.RequestDataDecryptPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.pre.VersionLimitPreErrorFilter;
import com.platform.system.gate.zuul.filter.error.route.DefaultRouteErrorFilter;
import com.platform.system.gate.zuul.filter.post.RequestTimeLogPostFilter;
import com.platform.system.gate.zuul.filter.post.ResponseDataEncryptPostFilter;
import com.platform.system.gate.zuul.filter.post.ResponseDataSignPostFilter;
import com.platform.system.gate.zuul.filter.pre.HeaderValueCheckPreFilter;
import com.platform.system.gate.zuul.filter.pre.IpLimitPreFilter;
import com.platform.system.gate.zuul.filter.pre.IpUserStatsPreFilter;
import com.platform.system.gate.zuul.filter.pre.RequestDataDecryptPreFilter;
import com.platform.system.gate.zuul.filter.pre.RequestDataSignCheckPreFilter;
import com.platform.system.gate.zuul.filter.pre.RequestLogPreFilter;
import com.platform.system.gate.zuul.filter.pre.RequestStatsPreFilter;
import com.platform.system.gate.zuul.filter.pre.RequestTimeLogPreFilter;
import com.platform.system.gate.zuul.filter.pre.TracePreFilter;
import com.platform.system.gate.zuul.filter.pre.UnifiedSignCheckPreFilter;
import com.platform.system.gate.zuul.filter.pre.VersionLimitCheckPreFilter;
import com.platform.system.gate.zuul.properties.HeaderValueCheckProperties;
import com.platform.system.gate.zuul.properties.IpLimitProperties;
import com.platform.system.gate.zuul.properties.IpUserStatsProperties;
import com.platform.system.gate.zuul.properties.RequestDataDecryptProperties;
import com.platform.system.gate.zuul.properties.RequestDataSignCheckProperties;
import com.platform.system.gate.zuul.properties.RequestLogProperties;
import com.platform.system.gate.zuul.properties.RequestStatsProperties;
import com.platform.system.gate.zuul.properties.RequestTimeStatsProperties;
import com.platform.system.gate.zuul.properties.ResponseDataEncryptProperties;
import com.platform.system.gate.zuul.properties.ResponseDataSignProperties;
import com.platform.system.gate.zuul.properties.TraceProperties;
import com.platform.system.gate.zuul.properties.UnifiedSignCheckProperties;
import com.platform.system.gate.zuul.properties.VersionLimitProperties;

/** zuul过滤器配置
 * 
 * @version: 1.0
 * */
@Configuration
public class ZuulFilterConfiguration {

    /** Redis配置
     * 
     * @param connectionFactory
     * @return */
    @ConditionalOnClass(RedisTemplate.class)
    public static class RedisConfiguration {

        @Bean
        @Primary
        @ConditionalOnMissingBean
        public StringRedisTemplate stringRedisTemplate(final RedisConnectionFactory connectionFactory) {
            return new StringRedisTemplate(connectionFactory);
        }
    }
    
    
    /**
     * 默认的过滤器
     */
    @Configuration
    public static class DefaultZuulFilter{
        
        /**
         * 默认的前置异常处理
         */
        @Bean
        @ConditionalOnMissingBean(DefaultPreErrorFilter.class)
        public DefaultPreErrorFilter defaultPreErrorFilter(){
            return new DefaultPreErrorFilter(FilterOrderConstant.DEFAULT_PRE_ERROR_ORDER);
        }
        
        /**
         * 默认的路由异常处理
         */
        @Bean
        @ConditionalOnMissingBean(DefaultRouteErrorFilter.class)
        public DefaultRouteErrorFilter defaultRouteErrorFilter(){
            return new DefaultRouteErrorFilter(FilterOrderConstant.DEFAULT_ROUTE_ERROR_ORDER);
        }
        
        /**
         * 默认的后置异常处理
         */
        @Bean
        @ConditionalOnMissingBean(DefaultPostErrorFilter.class)
        public DefaultPostErrorFilter defaultPostErrorFilter(){
            return new DefaultPostErrorFilter(FilterOrderConstant.DEFAULT_POST_ERROR_ORDER);
        }
    }

    /** 请求频率限制器
     * 
     * */
    @ConditionalOnProperty(prefix = "zuul.ratelimit", name = "enabled", havingValue = "true")
    public static class RateLimitAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean(RateLimitPreErrorFilter.class)
        public RateLimitPreErrorFilter rateLimitPreErrorFilter() {
            return new RateLimitPreErrorFilter(FilterOrderConstant.RATE_LIMIT_PRE_ERROR_ORDER);
        }

        /** 频率限制KEY生成器
         * 
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean
        public RateLimitKeyGenerator liguRateLimitKeyGenerator(
                com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties properties) {
            return new PlatformRateLimitKeyGenerator(properties);
        }
    }

    /** 请求时间统计配置
     * 
     * */
    @ConditionalOnProperty(prefix = RequestTimeStatsProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(RequestTimeStatsProperties.class)
    public static class RequestTimeStatConfiguration {

        /** 请求时间统计前置处理过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestTimeLogPreFilter.class)
        public RequestTimeLogPreFilter requestTimeLogPreFilter(RouteLocator routeLocator, RequestTimeStatsProperties properties) {
            return new RequestTimeLogPreFilter(FilterOrderConstant.REQUEST_TIME_STAT_PRE_ORDER, routeLocator, properties);
        }

        /** 请求时间统计后置处理过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestTimeLogPostFilter.class)
        public RequestTimeLogPostFilter requestTimeLogPostFilter(RouteLocator routeLocator, RequestTimeStatsProperties properties) {
            return new RequestTimeLogPostFilter(FilterOrderConstant.REQUEST_TIME_STAT_POST_ORDER, routeLocator, properties);
        }
    }

    /** 响应数据加密配置
     * 
     * */
    @ConditionalOnProperty(prefix = ResponseDataEncryptProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(ResponseDataEncryptProperties.class)
    public static class ResponseDataEncryptConfiguration {

        /** 响应数据加密后置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(ResponseDataEncryptPostFilter.class)
        public ResponseDataEncryptPostFilter responseDataEncryptPostFilter(RouteLocator routeLocator,
                ResponseDataEncryptProperties properties) {
            return new ResponseDataEncryptPostFilter(FilterOrderConstant.RESPONSE_DATA_ENCRYPT_POST_ORDER, routeLocator, properties);
        }

        /** 响应数据加密异常处理后置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(ResponseDataEncryptPostErrorFilter.class)
        public ResponseDataEncryptPostErrorFilter responseDataEncryptPostErrorFilter() {
            return new ResponseDataEncryptPostErrorFilter(FilterOrderConstant.RESPONSE_DATA_ENCRYPT_POST_ERROR_ORDER);
        }
    }

    /** 响应数据签名配置
     * 
     * */
    @ConditionalOnProperty(prefix = ResponseDataSignProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(ResponseDataSignProperties.class)
    public static class ResponseDataSignConfiguration {

        /** 响应数据签名后置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(ResponseDataSignPostFilter.class)
        public ResponseDataSignPostFilter responseDataSignPostFilter(RouteLocator routeLocator, ResponseDataSignProperties properties) {
            return new ResponseDataSignPostFilter(FilterOrderConstant.RESPONSE_DATA_SIGN_POST_ORDER, routeLocator, properties);
        }

        /** 异常处理
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(ResponseDataSignPostErrorFilter.class)
        public ResponseDataSignPostErrorFilter responseDataSignPostErrorFilter(RouteLocator routeLocator, ResponseDataSignProperties properties) {
            return new ResponseDataSignPostErrorFilter(FilterOrderConstant.RESPONSE_DATA_SIGN_POST_ERROR_ORDER);
        }
    }

    /** 请求头值校验过滤器
     * 
     * */
    @ConditionalOnProperty(prefix = HeaderValueCheckProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(HeaderValueCheckProperties.class)
    public static class HeaderValueCheckConfiguration {

        /** 前置过滤器
         * 
         * @param properties
         * @param routeLocator
         * @return */
        @Bean
        @ConditionalOnMissingBean(HeaderValueCheckPreFilter.class)
        public HeaderValueCheckPreFilter headerValueCheckPreFilter(HeaderValueCheckProperties properties, RouteLocator routeLocator) {
            return new HeaderValueCheckPreFilter(FilterOrderConstant.HEADER_VALUE_CHECK_PRE_ORDER, properties, routeLocator);
        }

        /** 异常处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(HeaderValueCheckPreErrorFilter.class)
        public HeaderValueCheckPreErrorFilter headerValueCheckPreErrorFilter() {
            return new HeaderValueCheckPreErrorFilter(FilterOrderConstant.HEADER_VALUE_CHECK_PRE_ERROR_ORDER);
        }
    }

    /** IP限制过滤器
     * 
     * */
    @ConditionalOnProperty(prefix = IpLimitProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(IpLimitProperties.class)
    public static class IpLimitConfiguration {

        /** 前置IP过滤器
         * 
         * @param properties
         * @param routeLocator
         * @return */
        @Bean
        @ConditionalOnMissingBean(IpLimitPreFilter.class)
        public IpLimitPreFilter ipLimitPreFilter(IpLimitProperties properties, RouteLocator routeLocator) {
            return new IpLimitPreFilter(FilterOrderConstant.IP_LIMIT_PRE_ORDER, properties, routeLocator);
        }

        /** IP限制前置异常处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(IpLimitPreErrorFilter.class)
        public IpLimitPreErrorFilter ipLimitPreErrorFilter() {
            return new IpLimitPreErrorFilter(FilterOrderConstant.IP_LIMIT_PRE_ERROR_ORDER);
        }
    }

    /** IP用户统计配置
     * */
    @ConditionalOnProperty(prefix = IpUserStatsProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(IpUserStatsProperties.class)
    public static class IPUserStatsConfiguration {

        @Bean
        @ConditionalOnMissingBean(IpUserStatsRecorder.class)
        public IpUserStatsRecorder requestStatsRecorder(StringRedisTemplate stringRedisTemplate) {
            return new IpUserStatsRecorder(stringRedisTemplate);
        }

        @Bean
        @ConditionalOnMissingBean(IpUserStatsPreFilter.class)
        public IpUserStatsPreFilter requestStatsPreFilter(IpUserStatsProperties properties, RouteLocator routeLocator,
                IpUserStatsRecorder recorder) {
            return new IpUserStatsPreFilter(FilterOrderConstant.IP_USER_STAT_PRE_ORDER, properties, routeLocator, recorder);
        }
    }

    /** 请求报文解密配置
     * */
    @ConditionalOnProperty(prefix = RequestDataDecryptProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(RequestDataDecryptProperties.class)
    public static class RequestDataDecryptConfiguration {

        /** 解密处理前置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestDataDecryptPreFilter.class)
        public RequestDataDecryptPreFilter requestDataDecryptPreFilter(RouteLocator routeLocator, RequestDataDecryptProperties properties) {
            return new RequestDataDecryptPreFilter(FilterOrderConstant.REQUEST_DATA_DECRYPT_PRE_ORDER, routeLocator, properties);
        }

        /** 解密异常处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestDataDecryptPreErrorFilter.class)
        public RequestDataDecryptPreErrorFilter requestDataDecryptPreErrorFilter() {
            return new RequestDataDecryptPreErrorFilter(FilterOrderConstant.REQUEST_DATA_DECRYPT_PRE_ERROR_ORDER);
        }
    }

    /** 请求报文签名校验配置
     * 
     * */
    @ConditionalOnProperty(prefix = RequestDataSignCheckProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(RequestDataSignCheckProperties.class)
    public static class RequestDataSignCheckConfiguration {

        /** 签名校验处理前置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestDataSignCheckPreFilter.class)
        public RequestDataSignCheckPreFilter requestDataSignCheckPreFilter(RouteLocator routeLocator, RequestDataSignCheckProperties properties) {
            return new RequestDataSignCheckPreFilter(FilterOrderConstant.REQUEST_DATA_SIGN_CHECK_PRE_ORDER, routeLocator, properties);
        }

        /** 签名错误处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(InvalidSignPreErrorFilter.class)
        public InvalidSignPreErrorFilter invalidSignPreErrorFilter() {
            return new InvalidSignPreErrorFilter(FilterOrderConstant.INVALID_SIGN_PRE_ERROR_ORDER);
        }
    }

    /** 请求日志记录配置
     * */
    @ConditionalOnProperty(prefix = RequestLogProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(RequestLogProperties.class)
    public static class RequestLogConfiguration {

        /** 请求日志记录
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(RequestLogPreFilter.class)
        public RequestLogPreFilter requestLogPreFilter(RouteLocator routeLocator, RequestLogProperties properties) {
            return new RequestLogPreFilter(FilterOrderConstant.REQUEST_INFO_LOG_PRE_ORDER, properties, routeLocator);
        }
    }

    /** 请求朔源配置
     * 
     * */
    @ConditionalOnProperty(prefix = TraceProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(TraceProperties.class)
    public static class TraceConfiguration {

        /** 请求朔源过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(TracePreFilter.class)
        public TracePreFilter requestTraceFilter(RouteLocator routeLocator, TraceProperties properties) {
            return new TracePreFilter(FilterOrderConstant.TRACE_PRE_ORDER, properties, routeLocator);
        }
    }

    /** 统一签名校验过滤器
     * 
     * */
    @ConditionalOnProperty(prefix = UnifiedSignCheckProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(UnifiedSignCheckProperties.class)
    public static class UnifiedSignCheckConfiguration {

        /** 获取签名参数接口
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(IApiSignRequestParamService.class)
        public IApiSignRequestParamService apiSignRequestParamService() {
            return new ApiSignRequestParamServiceImpl();
        }

        /** 统一签名校验前置过滤器
         * 
         * @param properties
         * @param routeLocator
         * @param stringRedisTemplate
         * @param apiSignRequestParamService
         * @return */
        @Bean
        @ConditionalOnMissingBean(UnifiedSignCheckPreFilter.class)
        public UnifiedSignCheckPreFilter unifiedSignCheckPreFilter(UnifiedSignCheckProperties properties, RouteLocator routeLocator,
                StringRedisTemplate stringRedisTemplate,
                IApiSignRequestParamService apiSignRequestParamService) {
            return new UnifiedSignCheckPreFilter(FilterOrderConstant.UNIFIED_SIGN_CHECK_PRE_ORDER, properties, routeLocator, stringRedisTemplate,
                    apiSignRequestParamService);
        }

        /** 签名错误处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(InvalidSignPreErrorFilter.class)
        public InvalidSignPreErrorFilter invalidSignPreErrorFilter() {
            return new InvalidSignPreErrorFilter(FilterOrderConstant.INVALID_SIGN_PRE_ERROR_ORDER);
        }

        /** 缺少参数处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(MissParamsPreErrorFilter.class)
        public MissParamsPreErrorFilter missParamsPreErrorFilter() {
            return new MissParamsPreErrorFilter(FilterOrderConstant.MISS_PARAMS_PRE_ERROR_ORDER);
        }

        /** 参数格式错误处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(ParamsPreErrorFilter.class)
        public ParamsPreErrorFilter paramsPreErrorFilter() {
            return new ParamsPreErrorFilter(FilterOrderConstant.PARAMS_ERROR_PRE_ERROR_ORDER);
        }
    }

    /** 请求统计配置
     * 
     * */
    @ConditionalOnProperty(prefix = RequestStatsProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(RequestStatsProperties.class)
    public static class RequestStatsConfiguration {

        @Bean
        @ConditionalOnMissingBean(RequestStatsRecorder.class)
        public RequestStatsRecorder requestStatsRecorder(StringRedisTemplate stringRedisTemplate) {
            return new RequestStatsRecorder(stringRedisTemplate);
        }

        @Bean
        @ConditionalOnMissingBean(RequestStatsPreFilter.class)
        public RequestStatsPreFilter requestStatsPreFilter(RequestStatsProperties properties, RouteLocator routeLocator,
                RequestStatsRecorder recorder) {
            return new RequestStatsPreFilter(FilterOrderConstant.REQUEST_STAT_PER_ORDER, properties, routeLocator, recorder);
        }
    }

    /** 版本限制配置
     * 
     * */
    @ConditionalOnProperty(prefix = VersionLimitProperties.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties(VersionLimitProperties.class)
    public static class VersionLimitConfiguration {

        /** 版本限制处理前置过滤器
         * 
         * @param routeLocator
         * @param properties
         * @return */
        @Bean
        @ConditionalOnMissingBean(VersionLimitCheckPreFilter.class)
        public VersionLimitCheckPreFilter versionLimitCheckPreFilter(RouteLocator routeLocator, VersionLimitProperties properties) {
            return new VersionLimitCheckPreFilter(FilterOrderConstant.VERSION_LIMIT_PRE_ORDER, properties, routeLocator);
        }

        /** 版本异常处理
         * 
         * @return */
        @Bean
        @ConditionalOnMissingBean(VersionLimitPreErrorFilter.class)
        public VersionLimitPreErrorFilter versionLimitPreErrorFilter() {
            return new VersionLimitPreErrorFilter(FilterOrderConstant.VERSION_LIMIT_PRE_ERROR_ORDER);
        }
    }

}
