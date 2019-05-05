package com.farmershao.stock.web;

import java.lang.annotation.*;

/**
 * 后台 Authentication
 *
 * @author : Zhao Da
 * @since : 2019/4/28 14:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BackAuthentication {

    String code() default "";
}
