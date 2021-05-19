package com.fesine.mall.service.impl;

import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.service.ICategoryService;
import com.fesine.mall.vo.CategoryVo;
import com.fesine.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public class CategoryServiceTest extends MallApplicationTest {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}