package com.farmershao.stock.web;

import java.lang.annotation.*;

/**
 * 后台 Authentication
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BackAuthentication {

    String code() default "";
}
