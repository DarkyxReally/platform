package com.platform.auth.bs.configuration;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.platform.system.common.mybatis.MybatisMapperDynamicLoader;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;


/**
 * mybatis DAO 扫描配置类
 * 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
@Slf4j
public class MyBatisMapperScannerConfig implements EnvironmentAware{
    
    private RelaxedPropertyResolver propertyResolver;

    private String basePackage;
    
    private String mapperLocations;
    
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(basePackage);
        return mapperScannerConfigurer;
    }
    
    /**
     * 开发环境时启动MAPPER动态加载 
     * @return
     */
    @Bean
    @Profile("dev")  
    public MybatisMapperDynamicLoader mybatisMapperDynamicLoader(){
        MybatisMapperDynamicLoader dynamicLoader = new MybatisMapperDynamicLoader();
        dynamicLoader.setMapperLocations(mapperLocations);
        return dynamicLoader;
    }

    @Override
    public void setEnvironment(Environment environment){
    	log.info("=========> environment：{}",environment);
        propertyResolver = new RelaxedPropertyResolver(environment);
        basePackage = propertyResolver.getProperty("mybatis.base_package");
        mapperLocations = propertyResolver.getProperty("mybatis.mapper_locations");
        log.info("=========> basePackage：{}",basePackage);
        log.info("=========> mapperLocations：{}",mapperLocations);
    }
}