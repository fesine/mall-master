package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.ItemDTO;

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
public @interface EsItemField {

    /**
     * 配置对应的map key
     * 传递获取值使用key.value
     * ->value表示一层传递，如有多层传递使用key->value->value
     * @return metricsData.get(metricsData.get(itemKey))
     * 对对象取值可用.
     * A.b(要求b为metricsData对象的subMap)
     * subMap = metricsData.get(A)
     * return subMap.get(b)
     */
    String itemKey();

    /**
     * 当返回值==expect配置的值时，替换成fill的值
     * @return
     */
    String expect() default "";

    String fill() default "";

    /**
     * 返回值是日期字符串时，进行日期转换
     * @return
     */
    String dateFormat() default "";

    /**
     * 针对Double、Float、BigDecimal类型小数部分进行格式化(四舍五入)
     * @return
     */
    int numberScale() default  -1;

    /**
     * 引用类型类,需要继承ItemDTO类
     * @return
     */
    Class<? extends ItemDTO> reference() default ItemDTO.class;
}
