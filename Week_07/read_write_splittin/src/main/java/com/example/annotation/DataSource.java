package com.example.annotation;

import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    /**
     * value 指定数据源进行访问，与loadBalance()互斥
     * @return
     */
    String value() default "";

    /**
     * loadBalance 为true时，数据源默认在从库集合中进行权重选举
     * @return
     */
    boolean loadBalance() default false;

}
