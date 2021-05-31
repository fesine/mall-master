package com.fesine.mall.service;

import com.fesine.mall.vo.OrderVo;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

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
     *
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    /**
     * 查询订单列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    /**
     * 查看订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    /**
     * 取消订单
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo cancel(Integer uid, Long orderNo);
}
