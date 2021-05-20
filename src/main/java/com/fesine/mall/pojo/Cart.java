package com.fesine.mall.pojo;

import lombok.Data;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
