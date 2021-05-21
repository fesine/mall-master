package com.fesine.mall.form;

import lombok.Data;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/21
 */
@Data
public class CartUpdateForm {
    private Integer quantity;

    private Boolean selected;
}
