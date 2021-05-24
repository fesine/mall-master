package com.fesine.mall.service;

import com.fesine.mall.form.ShippingForm;
import com.fesine.mall.pojo.Shipping;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/21
 */
public interface IShippingService {

    /**
     * 添加地址
     * @param uid
     * @param form
     * @return
     */
    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm form);

    /**
     * 删除地址
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo delete(Integer uid,Integer shippingId);

    /**
     * 更新地址
     * @param uid
     * @param shippingId
     * @param form
     * @return
     */
    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);

    /**
     * 分页查询地址
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo<Shipping>> list(Integer uid, Integer pageNum, Integer pageSize);
}
