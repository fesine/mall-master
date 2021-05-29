package com.fesine.mall.service;

import com.fesine.mall.vo.OrderVo;
import com.fesine.mall.vo.ResponseVo;

/**
 * @description: 订单服务接口
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);
}
