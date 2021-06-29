package com.fesine.mall.annotation;

import java.lang.annotation.*;

/**
 * @description: 作用于类上，用于获取根节点
 * @author: fesine
 * @createTime:2021/6/22
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsMetricsField {

    /**
     * 配置分组字段
     * @return data.get(groupBy)
     */
    String groupBy();
}
