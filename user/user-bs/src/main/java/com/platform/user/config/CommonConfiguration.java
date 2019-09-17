package com.platform.user.config;

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
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.util.RedisService;
import com.platform.system.common.web.converter.XssFastJsonHttpMessageConverter;

/**
 * 公共配置
 */
@Configuration
public class CommonConfiguration {

    /**
     * 使用hibernate校验框架
     * @return
     */
    @Bean
    public ValidatorFactory validatorFactoryBean(){
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
        //JSON输出 采用阿里巴巴fastjson
        XssFastJsonHttpMessageConverter fastjsonConverter = new XssFastJsonHttpMessageConverter();
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
        return new RedisService();
    }
}
