package com.fesine.mall.annotation;

import org.springframework.context.annotation.Import;

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
@Import(ScanPackageRegister.class)
public @interface ScanPackage {

    String[] value();
}
