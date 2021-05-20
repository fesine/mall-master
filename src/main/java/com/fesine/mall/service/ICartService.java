package com.fesine.mall.service;

import com.fesine.mall.form.CartAddForm;
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

}
