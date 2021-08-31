package com.fesine.mall.controller;

import com.fesine.mall.service.IEsService;
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
public class BeanTestController {

    @Resource
    private IEsService iisService;

    @Resource(name = "jwlog")
    private IEsService jwlogService;

    @GetMapping("/iis")
    public String iis(){
        return iisService.getMetrics();
    }

    @GetMapping("/jwlog")
    public String jwlog(){
        return jwlogService.getMetrics();
    }
}
