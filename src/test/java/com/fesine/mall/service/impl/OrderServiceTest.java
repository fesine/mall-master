package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.service.IOrderService;
import com.fesine.mall.vo.OrderVo;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
@Slf4j
public class OrderServiceTest extends MallApplicationTest {

    @Autowired
    private IOrderService orderService;

    Gson gson = new Gson();

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(2, 6);
        log.info("list = {}", JSON.toJSONString(responseVo.getData()));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list(){
        ResponseVo<PageInfo> responseVo = orderService.list(2, 1, 2);
        log.info("list = {}", gson.toJson(responseVo.getData()));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<OrderVo> responseVo = orderService.detail(2, 1622269087565L);
        log.info("list = {}", gson.toJson(responseVo.getData()));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void cancel(){
        ResponseVo responseVo = orderService.cancel(2, 1622269087565L);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}