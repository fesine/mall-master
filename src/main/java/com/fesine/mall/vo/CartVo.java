package com.fesine.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 购物车
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Data
public class CartVo {

    /**
     * 商品列表
     */
    private List<CartProductVo> cartProductVoList;

    /**
     * 是否全选
     */
    private Boolean selectedAll;

    /**
     * 购物车已选中商品总价
     */
    private BigDecimal cartTotalPrice;

    /**
     * 购物车已选中商品总数
     */
    private Integer cartTotalQuantity;
}
