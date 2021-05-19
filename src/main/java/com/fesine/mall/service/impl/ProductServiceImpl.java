package com.fesine.mall.service.impl;

import com.fesine.mall.dao.ProductMapper;
import com.fesine.mall.enums.ProductEnum;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.pojo.Product;
import com.fesine.mall.service.ICategoryService;
import com.fesine.mall.service.IProductService;
import com.fesine.mall.vo.ProductDetailVo;
import com.fesine.mall.vo.ProductVo;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategoryService categoryService;
    /**
     * 分页查询商品列表
     *
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> set = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, set);
            set.add(categoryId);
        }
        PageHelper.startPage(pageNum, pageSize, true);
        List<Product> products = productMapper.selectByCategoryIdSet(set);
        List<ProductVo> productVos = products.stream().map(e -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(e, productVo);
            return productVo;
        }).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productVos);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        //只对确定性条件判断
        if (ProductEnum.OFF_SALE.getCode().equals(product.getStatus()) || ProductEnum.DELETE.getCode().equals(product.getStatus())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo detailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,detailVo);
        //重新设置库存
        if (detailVo.getStock() > 100) {
            detailVo.setStatus(100);
        }
        return ResponseVo.success(detailVo);
    }
}
