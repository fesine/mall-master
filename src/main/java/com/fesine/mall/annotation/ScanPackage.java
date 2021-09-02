package com.fesine.mall.annotation;

import java.lang.annotation.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/3
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/3
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScanPackage {

    String[] value();
}
