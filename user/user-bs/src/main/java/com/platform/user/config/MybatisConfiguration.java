package com.platform.user.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.platform.system.common.config.properties.DataSourceProperties;
import com.platform.system.common.config.properties.MybatisProperties;

/**
 * mybatis 配置数据源类
 * 
 * 
 * 获取配置的变量
 * 方法1: 
 * 凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment 可以在工程启动时，通过RelaxedPropertyResolver 获取到系统环境变量和application配置文件中的变量。
 * 示例： private RelaxedPropertyResolver propertyResolver;
 *     在重写方法里面
 *     @Override
 *     public void setEnvironment(Environment env) {
 *          env.getProperty("JAVA_HOME"); //获取系统环境变量
 *          propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource."); //获取配置文件中的变量
            String url = propertyResolver.getProperty("url"); 
 *     } 
 * 方法2:
 *   可以通过@ConfigurationProperties 配置application属性配置文件中的相关属性。
 *   然后通过@EnableConfigurationProperties 注解,激活该配置相关属性(相关属性映射到实体中), 并将该配置文件注入到MybatisConfiguration中。
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({DataSourceProperties.class, MybatisProperties.class})
@Slf4j
public class MybatisConfiguration {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private MybatisProperties mybatisProperties;

    /**
     * mybatis 拦截器
     */
    @Autowired(required = false)
    private Interceptor[] mybatisInterceptors;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Bean(destroyMethod = "close")
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName(dataSourceProperties.getName());
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        String driveClassName = dataSourceProperties.getDriverClassName();
        druidDataSource.setDriverClassName(StringUtils.isNotBlank(driveClassName) ? driveClassName
                : "com.mysql.jdbc.Driver");
        String maxActive = dataSourceProperties.getMaxActive();
        druidDataSource.setMaxActive(StringUtils.isNotBlank(maxActive) ? Integer.parseInt(maxActive) : 10);
        String initialSize = dataSourceProperties.getInitialSize();
        druidDataSource.setInitialSize(StringUtils.isNotBlank(initialSize) ? Integer.parseInt(initialSize) : 1);
        String maxWait = dataSourceProperties.getMaxWait();
        druidDataSource.setMaxWait(StringUtils.isNotBlank(maxWait) ? Integer.parseInt(maxWait) : 60000);
        String minIdle = dataSourceProperties.getMinIdle();
        druidDataSource.setMinIdle(StringUtils.isNotBlank(minIdle) ? Integer.parseInt(minIdle) : 3);
        String timeBetweenEvictionRunsMillis = dataSourceProperties.getTimeBetweenEvictionRunsMillis();
        druidDataSource
                .setTimeBetweenEvictionRunsMillis(StringUtils.isNotBlank(timeBetweenEvictionRunsMillis) ? Integer
                        .parseInt(timeBetweenEvictionRunsMillis) : 60000);
        String minEvictableIdleTimeMillis = dataSourceProperties.getMinEvictableIdleTimeMillis();
        druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.isNotBlank(minEvictableIdleTimeMillis) ? Integer
                .parseInt(minEvictableIdleTimeMillis) : 300000);
        String validationQuery = dataSourceProperties.getValidationQuery();
        druidDataSource.setValidationQuery(StringUtils.isNotBlank(validationQuery) ? validationQuery : "select 'x'");
        String testWhileIdle = dataSourceProperties.getTestWhileIdle();
        druidDataSource.setTestWhileIdle(StringUtils.isNotBlank(testWhileIdle) ? Boolean.parseBoolean(testWhileIdle)
                : true);
        String testOnBorrow = dataSourceProperties.getTestOnBorrow();
        druidDataSource.setTestOnBorrow(StringUtils.isNotBlank(testOnBorrow) ? Boolean.parseBoolean(testOnBorrow)
                : false);
        String testOnReturn = dataSourceProperties.getTestOnReturn();
        druidDataSource.setTestOnReturn(StringUtils.isNotBlank(testOnReturn) ? Boolean.parseBoolean(testOnReturn)
                : false);
        String poolPreparedStatements = dataSourceProperties.getPoolPreparedStatements();
        druidDataSource.setPoolPreparedStatements(StringUtils.isNotBlank(poolPreparedStatements) ? Boolean
                .parseBoolean(poolPreparedStatements) : true);
        String maxOpenPreparedStatements = dataSourceProperties.getMaxOpenPreparedStatements();
        druidDataSource.setMaxOpenPreparedStatements(StringUtils.isNotBlank(maxOpenPreparedStatements) ? Integer
                .parseInt(maxOpenPreparedStatements) : 20);

        List<Filter> proxyFilters = new ArrayList<Filter>();
        proxyFilters.add(statFilter());
        proxyFilters.add(slf4jLogFilter());

        druidDataSource.setProxyFilters(proxyFilters);
        String filters = dataSourceProperties.getFilters();
        try{
            druidDataSource.setFilters(StringUtils.isNotBlank(filters) ? filters : "stat,wall,slf4j");
        }catch(SQLException e){
            log.error(e.getMessage(), e);
        }
        return druidDataSource;
    }

    /**
     * 统计SQL
     * @return
     */
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        // 超过1秒就算慢sql
        statFilter.setSlowSqlMillis(dataSourceProperties.getSlowSqlTimeMillis());
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        return statFilter;
    }


    /**
     * 日志filter
     * @author: 研发部-黄泽建
     * @return
     */
    @Bean
    public Slf4jLogFilter slf4jLogFilter(){
        Slf4jLogFilter filter = new Slf4jLogFilter();
        // 不记录结果
        filter.setResultSetLogEnabled(false);
        // 不记录连接信息
        filter.setConnectionLogEnabled(false);
        filter.setStatementParameterClearLogEnable(false);
        filter.setStatementCreateAfterLogEnabled(false);
        filter.setStatementCloseAfterLogEnabled(false);
        filter.setStatementPrepareAfterLogEnabled(false);
        // 打印SQL参数
        filter.setStatementParameterSetLogEnabled(true);
        // 打印可执行的SQL
        filter.setStatementExecutableSqlLogEnable(true);
        // 记录了异常SQL
        filter.setStatementLogErrorEnabled(true);
        // 格式化输出SQL
        filter.setStatementSqlPrettyFormat(true);
        return filter;
    }

    /**
     * SqlSessionFactory
     * @author: 研发部-黄泽建
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if(StringUtils.isNotBlank(this.mybatisProperties.getConfig())){
            Resource resource = this.resourceLoader.getResource(this.mybatisProperties.getConfig());
            if(resource.exists()){
                factory.setConfigLocation(resource);
            }else{
                log.debug("mybatis 配置文件不存在. 路径:{}", this.mybatisProperties.getConfig());
                if(this.mybatisProperties.isCheckConfigLocation()){ throw new IllegalStateException("mybatis 配置文件不存在"); }
            }
        }
        // 系统中注册的mybatis插件
        if(this.mybatisInterceptors != null && this.mybatisInterceptors.length > 0){
            factory.setPlugins(this.mybatisInterceptors);
        }
        factory.setTypeAliasesPackage(this.mybatisProperties.getTypeAliasesPackage());
        factory.setTypeHandlersPackage(this.mybatisProperties.getTypeHandlersPackage());
        // XML MAPPER路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources(this.mybatisProperties.getMapperLocations()));

        return factory.getObject();
    }

    /**
     * sqlSessionTemplate
     * @author: 研发部-黄泽建
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory, this.mybatisProperties.getExecutorType());
    }

    /**
     * 事务管理器
     * @author: 研发部-黄泽建
     * @param dataSource
     * @return
     */
	/*
	 * @Bean public DataSourceTransactionManager transactionManager(DataSource
	 * dataSource){ return new DataSourceTransactionManager(dataSource); }
	 */

}
