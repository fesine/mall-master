package com.fesine.mall.controller;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.service.IEsService;
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
@RestController
@ServiceGroup("jwlog")
public class BeanTestController {

    @Resource
    @ServiceGroup("iis")
    private IEsService iisService;

    @Autowired
    //@ServiceGroup("jwlog")
    private IEsService jwlogService;

    @Autowired
    private ICartService cartService;


    @GetMapping("/iis")
    public String iis(){
        return iisService.getMetrics();
    }

    @GetMapping("/jwlog")
    public String jwlog(){
        return jwlogService.getMetrics();
    }
}
