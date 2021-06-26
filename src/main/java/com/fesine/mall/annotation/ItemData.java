package com.fesine.mall.annotation;

import java.lang.annotation.*;

/**
 * @description: 获取List中每个map的元素转换成DTO属性
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemData {

    /**
     * 配置对应的map key
     * 传递获取值使用key.value
     * ->value表示一层传递，如有多层传递使用key->value->value
     * map = metricsData.iter
     * @return map.get(itemKey)
     */
    String itemKey();
}
