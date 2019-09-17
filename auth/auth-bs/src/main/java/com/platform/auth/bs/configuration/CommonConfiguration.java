package com.platform.auth.bs.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;
import com.platform.system.common.config.WechatConfig;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.feign.RequestTraceInterceptor;
import com.platform.system.common.util.RedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * 公共配置
 */
@Configuration
@Slf4j
public class CommonConfiguration {

    /**
     * 使用hibernate校验框架
     * @return
     */
    @Bean
    public ValidatorFactory validatorFactoryBean(){
    	log.info("===========> 使用hibernate校验框架");
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        return factoryBean;
    }
    
    /**
     * http消息转换器
     * @return
     */
    @Bean
    public HttpMessageConverters httpMessageConverters(){
    	log.info("===========> http消息转换器");
        //JSON输出 采用阿里巴巴fastjson
        FastJsonHttpMessageConverter fastjsonConverter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastjsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig config = new FastJsonConfig();  
        config.setCharset(CommonConstants.UTF_8);
        
       /* DisableCircularReferenceDetect——防止循环嵌套 
        QuoteFieldNames——输出key时是否使用双引号,默认为true 
        WriteMapNullValue——是否输出值为null的字段,默认为false 
        WriteNullNumberAsZero——数值字段如果为null,输出为0,而非null
        WriteNullListAsEmpty——List字段如果为null,输出为[],而非null
        WriteNullStringAsEmpty——字符类型字段如果为null,输出为”“,而非null  
        WriteNullBooleanAsFalse——Boolean字段如果为null,输出为false,而非null
        WriteDateUseDateFormat——Date的日期转换器 
        SerializerFeature.PrettyFormat ——格式化输出*/
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.QuoteFieldNames, SerializerFeature.DisableCircularReferenceDetect, 
//                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteEnumUsingToString);  
        fastjsonConverter.setFastJsonConfig(config);  

        return new HttpMessageConverters(fastjsonConverter);  
    }
    
    /**
     * redisService
     * @return
     */
    @Bean
    public RedisService redisService(){
    	log.info("===========> redisService");
        return new RedisService();
    }
    
    /**
     * feign请求拦截器
     * 增加每次请求的唯一请求ID
     * @return
     */
    @Bean
    public RequestTraceInterceptor feignRequestInterceptor(){
    	log.info("===========> feign请求拦截器");
        return new RequestTraceInterceptor();
    }
    
    /**
     * 用户认证相关配置
     * @return
     */
    @Bean
    public AuthUserConfig authUserConfig(){
    	log.info("===========>  用户认证相关配置");
        return new AuthUserConfig();
    }
    /**
     * 客户端认证相关配置
     * @return
     */
    @Bean
    public AuthClientConfig authClientConfig(){
    	log.info("===========>   客户端认证相关配置");
        return new AuthClientConfig();
    }

    /**
     * 微信小程序相关配置
     * @return
     */
    @Bean
    public WechatConfig wechatConfig(){
        return new WechatConfig();
    }
    
//    @Bean
//    public RabbitMQConfig rabbitMQConfig(){
//        return new RabbitMQConfig();
//    }

//    @Bean
//    public HandlerRegister3rd handlerRegister3rd(){
//        return new HandlerRegister3rd();
//    }

}
