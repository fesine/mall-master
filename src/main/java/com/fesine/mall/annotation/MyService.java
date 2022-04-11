package com.fesine.mall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 使用此注解的服务，在注入时会优先寻找当前value对应的组服务
 * @author: fesine
 * @createTime:2021/9/2
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/2
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyService {

    /**
     * 实例组名称
     * @return
     */
    String value() default "" ;
}
