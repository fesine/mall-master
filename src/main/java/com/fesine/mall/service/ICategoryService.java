package com.fesine.mall.service;

import com.fesine.mall.vo.CategoryVo;
import com.fesine.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public interface ICategoryService {

    /**
     * 查询所有类别
     * @return
     */
    ResponseVo<List<CategoryVo>> selectAll();

    /**
     * 查询所有的子类别
     * @param id
     * @param resultSet
     */
    void findSubCategoryId(Integer id, Set<Integer> resultSet);


}
