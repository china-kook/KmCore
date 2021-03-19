package com.km66.framework.core.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 使用切面管理事务
 *
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午2:27:44]
 * @version: [v1.0]
 */
@Aspect
@Configuration
public class TransactionAdviceInterceptor {

    /**
     * 事务超时时间
     */
    private static final int TX_METHOD_TIMEOUT = 6000;
    /**
     * 事务生效方法匹配表达式
     */
    private static String AOP_POINTCUT_EXPRESSION = "execution(* com.km66..web..service.*.*(..))";

    @Bean("transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager(
            @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("txAdvice")
    public TransactionInterceptor txAdvice(@Qualifier("transactionManager") PlatformTransactionManager m) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_METHOD_TIMEOUT);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("*", requiredTx);
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(m, source);
        return txAdvice;
    }


    @Bean
    public Advisor txAdviceAdvisor(@Qualifier("txAdvice") TransactionInterceptor txAdvice, @Autowired(required = false) TransactionConfig transactionConfig) throws InterruptedException {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        if (transactionConfig != null) {
            TransactionConfig.PointcutExpression pointcutExpression = new TransactionConfig.PointcutExpression();
            transactionConfig.setPointcutExpression(pointcutExpression);
            AOP_POINTCUT_EXPRESSION += pointcutExpression.get();
        }
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}