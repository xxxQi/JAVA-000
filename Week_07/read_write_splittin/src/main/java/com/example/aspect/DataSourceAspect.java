package com.example.aspect;

import com.example.annotation.DataSource;
import com.example.context.DynamicRoutingDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class DataSourceAspect{

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Pointcut(value = "@annotation(com.example.annotation.DataSource)")
    public void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSource annotation = methodSignature.getMethod().getAnnotation(DataSource.class);
        boolean loadBalance = annotation.loadBalance();
        String value = annotation.value();
        String routeKey = dynamicRoutingDataSource.getMasterRouteKey();
        try {
            if (loadBalance){
                routeKey = dynamicRoutingDataSource.loadBalanceOnSlaveDataSource();
            }else if (StringUtils.hasText(value)){
                routeKey =  dynamicRoutingDataSource.getRouteKey(value);
            }
            dynamicRoutingDataSource.setHolderRouteKey(routeKey);
            return joinPoint.proceed();
        }finally {
            dynamicRoutingDataSource.removeHolderRouteKey();
        }

    }
}