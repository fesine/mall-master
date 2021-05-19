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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Category> categoryList = categoryMapper.selectAll();
        //for (Category category : categoryList) {
        //    if (ROOT_PARENT_ID.equals(category.getParentId())) {
        //        CategoryVo categoryVo = new CategoryVo();
        //        BeanUtils.copyProperties(category, categoryVo);
        //        voList.add(categoryVo);
        //    }
        //}
        //使用lambada表达式实现
        List<CategoryVo> voList =
                categoryList.stream().filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        //查询子类别
        findSubCategory(categoryList, voList);
        return ResponseVo.success(voList);
    }

    /**
     * 查询所有的子类别
     *
     * @param id
     * @param resultSet
     */
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categoryList = categoryMapper.selectAll();
        findSubCategoryIdSet(id, resultSet, categoryList);
    }

    private void findSubCategoryIdSet(Integer id, Set<Integer> resultSet, List<Category> categoryList) {
        for (Category category : categoryList) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryIdSet(category.getId(),resultSet, categoryList);
            }
        }
    }

    /**
     * 查询子类别
     * @param categoryList 全部类别列表
     * @param voList 父级类别列表
     */
    private void findSubCategory(List<Category> categoryList, List<CategoryVo> voList){
        for (CategoryVo categoryVo : voList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            categoryVo.setSubCategories(subCategoryVoList);
            for (Category category : categoryList) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    subCategoryVoList.add(category2CategoryVo(category));
                }
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                findSubCategory(categoryList,subCategoryVoList);
            }
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
