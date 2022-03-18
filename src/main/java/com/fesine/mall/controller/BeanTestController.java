package com.fesine.mall.controller;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.aop.Auth;
import com.fesine.mall.as.AbstractService;
import com.fesine.mall.config.EsMultiServiceManager;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.service.IEsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ServiceGroup("jwlog")
    private IEsService jwlogService;

    @Autowired
    private ICartService cartService;

    @Resource
    private EsMultiServiceManager serviceManager;

    @Resource(name = "cclService")
    private AbstractService cclService;

    @Resource(name = "capService")
    private AbstractService capService;



    @ApiOperation(value = "测试抽象类注入属性", tags = "bean测试")
    @GetMapping("/abs")
    public String abs() throws Exception {
        cclService.invoke();
        capService.invoke();
        return iisService.getMetrics();
    }

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

    @ApiOperation(value = "传入group，调用服务", tags = "bean测试")
    @GetMapping("/group/{group}")
    public String getByGroup(@PathVariable String group){
        return serviceManager.getService(group).getMetrics();
    }
}
