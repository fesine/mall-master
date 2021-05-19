package com.fesine.mall.service;

import com.fesine.mall.vo.CategoryVo;
import com.fesine.mall.vo.ResponseVo;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();
}
