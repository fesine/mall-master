package com.fesine.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 购物车商品
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Data
public class CartProductVo {

    private Integer productId;
    /**
     * 购买的商品数量
     */
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    private Integer productStatus;
    /**
     * 当前商品总价=productPrice*quantity
     */
    private BigDecimal productTotalPrice;
    private Integer productStock;
    /**
     * 商品是否选中
     */
    private Boolean productSelected;
}
