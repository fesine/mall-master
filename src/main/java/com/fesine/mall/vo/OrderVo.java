package com.fesine.mall.vo;

import com.fesine.mall.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
@Data
public class OrderVo {


    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Integer shippingId;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVo> orderItemVoList;

    private Shipping shippingVo;
}
