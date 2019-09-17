package com.platform.rabbitmq.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 事务管理配置
 * @version: 1.0
 */
@Configuration
public class TransactionConfiguration {
    
    /**
     * 必须事务的超时时间
     */
    private static final int REQUIRED_TIMEOUT = 60;
    
    /**
     * 新开事务超时时间, 一般用于执行时间比较长的方法
     */
    private static final int REQUIRED_NEW_TIMEOUT = 120;
    
    /**
     * 事务切面表达式
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.ligu.app..service.impl.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 事务的切面配置
     * @version: 1.0
     * @return
     */
    private Map<String, TransactionAttribute> txAttributeMap(){
        // 只读事务，不做更新操作
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // support
        RuleBasedTransactionAttribute supportTx = new RuleBasedTransactionAttribute();
        supportTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        
        // 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(REQUIRED_TIMEOUT);
        
        // 新开事务
        RuleBasedTransactionAttribute requiredNewTx = new RuleBasedTransactionAttribute();
        requiredNewTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));
        requiredNewTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        requiredNewTx.setTimeout(REQUIRED_NEW_TIMEOUT);
        
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        // 必须事务
        txMap.put("init", requiredTx);
        txMap.put("init*", requiredTx);
        txMap.put("cancel", requiredTx);
        txMap.put("cancel*", requiredTx);
        txMap.put("create", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("add*", requiredTx);
        txMap.put("sub*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("receive", requiredTx);
        txMap.put("receive*", requiredTx);
        txMap.put("incr*", requiredTx);
        txMap.put("process", requiredTx);
        txMap.put("process*", requiredTx);
        txMap.put("exec*", requiredTx);
        
        // 新开事务
        txMap.put("execNew*", requiredNewTx); 

        // 只读事务
        txMap.put("is*", readOnlyTx);
        txMap.put("count*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        
        // 默认为support模式
        txMap.put("*", supportTx);
        return txMap;
    }
    
    
    /**
     * 事务拦截器
     * @return
     */
    @Bean
    public TransactionInterceptor defaultTxAdvice(){
        Map<String, TransactionAttribute> txAttributeMap = txAttributeMap();
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txAttributeMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * 事务切面
     * @return
     */
    @Bean
    public Advisor defaultTxAdviceAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, defaultTxAdvice());
    }

}