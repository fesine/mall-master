package com.fesine.mall.controller;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.service.ICategoryService;
import com.fesine.mall.service.IEsService;
import com.fesine.mall.vo.CategoryVo;
import com.fesine.mall.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@RestController
@Slf4j
@ServiceGroup("iis")
@Api(value = "cate测试接口", tags = "cate测试")
public class CategoryController {


    @Resource
    private IEsService iisService;

    @Autowired
    private IEsService jwlogService;

    @ApiOperation(value = "iis正常，继承类注解", tags = "cate测试")
    @GetMapping("/cate/iis")
    public String iis() {
        return iisService.getMetrics();
    }

    @ApiOperation(value = "jwlog使用iis，继承类注解", tags = "cate测试")
    @GetMapping("/cate/jwlog")
    public String jwlog() {
        return jwlogService.getMetrics();
    }

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){

        return categoryService.selectAll();
    }
}
