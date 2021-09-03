package com.fesine.mall.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/3
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/3
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    String[] values() default {};
}
