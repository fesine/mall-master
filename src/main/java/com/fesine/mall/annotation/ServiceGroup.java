package com.fesine.mall.annotation;

import java.lang.annotation.*;

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
public @interface ServiceGroup {

    /**
     * 实例组名称
     * @return
     */
    String value() default "";
}
