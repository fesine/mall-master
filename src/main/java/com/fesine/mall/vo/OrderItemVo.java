package com.fesine.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;


    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}
