package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.ItemDTO;
import lombok.Data;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
@Data
public class MyItemDTO extends ItemDTO {

    @ItemData(itemKey = "s_name")
    private String name;

    @ItemData(itemKey = "s_age")
    private String age;

    /**
     * address是由name的值作为key再取出的值
     */
    @ItemData(itemKey = "s_name->value")
    private String  address;

    @ItemData(itemKey = "s_name->value->value")
    private String zip;

}
