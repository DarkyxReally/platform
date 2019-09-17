package com.platform.system.common.mybatis.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印执行的sql
 * @version: 1.0
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PrintSQLInterceptor implements Interceptor {
    
    private static final Logger log = LoggerFactory.getLogger(PrintSQLInterceptor.class);
   
    /**
     * 是否记录查询语句
     */
    private boolean showSelectSql = false;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if(invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        // 判断是否查询语句, 如果是查询语句, 不打印SQL, 其他的打印SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if(SqlCommandType.SELECT == sqlCommandType) {
            if (showSelectSql){
                String sql = showSql(configuration, boundSql, sqlId);
                log.info(sql);
            }
        }else{
            String sql = showSql(configuration, boundSql, sqlId);
            log.info(sql);
        }
        return invocation.proceed();
    }

    /**
     * 获取SQL语句及耗时情况
     * @param configuration
     * @param boundSql
     * @param sqlId
     * @param time
     * @return
     */
    private String showSql(Configuration configuration, BoundSql boundSql, String sqlId){
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(sql);
        return str.toString();
    }

    /**
     * 获取参数值
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj){
        String value = null;
        if(obj instanceof String){
            value = "'" + obj.toString() + "'";
        }else if(obj instanceof Date){
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        }else{
            if(obj != null){
                value = obj.toString();
            }else{
                value = "";
            }

        }
        return value;
    }

    /**
     * 获取SQL语句
     * @param configuration
     * @param boundSql
     * @return
     */
    protected String showSql(Configuration configuration, BoundSql boundSql){
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if(parameterMappings.size() > 0 && parameterObject != null){
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                String val = getParameterValue(parameterObject);
                // Matcher.quoteReplacementJDK自动处理特殊字符的，比如$，用replace方法之前都用一次，把结果给replace用
                // val = java.util.regex.Matcher.quoteReplacement(val);
                sql = sql.replaceFirst("\\?", val);
            }else{
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for(ParameterMapping parameterMapping : parameterMappings){
                    String propertyName = parameterMapping.getProperty();
                    if(metaObject.hasGetter(propertyName)){
                        Object obj = metaObject.getValue(propertyName);
                        String val = getParameterValue(obj);
                        sql = dealParamsSpecialChar(sql, val);
                    }else if(boundSql.hasAdditionalParameter(propertyName)){
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        String val = getParameterValue(obj);
                        sql = dealParamsSpecialChar(sql, val);
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 处理特殊字符
     * @param sql
     * @param val
     * @return
     */
    private String dealParamsSpecialChar(String sql, String val){
        try{
            sql = sql.replaceFirst("\\?", val);
        }catch(IllegalArgumentException e){
            // Matcher.quoteReplacementJDK自动处理特殊字符的，比如$。用replace方法之前都用一次，把结果给replace用
            val = java.util.regex.Matcher.quoteReplacement(val);
            sql = sql.replaceFirst("\\?", val);
        }catch(IndexOutOfBoundsException e){
            // Matcher.quoteReplacementJDK自动处理特殊字符的，比如$。用replace方法之前都用一次，把结果给replace用
            val = java.util.regex.Matcher.quoteReplacement(val);
            sql = sql.replaceFirst("\\?", val);
        }
        return sql;
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
        String showSelectSql = properties.getProperty("showSelectSql", "false");
        this.showSelectSql = "true".equals(showSelectSql);
    }
}
