package com.fesine.mall.controller;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.aop.Auth;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.service.IEsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/1
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/1
 */
@Api(value = "bean测试接口", tags = "bean测试")
@RestController
@ServiceGroup("jwlog")
public class BeanTestController {

    /**
     * 继续使用iis
     */
    @Resource
    @ServiceGroup("iis")
    private IEsService iisService;

    /**
     * 使用的是jwlog配置
     */
    @Auth
    @Autowired
    private IEsService jwlogService;

    @Autowired
    private ICartService cartService;


    @ApiOperation(value = "iis正常，field注解", tags = "bean测试")
    @GetMapping("/iis")
    public String iis(){
        return iisService.getMetrics();
    }

    @ApiOperation(value = "jwlog正常，继承类注解", tags = "bean测试")
    @GetMapping("/jwlog")
    public String jwlog(){
        return jwlogService.getMetrics();
    }
}
