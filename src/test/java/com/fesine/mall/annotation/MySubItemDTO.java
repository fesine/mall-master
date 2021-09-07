package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.ItemDTO;
import lombok.Data;

/**
 * @description: 类描述
 * 包装类：Integer、Long、Short、Boolean、Byte、Character、Double、Float
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
@Data
public class MySubItemDTO extends ItemDTO {

    @EsItemField(itemKey = "ss_name")
    private String name;

    @EsItemField(itemKey = "ss_age", expect = "NaN", fill = "0")
    private Integer age;


}
