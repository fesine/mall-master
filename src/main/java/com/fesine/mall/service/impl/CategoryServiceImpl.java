package com.fesine.mall.service.impl;

import com.fesine.mall.dao.CategoryMapper;
import com.fesine.mall.pojo.Category;
import com.fesine.mall.service.ICategoryService;
import com.fesine.mall.vo.CategoryVo;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fesine.mall.constants.MallConstants.ROOT_PARENT_ID;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<CategoryVo> voList = new ArrayList<>();
        List<Category> categoryList = categoryMapper.selectAll();
        for (Category category : categoryList) {
            if (ROOT_PARENT_ID.equals(category.getParentId())) {
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category, categoryVo);
                voList.add(categoryVo);
            }
        }
        return ResponseVo.success(voList);
    }
}
