package cc.mrbird.common.annotation;

import cc.mrbird.common.domain.LimitType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    // 资源名称，用于描述接口功能
    String name() default "";

    // 资源 key
    String key() default "";

    // key prefix
    String prefix() default "";

    // 时间的，单位秒
    int period();

    // 限制访问次数
    int count();

    // 限制类型
    /* 传统类型
    CUSTOMER,
    根据 IP 限制
    IP;*/
    LimitType limitType() default LimitType.CUSTOMER;
}
