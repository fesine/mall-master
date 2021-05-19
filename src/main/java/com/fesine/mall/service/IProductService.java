package com.fesine.mall.service;

import com.fesine.mall.vo.ProductDetailVo;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public interface IProductService {

    /**
     * 分页查询商品列表
     *
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer id);
}
