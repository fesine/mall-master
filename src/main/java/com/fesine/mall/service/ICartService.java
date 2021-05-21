package com.fesine.mall.service;

import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.form.CartUpdateForm;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
public interface ICartService {

    /**
     * 添加购物车
     * @param uid
     * @param cartAddForm
     * @return
     */
    ResponseVo<CartVo> add(Integer uid,CartAddForm cartAddForm);

    /**
     * 查询购物车
     * @param uid
     * @return
     */
    ResponseVo<CartVo> list(Integer uid);

    /**
     * 更新购物车
     *
     * @param uid
     * @param productId
     * @param cartUpdateForm
     * @return
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    /**
     * 删除购物车
     * @param uid
     * @param productId
     * @return
     */
    ResponseVo<CartVo> delete(Integer uid,Integer productId);

    /**
     * 全选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> selectAll(Integer uid);

    /**
     * 全不选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /**
     * 获取购物车总数
     * @param uid
     * @return
     */
    ResponseVo<Integer> sum(Integer uid);

}
