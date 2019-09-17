package com.platform.system.common.mybatis.interceptor;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截慢SQL
 * @version: 1.0
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PrintSlowSQLInterceptor extends PrintSQLInterceptor{
    
    private static final Logger log = LoggerFactory.getLogger(PrintSlowSQLInterceptor.class);
    
    private long slowSqlTimeMillis = 1000; //默认超过1秒就算慢sql
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if(invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        long end = System.currentTimeMillis();
        long time = (end - start);
        String sql = getSqlCostTime(configuration, boundSql, sqlId, time);
        if(time > slowSqlTimeMillis) {
            // 超过限制就算慢SQL
            log.warn("[慢]:{}", sql);
        }
        return result;
    }

    /**
     * 获取SQL语句及耗时情况
     * @param configuration
     * @param boundSql
     * @param sqlId
     * @param time
     * @return
     */
    private String getSqlCostTime(Configuration configuration, BoundSql boundSql, String sqlId, long time){
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(time);
        str.append("ms");
        str.append(":");
        str.append(sql);
        return str.toString();
    }

    /**
     * 插件
     */
    @Override
    public Object plugin(Object target){
        return Plugin.wrap(target, this);
    }

    /**
     * 属性配置
     */
    @Override
    public void setProperties(Properties properties){
        String slowSqlTimeMillis = properties.getProperty("slowSqlTimeMillis");
        if (StringUtils.isNotBlank(slowSqlTimeMillis)){
            this.slowSqlTimeMillis = Long.parseLong(slowSqlTimeMillis);
        }
    }
}
