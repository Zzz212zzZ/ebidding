package com.ebidding.common.auth;

import java.lang.annotation.*;

@Documented
//自定义注解，.RUNTIME : 始终不会丢弃，可以使用反射获得该注解的信息。自定义的注解最常用的使用方式。
@Retention(RetentionPolicy.RUNTIME)
//Target – 注解用于什么地方
@Target({ElementType.METHOD})
public @interface Authorize {
    String[] value();
}